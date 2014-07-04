package br.ikomm.hsm;

import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.PacoteAdapter;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * PacoteActivity.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class PacoteActivity extends FragmentActivity implements OnItemClickListener {

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
		
		ListView listview = (ListView) findViewById(R.id.listViewPacotes);
		Activity context = this;
		listview.setAdapter(new PacoteAdapter(context, this, mEventId));
		listview.setOnItemClickListener(this);
	}

	//--------------------------------------------------
	// Menu Methods
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pacote, menu);
		return false;
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