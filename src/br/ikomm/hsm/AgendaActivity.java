package br.ikomm.hsm;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.AgendaAdapter;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class AgendaActivity extends SherlockFragmentActivity implements OnClickListener, TabListener {

	private ViewPager viewPager;
	private int event_id = 0;
	String[] dates;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			event_id = extras.getInt("event_id");
			dates = extras.getString("dates").split("-");
		}
		
		Activity context = this;
		viewPager = (ViewPager) findViewById(R.id.viewPagerAgenda);
		viewPager.setAdapter(new AgendaAdapter(getSupportFragmentManager(), event_id));
		
		
		final com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int i = 0; i < dates.length; i++) {
			actionBar.addTab(actionBar.newTab().setText(dates[i]).setTabListener(this));
		}
        
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
              @Override
              public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
              }
        });
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, DetalhePalestraActivity.class));
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

}
