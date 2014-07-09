package br.ikomm.hsm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.ikomm.hsm.fragment.AgendaFragment;

/**
 * AgendaAdapter.java class.
 * Modified by Rodrigo Cericatto at June 30, 2014.
 */
public class AgendaAdapter extends FragmentPagerAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Integer mEventId;
	private Integer mDatesSize;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public AgendaAdapter(FragmentManager fragmentManager, Integer eventId, Integer datesSize) {
		super(fragmentManager);
		mEventId = eventId;
		mDatesSize = datesSize;
	}
	
	//--------------------------------------------------
	// FragmentPager Life Cycle
	//--------------------------------------------------
	
	/*
	@Override
	public Fragment getItem(int position) {
		return new AgendaFragment(mEventId, mPosition);
	}
	*/
	
    @Override
    public Fragment getItem(int position) {
        return AgendaFragment.create(position, mEventId);
    }

	@Override
	public int getCount() {
		return mDatesSize;
	}
}