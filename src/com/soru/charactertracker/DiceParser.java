package com.soru.charactertracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Function;

public class DiceParser extends DiceGrammar {
	Random rand = new Random();
	String listAsString;
	public List<Integer> rolls = new ArrayList<Integer>();
	
	protected void initialize() {
	    super.initialize();
	    
	    action("roll", new Function<DieRoll, Integer>() { 
			@Override
			public Integer apply(DieRoll die) {
				int result = die.roll(0);
				rolls.add(result);
				return result;
			}
		});
	   
		action("result", new Function<List<?>, Integer>() {
			@Override
			public Integer apply(List<?> list) {
				return parseList(list);
			}
		});
	}
  
	public String getRollAsString() {
		return listAsString;
	}
  
	int parseList(List<?> items) {
		int result = (Integer)items.get(0);
		StringBuilder sb = new StringBuilder();
		
		sb.append(result);
		sb.append(' ');
    
		for (int pos = 1; pos < items.size(); pos += 2) {
			sb.append(' ');
			sb.append((Character)items.get(pos));
			sb.append(' ');
	  	  	sb.append((Integer)items.get(pos + 1));
	  	  
	  	  	if ((Character)items.get(pos) ==  '+')
	  	  		result += (Integer)items.get(pos + 1);
	  	  	else
	  	  		result -= (Integer)items.get(pos + 1);
		}

		listAsString = sb.toString();
		return result;
	}
}


