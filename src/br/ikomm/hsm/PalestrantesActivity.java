package br.ikomm.hsm;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.PalestranteAdapter;

/**
 * PalestrantesActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class PalestrantesActivity extends Activity implements OnItemClickListener {

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
		setContentView(R.layout.activity_palestrantes);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			mEventId = extras.getInt("event_id");
		}
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		carregarCampos();
	}

	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.palestrantes, menu);
		return false;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private void carregarCampos() {
		ListView lista = (ListView) findViewById(R.id.listaPalestrantes);
		lista.setAdapter(new PalestranteAdapter(this, mEventId));
		lista.setOnItemClickListener(this);
	}
	
	//--------------------------------------------------
	// Liste
	//--------------------------------------------------
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Intent intent = new Intent(this, DetalhePalestraActivity.class);
		intent.putExtra("panelist_id", id);
		intent.putExtra("event_id", mEventId);
		startActivity(intent);
	}
}