package com.soru.charactertracker.trackingitems;

import android.support.v4.app.Fragment;

public class PointsCountUpItem extends PointsItem {
	public PointsCountUpItem(Fragment parent, String name) {
		super(parent, name, 0);
	}
	
	public String toString() { return name; }

	@Override
	public void createView() {
		expandTrackingItemLayout();
		setName(name);
		setSubtext("");
		setCurrent(0);
	}
	
	void setCurrent(int current) {
		value = current;
		setValue(value);
	}
}
