package com.soru.charactertracker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.soru.charactertracker.R;
import com.soru.charactertracker.trackingitems.*;

public class CharacterFragment extends Fragment {
	ListView itemsList;
	View view;
	List<TrackingItem> trackingItems = new ArrayList<TrackingItem>();
	ItemsAdapter listAdapter = new ItemsAdapter(trackingItems);
	
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
            return null;
        }
		
		return (View)inflater.inflate(R.layout.character_fragment, container, false);
	}
	
	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
		this.view = view;
		itemsList = (ListView)view.findViewById(R.id.listViewCharacterItems);
		
		trackingItems.clear();
		trackingItems.add(new PointsCountDownItem(this, "Hitpoints", 191, 191));

		trackingItems.add(new SavingThrowItem(this, "Fortitude Save", 11));
		trackingItems.add(new SavingThrowItem(this, "Reflex Save",    9));
		trackingItems.add(new SavingThrowItem(this, "Will Save",      14));

		trackingItems.add(new SkillCheckItem(this, "Perception",       14));
		trackingItems.add(new PointsCountDownItem(this, "Mythic Points", 12, 12));
		trackingItems.add(new PointsCountDownItem(this, "4th Level Spells", 3, 3));
		trackingItems.add(new PointsCountDownItem(this, "3rd Level Spells", 6, 6));
		trackingItems.add(new PointsCountDownItem(this, "2nd Level Spells", 6, 6));
		trackingItems.add(new PointsCountDownItem(this, "1st Level Spells", 7, 7));

		itemsList.setAdapter(listAdapter);
		itemsList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				trackingItems.get(position).onClick(view);
			}
		});
    }

	@Override
	public void onPause() {
	    /*MainActivity mainAct = (MainActivity)getActivity();

        PointsCountDownItem hps = (PointsCountDownItem)trackingItems.get(0);
        mainAct.getState().putInt("curHps", hps.value);
	     */
	    super.onPause();
	}

	@Override
	public void onResume() {
	    /*MainActivity mainAct = (MainActivity)getActivity();
	    int curHps = mainAct.getState().getInt("curHps");
		
		if (curHps != 0) {
            PointsCountDownItem hps = (PointsCountDownItem)trackingItems.get(0);
            hps.value = curHps;
            hps.createView();
            hps.onValueChanged();
		}*/

	    super.onResume();
	}

}
