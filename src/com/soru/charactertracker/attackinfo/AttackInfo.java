package com.soru.charactertracker.attackinfo;

import java.util.ArrayList;
import java.util.List;

import org.petitparser.context.Context;
import org.petitparser.context.Result;

import android.widget.Toast;

import com.soru.charactertracker.DiceParser;

public class AttackInfo {
	public String name;
	public String hitDice;
	public String damageDice;
	public int critRange;
	
	public boolean error;
	public boolean isThreat;
	public int hit;
	public int hitConfirm;
	public int damage;
	public int critDamage;
	public String hitResult;
	public String hitConfirmResult;
	public String damageResult;
	public String critDamageResult;
	public boolean hideFromHitScreen = false;
	
	public enum hitOutcome {
		HIT, MISS, CRIT;
	};
	
	public hitOutcome outcome;
	public boolean isCritMiss;
	private DiceParser lastDice;
	private int lastHitBonus;
	private int lastDmgBonus;
	private int lastD6Bonus;
	
	public String toString() {
		return name;
	}
	
	public AttackInfo(String name, String hitDice, String damageDice, int critRange) {
		this.name = name;
		this.hitDice = hitDice;
		this.damageDice	= damageDice;
		this.critRange = critRange;
	}
	
	private String generateRollString(String base, int bonus, int bonusD6) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(base);
		
		if (bonus != 0) {
			sb.append(" + ");
			sb.append(bonus);
		}
		
		if (bonusD6 != 0) {
			sb.append(" + ");
			sb.append(bonusD6);
			sb.append("d6");
		}
		
		return sb.toString();
	}
	
	public void Reroll() {
		Roll(lastDice, lastHitBonus, lastDmgBonus, lastD6Bonus);
	}
	
	public void RerollConfirm() {
		Context lastHitContext = new Context(generateRollString(hitDice, lastHitBonus, 0));
		
		hitConfirm = lastDice.parse(lastHitContext).get();
		hitConfirmResult = lastDice.getRollAsString();
	}

	public void Roll(DiceParser dice, int hitBonus, int dmgBonus, int d6Bonus) {
		//dice.rolls.clear();
		dice = new DiceParser();
		
		lastDice = dice;
		lastHitBonus = hitBonus;
		lastDmgBonus = dmgBonus;
		lastD6Bonus = d6Bonus;

		Context hitContext = new Context(generateRollString(hitDice, hitBonus, 0));
		Context dmgContext = new Context(generateRollString(damageDice, dmgBonus, d6Bonus));
		
		Result result = dice.parse(hitContext);
		if ((error = result.isFailure()) == true) 
			return;
		
		hit = result.get();
		hitResult = dice.getRollAsString();
		
		if (dice.rolls.get(0) >= critRange) {
			isThreat = true;
			hitConfirm = dice.parse(hitContext).get();
			hitConfirmResult = dice.getRollAsString();
		} else {
			isThreat = false;
			isCritMiss = (dice.rolls.get(0) == 1);
		}
		
		Result dmgResult = dice.parse(dmgContext);
		if ((error = result.isFailure()) == true) 
			return;
		
		damage = dmgResult.get();
		damageResult = dice.getRollAsString();
		critDamage = damage + (Integer)dice.parse(dmgContext).get();
		critDamageResult = String.format("%s + %s", damageResult, dice.getRollAsString());
	}

	public void resolveWithAc(int ac) {
		if (isCritMiss)
			outcome = hitOutcome.MISS;
		else if (isThreat && hitConfirm >= ac)
			outcome = hitOutcome.CRIT;
		else if (isThreat || hit >= ac) 
			outcome = hitOutcome.HIT;
		else
			outcome = hitOutcome.MISS;
	}
	
	public List<AttackInfo> getAttackList() {
		List<AttackInfo> ret = new ArrayList<AttackInfo>();
		ret.add(this);
		return ret;
	}

	public void finalizeOutcomes() {
	}

}
