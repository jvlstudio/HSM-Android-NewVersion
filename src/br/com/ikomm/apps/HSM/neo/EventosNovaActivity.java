package br.com.ikomm.apps.HSM.neo;

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
import br.ikomm.hsm.adapter.EventosAdapter;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.repo.EventRepo;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * EventosNovaActivity.java class.
 * Modified by Rodrigo Cericatto at July 7, 2014.
 */
public class EventosNovaActivity extends SherlockActivity implements OnItemClickListener {
	
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
		setContentView(R.layout.activity_eventos_nova);
		
		ActionBar action = getActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		
		
		EventRepo eventRepo = new EventRepo(getBaseContext());
		List<Event> list = new ArrayList<Event>();
		eventRepo.open();
		list = eventRepo.getAllEvent();
		eventRepo.close();
		
		EventosAdapter adapter = new EventosAdapter(this, list);
		mListView = (ListView) findViewById(R.id.id_list_view);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
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
	// Listeners
	//--------------------------------------------------

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, DetalheEventoNeoActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
}