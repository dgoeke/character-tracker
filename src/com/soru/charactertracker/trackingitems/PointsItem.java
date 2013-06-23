package com.soru.charactertracker.trackingitems;

import com.soru.charactertracker.NumericInputDlg;
import com.soru.charactertracker.NumericInputDlg.modes;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.View;

public class PointsItem extends TrackingItem {
	public int value;
	String name;
	
	PointsItem(Fragment parent, String name, int value) {
		super(parent);
		this.name = name;
		this.value = value;
	}
	
	public String toString() { return name; }

	@Override
	public void createView() {
		expandTrackingItemLayout();
		setName(name);
		setValue(value);
		setSubtext("");
	}
	
	@Override
	public void onClick(View view) {
		final NumericInputDlg dlg = new NumericInputDlg(view.getContext());
		dlg.onOkClicked(new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog,int id) {
		    	modValue(dlg.getResultMode(), dlg.getResultValue());
		    }
		});

		dlg.show();
	}

	protected void modValue(modes resultMode, int resultValue) {
		switch (resultMode) {
		case PLUS: setValue(value += resultValue); break;
		case MINUS: setValue(value -= resultValue); break;
		case EQUALS: setValue(value = resultValue); break;
		}
		
		onValueChanged();
	}
	
	protected void onValueChanged() {
	}
}