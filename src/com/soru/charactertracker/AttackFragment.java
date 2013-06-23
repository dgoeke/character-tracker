package com.soru.charactertracker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.soru.charactertracker.R;
import com.soru.charactertracker.attackinfo.AttackInfo;
import com.soru.charactertracker.attackinfo.RendingMultipleAttackInfo;
import com.soru.charactertracker.trackingitems.FullAttackItem;
import com.soru.charactertracker.trackingitems.SingleAttackItem;
import com.soru.charactertracker.trackingitems.TrackingItem;

public class AttackFragment extends Fragment {
	EditText editHitBonus;
	EditText editDmgBonus;
	EditText editD6Bonus;

	ListView itemsList;
	View view;
	List<TrackingItem> trackingItems = new ArrayList<TrackingItem>();
	ItemsAdapter listAdapter = new ItemsAdapter(trackingItems);
	
	AttackInfo bite = new AttackInfo("Bite", "d20+23", "2d6+14", 20);
	AttackInfo claw = new AttackInfo("Claw", "d20+23", "2d6+d6+14", 20);
	AttackInfo rend = new AttackInfo("Rend", "d20",    "2d6+19", 20);
	
	MainActivity mainActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
            return null;
        }
		return (View)inflater.inflate(R.layout.attack_fragment, container, false);
	}
	
	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
		
		view.findViewById(R.id.buttonHitPlus).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int newHitBonus = EditFieldToInt(editHitBonus) + 1; 
				editHitBonus.setText(String.valueOf(newHitBonus));
				mainActivity.setHitBonus(newHitBonus);
			}
		});
		view.findViewById(R.id.buttonHitMinus).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int newHitBonus = EditFieldToInt(editHitBonus) - 1; 
				editHitBonus.setText(String.valueOf(newHitBonus));
				mainActivity.setHitBonus(newHitBonus);
			}
		});
		view.findViewById(R.id.buttonDmgPlus).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int newDmgBonus = EditFieldToInt(editDmgBonus) + 1; 
				editDmgBonus.setText(String.valueOf(newDmgBonus));
				mainActivity.setDmgBonus(newDmgBonus);
			}
		});
		view.findViewById(R.id.buttonDmgMinus).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int newDmgBonus = EditFieldToInt(editDmgBonus) - 1; 
				editDmgBonus.setText(String.valueOf(newDmgBonus));
				mainActivity.setDmgBonus(newDmgBonus);
			}
		});
		view.findViewById(R.id.buttonD6Plus).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int newD6Bonus = EditFieldToInt(editD6Bonus) + 1; 
				editD6Bonus.setText(String.valueOf(newD6Bonus));
				mainActivity.setD6Bonus(newD6Bonus);
			}
		});
		view.findViewById(R.id.buttonD6Minus).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int newD6Bonus = EditFieldToInt(editD6Bonus) - 1; 
				editD6Bonus.setText(String.valueOf(newD6Bonus));
				mainActivity.setD6Bonus(newD6Bonus);
			}
		});

		
		mainActivity = (MainActivity)getActivity();
		rend.hideFromHitScreen = true;

		this.view      = view;
		editHitBonus   = (EditText)view.findViewById(R.id.editTextHitBonus);
		editDmgBonus   = (EditText)view.findViewById(R.id.editTextDmgBonus);
		editD6Bonus    = (EditText)view.findViewById(R.id.editTextD6Bonus);
		itemsList      = (ListView)view.findViewById(R.id.listViewItems);
		
		editHitBonus.setText("0");
		editDmgBonus.setText("0");
		editD6Bonus.setText("0");

		List<AttackInfo> attacks = new ArrayList<AttackInfo>();
		attacks.add(bite);
		attacks.add(claw);
		attacks.add(rend);
		
		List<AttackInfo> fullAttack = new ArrayList<AttackInfo>();
		fullAttack.add(bite);
		fullAttack.add(new RendingMultipleAttackInfo(claw.name, claw.hitDice, claw.damageDice, claw.critRange, 4, 2, rend));

		List<AttackInfo> fullHastedAttack = new ArrayList<AttackInfo>();
		fullHastedAttack.add(bite);
		fullHastedAttack.add(new RendingMultipleAttackInfo(claw.name, claw.hitDice, claw.damageDice, claw.critRange, 5, 2, rend));

		
		trackingItems.add(new FullAttackItem(this, "Full Attack", fullAttack));
		trackingItems.add(new FullAttackItem(this, "Full Atk +Haste", fullHastedAttack));
		trackingItems.add(new SingleAttackItem(this, attacks.get(0)));
		trackingItems.add(new SingleAttackItem(this, attacks.get(1)));
		trackingItems.add(new SingleAttackItem(this, attacks.get(2)));
		
		itemsList.setAdapter(listAdapter);
		itemsList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				trackingItems.get(position).onClick(view);
			}
		});
    }

	private int EditFieldToInt(EditText field) {
		String txt = field.getText().toString();
		if (txt.length() == 0)
			return 0;
		else
			return Integer.valueOf(txt);
	}
}
