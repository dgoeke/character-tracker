package com.soru.charactertracker;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.soru.charactertracker.trackingitems.TrackingItem;

public class ItemsAdapter extends BaseAdapter {
	List<TrackingItem> items;
	
	public ItemsAdapter(List<TrackingItem> items) {
		this.items = items;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		return items.get(position).getView();
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return items.get(position).getView().getId();
	}
}
