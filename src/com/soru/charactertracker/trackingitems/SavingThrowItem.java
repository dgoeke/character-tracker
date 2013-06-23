package com.soru.charactertracker.trackingitems;

import android.support.v4.app.Fragment;

public class SavingThrowItem extends SkillCheckItem {
	public SavingThrowItem(Fragment parent, String name, int bonus) {
		super(parent, name, bonus);
	}
	
	@Override
	public void createView() {
		super.createView();
		setSubtext("");
		setValue(String.format("%s%d", bonus < 0 ? "" : "+", bonus));
	}
}
