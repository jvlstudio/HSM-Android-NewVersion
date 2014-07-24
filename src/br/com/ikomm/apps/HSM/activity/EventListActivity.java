package br.com.ikomm.apps.HSM.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.EventsAdapter;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.repo.EventRepo;
import br.com.ikomm.apps.HSM.utils.FileUtils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * EventListActivity.java class.
 * Modified by Rodrigo Cericatto at July 7, 2014.
 */
public class EventListActivity extends SherlockActivity implements OnItemClickListener {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ListView mListView;
	private FileUtils mFileManager = new FileUtils();

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_event_list);
		
		ActionBar action = getActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		setAdapter();
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
	 * Sets the list adapter.
	 */
	public void setAdapter() {
		String path = mFileManager.createDir(FileUtils.CACHE_DIR);
		EventsAdapter adapter = new EventsAdapter(this, getEventList(), path);
		mListView = (ListView) findViewById(R.id.id_list_view);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}
	
	/**
	 * Gets the {@link Event} list.
	 * 
	 * @return
	 */
	public List<Event> getEventList() {
		EventRepo eventRepo = new EventRepo(getBaseContext());
		List<Event> list = new ArrayList<Event>();
		eventRepo.open();
		list = eventRepo.getAllEvent();
		eventRepo.close();
		
		return list;
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, EventDetailsActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
}