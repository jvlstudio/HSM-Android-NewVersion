package br.ikomm.hsm.adapter;

import br.ikomm.hsm.fragment.BDWData1Fragment;
import br.ikomm.hsm.fragment.BDWData2Fragment;
import br.ikomm.hsm.fragment.BDWData3Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BDWAgendaAdapter extends FragmentPagerAdapter {

	
	
	public BDWAgendaAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		
		switch (position) {
		case 0:
			return new BDWData1Fragment();
		case 1:
			return new BDWData2Fragment();
		default:
			return new BDWData3Fragment();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	

}
