package com.soru.charactertracker.attackinfo;

import java.util.ArrayList;
import java.util.List;

import com.soru.charactertracker.DiceParser;
import com.soru.charactertracker.attackinfo.AttackInfo.hitOutcome;

public class MultipleAttackInfo extends AttackInfo {
	List<AttackInfo> attacks;
	
	MultipleAttackInfo(String name, String hitDice, String damageDice, int critRange, int numAttacks) {
		super(name, hitDice, damageDice, critRange);
		
		attacks = new ArrayList<AttackInfo>(numAttacks);
		for (int i = 0; i < numAttacks; i++)
			attacks.add(new AttackInfo(String.format("%s #%d", name, i + 1), hitDice, damageDice, critRange));
		
		this.name = String.format("%d x %s", numAttacks, name);
	}
	
	@Override
	public void Roll(DiceParser dice, int hitBonus, int dmgBonus, int d6Bonus) {
		for (AttackInfo atk: attacks)
			atk.Roll(dice, hitBonus, dmgBonus, d6Bonus);
	}
	
	@Override
	public List<AttackInfo> getAttackList() {
		List<AttackInfo> ret = new ArrayList<AttackInfo>();
		for (AttackInfo atk: attacks)
			ret.addAll(atk.getAttackList());
		
		return ret;
	}

}