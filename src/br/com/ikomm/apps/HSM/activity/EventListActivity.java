package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.EventsAdapter;
import br.com.ikomm.apps.HSM.manager.ContentManager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * EventListActivity.java class.
 * Modified by Rodrigo Cericatto at July 7, 2014.
 */
public class EventListActivity extends SherlockActivity implements OnItemClickListener {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String EXTRA_EVENT_ID = "event_id";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ListView mListView;
 
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_event_list);
		
		setActionBar();
		setAdapter();
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
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Sets the list adapter.
	 */
	public void setAdapter() {
		EventsAdapter adapter = new EventsAdapter(this, ContentManager.getInstance().getCachedEventList());
		mListView = (ListView) findViewById(R.id.id_list_view);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, EventDetailsActivity.class);
		intent.putExtra(EXTRA_EVENT_ID, id);
		startActivity(intent);
	}
}