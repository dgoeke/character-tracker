package com.soru.charactertracker.trackingitems;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.view.View;

import com.google.common.base.Joiner;
import com.soru.charactertracker.DiceFragment;
import com.soru.charactertracker.DiceParser;
import com.soru.charactertracker.HitDialog;
import com.soru.charactertracker.MainActivity;
import com.soru.charactertracker.attackinfo.AttackInfo;

public class FullAttackItem extends TrackingItem {
	String attackString;
	String damageString;
	String name = null;
	List<AttackInfo> attacks = new ArrayList<AttackInfo>();
	private int dmgBonus;
	private int hitBonus;
	private int d6Bonus;

	public FullAttackItem(Fragment parent, List<AttackInfo> attacks) {
		super(parent);
		this.attacks.addAll(attacks);
	}

	public FullAttackItem(Fragment parent, String name, List<AttackInfo> attacks) {
		super(parent);
		this.name = name;
		this.attacks.addAll(attacks);
	}

	@Override
	public
	void createView() {
		expandTrackingItemLayout();
		Joiner j = Joiner.on(", ");

		setName(name == null ? "Full Attack" : name);
		setSubtext(j.join(attacks));
		setValue("");
	}

	public void getHitDmgBonuses() {
		MainActivity act = (MainActivity)parent.getActivity();
		hitBonus = act.getHitBonus();
		dmgBonus = act.getDmgBonus();
		d6Bonus = act.getD6Bonus();
	}
	
	@Override
	public void onClick(View v) {
		DiceParser d = new DiceParser();
		getHitDmgBonuses();
		List<AttackInfo> allAttacks = new ArrayList<AttackInfo>();

		for (AttackInfo atk: attacks) {
			atk.Roll(d, hitBonus, dmgBonus, d6Bonus);
			allAttacks.addAll(atk.getAttackList());
		}
		
		HitDialog dlg = new HitDialog(parent.getView().getContext(), allAttacks);
		dlg.show(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (AttackInfo atk: attacks)
					atk.finalizeOutcomes();
				
				displayAttackResults();
			}
		});
	}
	
	protected void displayAttackResults() {
		MainActivity act = (MainActivity)parent.getActivity();
		DiceFragment dicePage = act.getDicePage();
		List<AttackInfo> allAttacks = new ArrayList<AttackInfo>();
		int totalDmg = 0;
		int crits = 0, hits = 0, misses = 0;

		for (AttackInfo atk: attacks)
			allAttacks.addAll(atk.getAttackList());

		for (AttackInfo attackInfo: allAttacks) {
			if (attackInfo.hideFromHitScreen) {
				int dmg = 0;
				
				switch (attackInfo.outcome) {
				case CRIT:	dmg = attackInfo.critDamage; break;
				case HIT:	dmg = attackInfo.damage; break;
				case MISS:	break;
				}
				
				totalDmg += dmg;

				dicePage.addAttackResultView(dmg, 
						String.format("%s: %s", attackInfo.name, attackInfo.outcome.toString()), attackInfo.damageResult);
			} else {
				switch (attackInfo.outcome) {
				case CRIT:
					totalDmg += attackInfo.critDamage;
					crits++;
					
					dicePage.addAttackResultView(attackInfo.critDamage, 
							String.format("%s: %s (%s = %d)", attackInfo.name, attackInfo.outcome.toString(), attackInfo.hitConfirmResult, attackInfo.hitConfirm),
							attackInfo.critDamageResult);
					break;
					
				case HIT:
					hits++;
					totalDmg += attackInfo.damage;
	
					dicePage.addAttackResultView(attackInfo.damage, 
							String.format("%s: %s (%s = %d)", attackInfo.name, attackInfo.outcome.toString(), attackInfo.hitResult, attackInfo.hit),
							attackInfo.damageResult);
					break;
					
				case MISS:
					misses++;
					
					dicePage.addAttackResultView(0, 
							String.format("%s: %s (%s = %d)", attackInfo.name, attackInfo.outcome.toString(), attackInfo.hitResult, attackInfo.hit),
							attackInfo.damageResult);
					break;
				}
			}
		}

		dicePage.addAttackResultView(totalDmg, 
				String.format("%s: Total", name),
				String.format("%d crit%s, %d hit%s, %d miss%s", 
						crits, crits == 1 ? "" : "s", 
						hits, hits == 1 ? "" : "s",
						misses, misses== 1 ? "" : "es"
						));
	}
}
