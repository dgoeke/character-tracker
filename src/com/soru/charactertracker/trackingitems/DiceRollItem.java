package com.soru.charactertracker.trackingitems;

import android.support.v4.app.Fragment;
import android.view.View;

public class DiceRollItem extends TrackingItem {
	String diceString;
	String name;

	DiceRollItem(Fragment parent, String name, String diceString) {
		super(parent);
		
		this.name = name;
		this.diceString = diceString;
	}

	@Override
	public
	void createView() {
		expandTrackingItemLayout();
		
		setName(name);
		setSubtext(diceString);
		setValue("");
	}
	
	@Override
	public void onClick(View v) {
		addDiceResultView(name, diceString);
	}
}
