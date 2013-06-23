package com.soru.charactertracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.soru.charactertracker.attackinfo.AttackInfo;
import com.soru.charactertracker.attackinfo.AttackInfo.hitOutcome;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

public class HitDialog {
	private Context context;
	private SortedSet<Integer> acList = new TreeSet<Integer>();
	private List<AttackInfo> attacks = new ArrayList<AttackInfo>();
	private OnClickListener listener;
	private AttackInfo lowestAtk;
	private int numMisses = 0;

	public HitDialog(Context context, AttackInfo info) {
		this.context = context;
		attacks.add(info);
	}

	public HitDialog(Context context, List<AttackInfo> info) {
		this.context = context;
		attacks.addAll(info);
	}
	
	protected void buildAcList() {
		int lowestAtkResult = 999;
		int currentResult = 0;
		numMisses = 0;

		acList.clear();
		lowestAtk = attacks.get(0);

		for (AttackInfo atk : attacks) {
			if (atk.isCritMiss) {
				currentResult = 0;
				numMisses++;
			} else if (atk.hideFromHitScreen == false) {
				acList.add(atk.hit);
				currentResult = atk.hit;
				
				if (atk.isThreat) {
					acList.add(atk.hitConfirm);
					currentResult = atk.hitConfirm;
				}
			}
			
			if (currentResult < lowestAtkResult && atk.hideFromHitScreen == false) {
				lowestAtkResult = currentResult;
				lowestAtk = atk;
			}
		}
	}
	
	public void show(OnClickListener listener) {
		if (listener != null) {
			this.listener = listener;
		}
		
		buildAcList();
		
		if (acList.size() == 1) {
			showYesNoAlert();
		} else {
			showListAlert();
		}
	}

	private void showListAlert() {
		if (acList.size() == 0) {
			resolveWithAc(999);
			return;
		}
		
		final String[] items = new String[acList.size() + 2];
		int i = 1;
		
		for (final Iterator<Integer> it = acList.iterator(); it.hasNext(); )
			items[i++] = it.next().toString();

		items[0] = String.format("Reroll lowest (%d miss%s)", numMisses, numMisses == 1 ? "" : "es");
		items[i++] = "None";
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Choose the lowest that hits:"); //.setMessage("Choose the lowest AC that hits.");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	String text = items[item];
		    	
		    	if (text == "None")
		    		resolveWithAc(999);
		    	else if (text.contains("Reroll"))
		    		rerollLowest();
		    	else 
		    		resolveWithAc(Integer.parseInt(text));
		    }
		});
		builder.create().show();
	}

	protected void rerollLowest() {
		if (lowestAtk.isThreat)
			lowestAtk.RerollConfirm();
		else
			lowestAtk.Reroll();
		
		this.show(null);
	}

	private void showYesNoAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Hit?").setMessage(String.format("Does %d hit?", acList.first()));
		builder.setPositiveButton("Yes", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				resolveWithAc(acList.first());
			}
		});
		builder.setNegativeButton("No", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				resolveWithAc(999);
			}
		});
		
		builder.create().show();
	}

	protected void resolveWithAc(int ac) {
		for (AttackInfo atk : attacks) {
			atk.resolveWithAc(ac);
		}
		
		listener.onClick(null);
	}

}
