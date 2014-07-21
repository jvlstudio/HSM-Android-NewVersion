package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.AgendaPagerAdapter;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * AgendaPagerAdapter.java class.
 * Modified by Rodrigo Cericatto at June 30, 2014.
 */
public class AgendaActivity extends SherlockFragmentActivity implements OnClickListener, TabListener {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private ViewPager mViewPager;
	private int mEventId = 0;
	private String[] mDates;
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mEventId = extras.getInt("event_id");
			mDates = extras.getString("dates").replace("|", "-").split("-");
		}
		
		mViewPager = (ViewPager) findViewById(R.id.viewPagerAgenda);
		mViewPager.setAdapter(new AgendaPagerAdapter(getSupportFragmentManager(), mEventId, mDates.length));
		
		final com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int i = 0; i < mDates.length; i++) {
			actionBar.addTab(actionBar.newTab().setText(mDates[i]).setTabListener(this));
		}
        
		mViewPager.setOnPageChangeListener(
			new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					actionBar.setSelectedNavigationItem(position);
				}
			}
		);
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.application_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//--------------------------------------------------
	// Click Listener
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		startActivity(new Intent(this, DetalhePalestraActivity.class));
	}
	
	//--------------------------------------------------
	// Tab Listener
	//--------------------------------------------------
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}
}
