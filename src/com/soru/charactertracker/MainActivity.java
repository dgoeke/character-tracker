package com.soru.charactertracker;

import java.util.List;
import java.util.Vector;

import com.soru.charactertracker.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.soru.charactertracker.CharacterFragment;
import com.soru.charactertracker.trackingitems.PointsCountDownItem;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    List<Fragment> fragments;
	private int hitBonus = 0;
	private int dmgBonus = 0;
	private int d6Bonus = 0;
	private Bundle stateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stateInfo = savedInstanceState;
        if (stateInfo == null) {
        	stateInfo = new Bundle();
        }
        
        this.initializePaging();
    }

    public DiceFragment getDicePage() {
    	mViewPager.setCurrentItem(2, true);
    	return (DiceFragment)fragments.get(2);
    }
    
    public void setHitBonus(int hitBonus) {
    	this.hitBonus = hitBonus;
    }
    
    public void setDmgBonus(int dmgBonus) {
    	this.dmgBonus = dmgBonus;
    }

    public void setD6Bonus(int d6Bonus) {
    	this.d6Bonus = d6Bonus;
    }

    public int getHitBonus() {
    	return hitBonus;
    }
    
    public int getDmgBonus() {
    	return dmgBonus;
    }

    public int getD6Bonus() {
    	return d6Bonus;
    }

    private void initializePaging() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, CharacterFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, AttackFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, DiceFragment.class.getName()));
        
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(super.getSupportFragmentManager(), fragments);

        mViewPager = (ViewPager)super.findViewById(R.id.pager);
        mViewPager.setAdapter(this.mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.addTab(actionBar.newTab().setText("CHARACTER").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("ATTACK").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("DICE").setTabListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    	private List<Fragment> fragments;
    	
    	public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
    		super(fm);
    		this.fragments = fragments;
    	}
    	
    	@Override
    	public Fragment getItem(int position) {
    		return this.fragments.get(position);
    	}
    	
    	@Override
    	public int getCount() {
    		return this.fragments.size();
    	}
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(stateInfo);
    	super.onSaveInstanceState(outState);
    }

    public Bundle getState() {
    	return stateInfo;
    }
}
