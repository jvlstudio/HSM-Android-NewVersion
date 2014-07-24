package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
public class AgendaActivity extends SherlockFragmentActivity implements OnClickListener, TabListener, OnPageChangeListener {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private ViewPager mViewPager;
	private Integer mEventId = 0;
	private String[] mDates;
	
	private com.actionbarsherlock.app.ActionBar mSherlockActionBar;
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		
		getExtras();
		setViewPager();
		setActionBar();
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
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mEventId = extras.getInt("event_id");
			mDates = extras.getString("dates").replace("|", "-").split("-");
		}
	}
	
	/**
	 * Sets the {@link ViewPager}.
	 */
	public void setViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.viewPagerAgenda);
		mViewPager.setAdapter(new AgendaPagerAdapter(getSupportFragmentManager(), mEventId, mDates.length));
		mViewPager.setOnPageChangeListener(this);
	}
	
	/**
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		// Changes the current ActionBar.
		ActionBar actionBar = getActionBar();
		actionBar.setLogo(R.drawable.hsm_logo);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// Changes the Sherlock ActionBar.
		mSherlockActionBar = getSupportActionBar();
		mSherlockActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 0; i < mDates.length; i++) {
        	mSherlockActionBar.addTab(mSherlockActionBar.newTab().setText(mDates[i]).setTabListener(this));
		}
	}
	
	//--------------------------------------------------
	// Click Listeners
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		startActivity(new Intent(this, LectureDetailsActivity.class));
	}

	//--------------------------------------------------
	// Tab Listeners
	//--------------------------------------------------
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {}

	//--------------------------------------------------
	// Page Changed Listeners
	//--------------------------------------------------
	
	@Override
	public void onPageScrollStateChanged(int state) {}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

	@Override
	public void onPageSelected(int position) {
		mSherlockActionBar.setSelectedNavigationItem(position);
	}
}