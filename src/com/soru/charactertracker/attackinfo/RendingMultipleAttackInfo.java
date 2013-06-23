package com.soru.charactertracker.attackinfo;

import java.util.ArrayList;
import java.util.List;

import com.soru.charactertracker.DiceParser;

public class RendingMultipleAttackInfo extends MultipleAttackInfo {
	int hitsToRend;
	AttackInfo rend;
	
	public RendingMultipleAttackInfo(String name, String hitDice, String damageDice, int critRange, int numAttacks, int hitsToRend, AttackInfo rend) {
		super(name, hitDice, damageDice, critRange, numAttacks);
		this.hitsToRend = hitsToRend;
		this.name = String.format("%s, %s", this.name, rend.name);
		this.rend = rend;
	}
	
	@Override
	public void Roll(DiceParser dice, int hitBonus, int dmgBonus, int d6Bonus) {
		super.Roll(dice, hitBonus, dmgBonus, d6Bonus);
		rend.Roll(dice, hitBonus, dmgBonus, d6Bonus);
	}

	@Override
	public void finalizeOutcomes() {
		int hits = 0;
		
		for (AttackInfo atk: attacks) {
			if (atk.outcome == hitOutcome.HIT || atk.outcome == hitOutcome.CRIT)
				hits++;
		}
		
		rend.outcome = (hits >= hitsToRend) ? hitOutcome.HIT : hitOutcome.MISS;
	}

	@Override
	public List<AttackInfo> getAttackList() {
		List<AttackInfo> ret = super.getAttackList();
		ret.add(rend);
		return ret;
	}
}