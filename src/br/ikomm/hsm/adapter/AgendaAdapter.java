package br.ikomm.hsm.adapter;

import br.ikomm.hsm.fragment.Data1Fragment;
import br.ikomm.hsm.fragment.Data2Fragment;
import br.ikomm.hsm.fragment.Data3Fragment;
import br.ikomm.hsm.fragment.DataFragmentE1D1;
import br.ikomm.hsm.fragment.DataFragmentE1D2;
import br.ikomm.hsm.fragment.DataFragmentE2D1;
import br.ikomm.hsm.fragment.DataFragmentE2D2;
import br.ikomm.hsm.fragment.DataFragmentE3D1;
import br.ikomm.hsm.fragment.DataFragmentE3D2;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class AgendaAdapter extends FragmentPagerAdapter {

	private int id;
	
	public AgendaAdapter(FragmentManager fm, int id) {
		super(fm);
		this.id = id;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Fragment getItem(int position) {
		if (id == 1) {
			switch (position) {
				case 0:
					return new DataFragmentE1D1();
				case 1:
					return new DataFragmentE1D2();
			}

		} else if (id == 2) {
			switch (position) {
				case 0:
					return new DataFragmentE2D1();
				case 1:
					return new DataFragmentE2D2();
			}
		} else if (id == 3) {
			switch (position) {
				case 0:
					return new DataFragmentE3D1();
				case 1:
					return new DataFragmentE3D2();
			}
		} else {
			switch (position) {
				case 0:
					return new Data1Fragment();
				case 1:
					return new Data2Fragment();
				default:
					return new Data3Fragment();
			}
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
