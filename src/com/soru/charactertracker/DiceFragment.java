package com.soru.charactertracker;

import org.petitparser.context.Result;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.soru.charactertracker.R;

public class DiceFragment extends Fragment {
	View view;
	LinearLayout resultsLayout;
	ScrollView scroll;
	DiceParser diceParser = new DiceParser();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
            return null;
        }
		return (View)inflater.inflate(R.layout.dice_fragment, container, false);
	}

	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
		this.view = view;
		
		view.findViewById(R.id.buttonD20).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDiceResultView("Quick roll: d20", "d20");
			}
		});
		view.findViewById(R.id.buttonD12).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDiceResultView("Quick roll: d12", "d12");
			}
		});
		view.findViewById(R.id.buttonD10).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDiceResultView("Quick roll: d10", "d10");
			}
		});
		view.findViewById(R.id.buttonD8).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDiceResultView("Quick roll: d8", "d8");
			}
		});
		view.findViewById(R.id.buttonD6).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDiceResultView("Quick roll: d6", "d6");
			}
		});
		view.findViewById(R.id.buttonD4).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDiceResultView("Quick roll: d4", "d4");
			}
		});
		
		resultsLayout = (LinearLayout)view.findViewById(R.id.resultsLayout);
		scroll = (ScrollView)view.findViewById(R.id.scrollView);
	}

	public void addDiceResultView(String title, String rollString) {
		int result = 0;
		
		Result r = diceParser.parse(new org.petitparser.context.Context(rollString));
		if (r.isSuccess()) {
			result = r.get();
		}

		LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = (View)inflater.inflate(R.layout.dice_result, null);

		TextView txtResult   = (TextView)v.findViewById(R.id.txtResult);
		TextView txtTitle    = (TextView)v.findViewById(R.id.txtTitle);
		TextView txtSubtitle = (TextView)v.findViewById(R.id.txtSubtitle);
		
		txtResult.setText(Integer.toString(result));
		txtTitle.setText(title);
		txtSubtitle.setText(diceParser.getRollAsString());

		addResult(v);
	}

	public void addAttackResultView(Integer result, String title, String subtitle) {
		LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = (View)inflater.inflate(R.layout.dice_result, null);

		TextView txtResult   = (TextView)v.findViewById(R.id.txtResult);
		TextView txtTitle    = (TextView)v.findViewById(R.id.txtTitle);
		TextView txtSubtitle = (TextView)v.findViewById(R.id.txtSubtitle);
		
		txtResult.setText(Integer.toString(result));
		txtTitle.setText(title);
		txtSubtitle.setText(subtitle);

		addResult(v);
	}

	private void addResult(View v) {
		resultsLayout.addView(v);
		//scroll.fullScroll(View.FOCUS_DOWN);
		
		scroll.post(new Runnable() {            
		    @Override
		    public void run() {
		           scroll.fullScroll(View.FOCUS_DOWN);              
		    }
		});
	}

}
