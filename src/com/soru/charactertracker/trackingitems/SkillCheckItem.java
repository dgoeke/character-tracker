package com.soru.charactertracker.trackingitems;

import android.support.v4.app.Fragment;

public class SkillCheckItem extends DiceRollItem {
	int bonus;
	
	public SkillCheckItem(Fragment parent, String name, int bonus) {
		super(parent, name, String.format("d20+%d", bonus));
		this.bonus = bonus;
	}
	
	@Override
	public void createView() {
		super.createView();
		setSubtext("");
		setValue(String.format("%s%d", bonus < 0 ? "" : "+", bonus));
	}

}