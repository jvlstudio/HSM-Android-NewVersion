package br.com.ikomm.apps.HSM;

import br.ikomm.hsm.adapter.BDWAgendaAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public class BDWAgendaActivity extends SherlockFragmentActivity implements OnClickListener, TabListener{

	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bdwagenda);
		
		ActionBar action = getSupportActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		Activity context = this;
		viewPager = (ViewPager) findViewById(R.id.viewPagerBDWAgenda);
		viewPager.setAdapter(new BDWAgendaAdapter(getSupportFragmentManager()));
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("4 Novembro").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("5 Novembro").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("6 Novembro").setTabListener(this));
		
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
