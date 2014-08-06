package br.com.ikomm.apps.HSM.activity;

import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.PanelistGridViewAdapter;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.model.Panelist;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * PanelistActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class PanelistActivity extends SherlockActivity implements OnItemClickListener {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String EXTRA_PANELIST_ID = "panelist_id";
	public static final String EXTRA_EVENT_ID = "event_id";
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private Integer mEventId;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_panelist);

		setActionBar();
		getExtras();
		setGridView();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_application, menu);
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
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mEventId = extras.getInt(EventDetailsActivity.EXTRA_EVENT_ID);
		}
	}
	
	/**
	 * Sets the {@link GridView}.
	 */
	private void setGridView() {
		// Gets the Panelist list.
		List<Panelist> panelistList = QueryHelper.getPanelistListByEvent(mEventId);
		
		// Sets the GridView.
		GridView gridView = (GridView)findViewById(R.id.id_list_view);
		gridView.setAdapter(new PanelistGridViewAdapter(this, panelistList));
		gridView.setOnItemClickListener(this);
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Intent intent = new Intent(this, LectureDetailsActivity.class);
		intent.putExtra(EXTRA_PANELIST_ID, id);
		intent.putExtra(EXTRA_EVENT_ID, mEventId);
		startActivity(intent);
	}
}