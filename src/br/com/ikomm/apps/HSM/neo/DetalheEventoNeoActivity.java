package br.com.ikomm.apps.HSM.neo;

import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.R.layout;
import br.com.ikomm.apps.HSM.R.menu;
import br.ikomm.hsm.AgendaActivity;
import br.ikomm.hsm.DetalheEventoActivity;
import br.ikomm.hsm.PacoteActivity;
import br.ikomm.hsm.PalestrantesActivity;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.model.EventRepo;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DetalheEventoNeoActivity extends FragmentActivity {

	private int id;
	private EventRepo _er;
	private Event _event;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_evento);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			id = extras.getInt("id");
		}
		
		_er = new EventRepo(getBaseContext());
		_er.open();
		_event = _er.getEvent(id);

		if (_event != null) {
			carregarCampos();
		}
		
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);		
		
		addListenerButton();
	}

	private void carregarCampos() {
		// TODO Auto-generated method stub
		final ImageView imgEvento = (ImageView) findViewById(R.id.imgEventoDet);
		TextView txtEvento = (TextView) findViewById(R.id.textEvent);
		TextView txtDescricao = (TextView) findViewById(R.id.textDescriptionEvent);
		Button btnAgenda = (Button) findViewById(R.id.btnAgenda);
		Button btnPasses = (Button) findViewById(R.id.btnPasses);
		Button btnPalestrantes = (Button) findViewById(R.id.btnPalestrante);
		
		txtEvento.setText(_event.name);
		txtDescricao.setText(_event.description);
	}

	private void addListenerButton() {
		// TODO Auto-generated method stub
		Button btnAgenda = (Button) findViewById(R.id.btnAgenda);
		btnAgenda.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalheEventoNeoActivity.this, AgendaActivity.class);
				intent.putExtra("event_id", _event.id);
				intent.putExtra("dates", _event.info_dates);
				
				
				
				startActivity(intent);
			}
		});
		
		Button btnPasses = (Button) findViewById(R.id.btnPasses);
		btnPasses.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalheEventoNeoActivity.this, PacoteActivity.class);
				intent.putExtra("event_id", _event.id);
				startActivity(intent);
			}
		});
		
		Button btnPalestrantes = (Button) findViewById(R.id.btnPalestrante);
		btnPalestrantes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetalheEventoNeoActivity.this, PalestrantesActivity.class);
				intent.putExtra("event_id", _event.id);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_evento_neo, menu);
		return false;
	}


}
