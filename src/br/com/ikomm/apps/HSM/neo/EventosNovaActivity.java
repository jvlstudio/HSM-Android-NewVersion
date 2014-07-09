package br.com.ikomm.apps.HSM.neo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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

/**
 * EventosNovaActivity.java class.
 * Modified by Rodrigo Cericatto at July 7, 2014.
 */
public class EventosNovaActivity extends Activity implements OnItemClickListener {
	
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
	// Listeners
	//--------------------------------------------------

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, DetalheEventoNeoActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
}