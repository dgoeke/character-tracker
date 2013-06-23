package com.soru.charactertracker.trackingitems;

import com.soru.charactertracker.DiceFragment;
import com.soru.charactertracker.DiceParser;
import com.soru.charactertracker.HitDialog;
import com.soru.charactertracker.MainActivity;
import com.soru.charactertracker.attackinfo.AttackInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.Fragment;
import android.view.View;

public class SingleAttackItem extends TrackingItem {
	String attackString;
	String damageString;
	String name;
	AttackInfo attackInfo;
	private int hitBonus;
	private int dmgBonus;
	private int d6Bonus;
	
	public SingleAttackItem(Fragment parent, AttackInfo info) {
		super(parent);
		this.name = info.name;
		this.attackString = info.hitDice;
		this.damageString = info.damageDice;
		this.attackInfo = info;
	}
	
	@Override
	public void createView() {
		expandTrackingItemLayout();
		
		setName(name);
		setSubtext(String.format("Hit: %s, Dmg: %s", attackString, damageString));
		setValue("");
	}
	
	public void getHitDmgBonuses() {
		MainActivity act = (MainActivity)parent.getActivity();
		hitBonus = act.getHitBonus();
		dmgBonus = act.getDmgBonus();
		d6Bonus = act.getD6Bonus();
	}
	
	@Override
	public void onClick(View v) {
		DiceParser d = new DiceParser();
		getHitDmgBonuses();
		attackInfo.Roll(d, hitBonus, dmgBonus, d6Bonus);
		
		final HitDialog dlg = new HitDialog(parent.getView().getContext(), attackInfo);
		dlg.show(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attackInfo.finalizeOutcomes();
				displayAttackResults();
			}
		});
	}

	protected void displayAttackResults() {
		MainActivity act = (MainActivity)parent.getActivity();
		DiceFragment dicePage = act.getDicePage();
		
		switch (attackInfo.outcome) {
		case CRIT:
			dicePage.addAttackResultView(attackInfo.critDamage, 
					String.format("%s: %s (%s = %d)", attackInfo.name, attackInfo.outcome.toString(), attackInfo.hitConfirmResult, attackInfo.hitConfirm),
					attackInfo.critDamageResult);
			break;
		case HIT:
			dicePage.addAttackResultView(attackInfo.damage, 
					String.format("%s: %s (%s = %d)", attackInfo.name, attackInfo.outcome.toString(), attackInfo.hitResult, attackInfo.hit),
					attackInfo.damageResult);
			break;
		case MISS:
			dicePage.addAttackResultView(0, 
					String.format("%s: %s (%s = %d)", attackInfo.name, attackInfo.outcome.toString(), attackInfo.hitResult, attackInfo.hit),
					attackInfo.damageResult);
			break;

		}
	}
}
