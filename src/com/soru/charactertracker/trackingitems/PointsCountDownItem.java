package com.soru.charactertracker.trackingitems;

import android.support.v4.app.Fragment;

public class PointsCountDownItem extends PointsItem {
	public int maxValue;
	
	public PointsCountDownItem(Fragment parent, String name, int startingValue, int maxValue) {
		super(parent, name, startingValue);
		this.maxValue = maxValue;
	}
	
	public String toString() { return name; }

	@Override
	public void createView() {
		expandTrackingItemLayout();
		setName(name);
		setCurrent(value);
	}
	
	void setCurrent(int current) {
		value = current;
		setValue(value);
		setSubtext(String.format("%d / %d (%.1f%%)", value, maxValue, (1.0 * current / maxValue) * 100));
	}
	
	@Override
	public void onValueChanged() {
		setSubtext(String.format("%d / %d (%.1f%%)", value, maxValue, (1.0 * value / maxValue) * 100));
	}
}