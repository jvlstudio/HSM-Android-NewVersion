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

public class PalestrantesActivity extends Activity implements OnItemClickListener {

	private int event_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palestrantes);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			event_id = extras.getInt("event_id");
		}
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		carregarCampos();
	}

	private void carregarCampos() {
		ListView lista = (ListView) findViewById(R.id.listaPalestrantes);
		lista.setAdapter(new PalestranteAdapter(this, event_id));
		lista.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.palestrantes, menu);
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent _intent = new Intent(this, DetalhePalestraActivity.class);
		_intent.putExtra("panelist_id", arg3);
		_intent.putExtra("event_id", event_id);
		startActivity(_intent);
	}
}