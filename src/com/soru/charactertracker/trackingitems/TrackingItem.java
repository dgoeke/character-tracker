package com.soru.charactertracker.trackingitems;

import org.petitparser.context.Result;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soru.charactertracker.DiceFragment;
import com.soru.charactertracker.MainActivity;
import com.soru.charactertracker.R;
import com.soru.charactertracker.DiceParser;

public abstract class TrackingItem {
	protected Fragment parent;
	protected View view = null;
	protected Context context;
	
	TextView textViewName;
	TextView textViewSubtext;
	TextView textViewValue;
	
	TrackingItem(Fragment parent) {
		this.context = parent.getView().getContext();
		this.parent = parent;
	}
	
	public abstract void createView();
	public View getView() {
		if (view == null) {
			createView();
		}
		
		return view;
	}

	public void expandTrackingItemLayout() {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = (View)inflater.inflate(R.layout.default_tracking_item, null);

		textViewName    = (TextView)view.findViewById(R.id.textViewName);
		textViewSubtext = (TextView)view.findViewById(R.id.textViewSubtext);
		textViewValue   = (TextView)view.findViewById(R.id.textViewValue);
	}
	
	void setName(final String name) {
		textViewName.setText(name);
	}
	
	void setValue(final String value) {
		textViewValue.setText(value);
		textViewValue.setVisibility(value == "" ? View.GONE : View.VISIBLE);
	}

	void setValue(int value) {
		setValue(String.format("%d", value));
	}

	void setSubtext(final String subtext) {
		textViewSubtext.setText(subtext);
		textViewSubtext.setVisibility(subtext == "" ? View.GONE : View.VISIBLE);
	}

	public void onClick(View v) {
	}
	
	protected void changeToResultsPage() {
		ViewPager pager = (ViewPager)parent.getActivity().findViewById(R.id.pager);
		pager.setCurrentItem(2, true);
	}
	
	protected void addDiceResultView(String title, String rollString) {
		MainActivity act = (MainActivity)parent.getActivity();
		DiceFragment d = act.getDicePage();
		d.addDiceResultView(title, rollString);
	}
}