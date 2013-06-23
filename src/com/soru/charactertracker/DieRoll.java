package com.soru.charactertracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DieRoll {
	String name;
	String expandedResult;
	int number;
	int size;
	int bonus;
	int result;
	List<Integer> rolls;

	Random rand = new Random();

	private void init(String name, int number, int size, int bonus) {
		this.number = number;
		this.size = size;
		this.bonus = bonus;

		if (name != null)
			this.name = name;
		else
			setName();
		
		rolls = new ArrayList<Integer>(number);
	}

	public DieRoll(int size) {
		init(null, 1, size, 0);
	}

	public DieRoll(String name, int size) {
		init(name, 1, size, 0);
	}
	
	public DieRoll(String name, int number, int size) {
		init(name, number, size, 0);
	}

	public DieRoll(int number, int size) {
		init(null, number, size, 0);
	}

	public DieRoll(String name, int number, int size, int bonus) {
		init(name, number, size, bonus);
	}

	public DieRoll(int number, int size, int bonus) {
		init(null, number, size, bonus);
	}
	
	private void setName() {
		if (number == 1) {
			if (bonus == 0) {
				name = String.format("d%d", size);
			} else {
				name = String.format("d%d%s%d", size, bonus >= 0 ? "+" : "", bonus);
			}
		} else {
			if (bonus == 0) {
				name = String.format("%dd%d", number, size);
			} else {
				name = String.format("%dd%d%s%d", number, size, bonus >= 0 ? "+" : "", bonus);
			}
		}
	}

	private void setExpandedResult(int rollBonus) {
		StringBuilder sb = new StringBuilder("(");
		
		sb.append(rolls.get(0));
		for (int i = 1; i < rolls.size(); i++) {
			sb.append(" + ");
			sb.append(rolls.get(i));
		}
		sb.append(")");

		if (bonus != 0) {
			sb.append(' ');
			sb.append(bonus >= 0 ? '+' : '-');
			sb.append(' ');
			sb.append(Math.abs(bonus));
		}
			
		if (rollBonus != 0) {
			sb.append(' ');
			sb.append(rollBonus >= 0 ? '+' : '-');
			sb.append(' ');
			sb.append(Math.abs(rollBonus));
		}
		
		expandedResult = sb.toString();
	}

	public int roll(int rollBonus) {
		result = 0;
		rolls.clear();
		
		for (int i = 0; i < number; i++) {
			rolls.add(rand.nextInt(size) + 1);
			result += rolls.get(i);
		}
			
		result += bonus + rollBonus;
		setExpandedResult(rollBonus);
		
		return result;
	}

	public int roll() {
		return roll(0);
	}
}