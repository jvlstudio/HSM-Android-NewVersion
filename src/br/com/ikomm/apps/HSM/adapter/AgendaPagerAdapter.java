package br.com.ikomm.apps.HSM.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.ikomm.apps.HSM.fragment.AgendaFragment;

/**
 * AgendaPagerAdapter.java class.
 * Modified by Rodrigo Cericatto at June 30, 2014.
 */
public class AgendaPagerAdapter extends FragmentPagerAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Integer mEventId;
	private Integer mDatesSize;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public AgendaPagerAdapter(FragmentManager fragmentManager, Integer eventId, Integer datesSize) {
		super(fragmentManager);
		mEventId = eventId;
		mDatesSize = datesSize;
	}
	
	//--------------------------------------------------
	// FragmentPager Life Cycle
	//--------------------------------------------------
	
    @Override
    public Fragment getItem(int position) {
        return AgendaFragment.create(position, mEventId);
    }

	@Override
	public int getCount() {
		return mDatesSize;
	}
}