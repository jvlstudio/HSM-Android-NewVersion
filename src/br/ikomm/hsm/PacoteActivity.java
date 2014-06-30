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

public class PacoteActivity extends FragmentActivity implements OnItemClickListener {

	private int event_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pacote);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			event_id = extras.getInt("event_id");
		}
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		ListView listview = (ListView) findViewById(R.id.listViewPacotes);
		Activity context = this;
		listview.setAdapter(new PacoteAdapter(context, this, event_id));
		
		listview.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pacote, menu);
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> s, View arg1, int pos, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, PagamentoActivity.class);
		intent.putExtra("passe", id);
		startActivity(intent);
	}

}
