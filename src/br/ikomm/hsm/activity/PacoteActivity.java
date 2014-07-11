package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.PacoteAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * PacoteActivity.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class PacoteActivity extends SherlockFragmentActivity implements OnItemClickListener {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private int mEventId;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pacote);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			mEventId = extras.getInt("event_id");
		}
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
		
		ListView listview = (ListView) findViewById(R.id.listViewPacotes);
		Activity context = this;
		listview.setAdapter(new PacoteAdapter(context, this, mEventId));
		listview.setOnItemClickListener(this);
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
	public void onItemClick(AdapterView<?> s, View view, int position, long id) {
		Intent intent = new Intent(this, PagamentoActivity.class);
		intent.putExtra("passe", id);
		startActivity(intent);
	}
}