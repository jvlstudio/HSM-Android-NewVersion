package br.com.ikomm.apps.HSM.neo;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.activity.AgendaActivity;
import br.ikomm.hsm.activity.PacoteActivity;
import br.ikomm.hsm.activity.PalestrantesActivity;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.repo.EventRepo;

/**
 * DetalheEventoNeoActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class DetalheEventoNeoActivity extends FragmentActivity {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private Long mId;
	private EventRepo mEventRepo;
	private Event mEvent;
	
	private Button mAgendaButton;
	private Button mPassesButton;
	private Button mPalestrantesButton;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_evento);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mId = extras.getLong("id");
		}
		
		mEventRepo = new EventRepo(getBaseContext());
		mEventRepo.open();
		mEvent = mEventRepo.getEvent(mId);

		if (mEvent != null) {
			carregarCampos();
		}
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);		
		addListenerButton();
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private void carregarCampos() {
		TextView txtEvento = (TextView) findViewById(R.id.textEvent);
		txtEvento.setText(mEvent.name);

		TextView txtDescricao = (TextView) findViewById(R.id.textDescriptionEvent);
		txtDescricao.setText(mEvent.description);
		
		mAgendaButton = (Button) findViewById(R.id.btnAgenda);
		mPassesButton = (Button) findViewById(R.id.btnPasses);
		mPalestrantesButton = (Button) findViewById(R.id.btnPalestrante);
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	private void addListenerButton() {
		mAgendaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DetalheEventoNeoActivity.this, AgendaActivity.class);
				intent.putExtra("event_id", mEvent.id);
				intent.putExtra("dates", mEvent.info_dates);
				startActivity(intent);
			}
		});
		
		mPassesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DetalheEventoNeoActivity.this, PacoteActivity.class);
				intent.putExtra("event_id", mEvent.id);
				startActivity(intent);
			}
		});
		
		mPalestrantesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DetalheEventoNeoActivity.this, PalestrantesActivity.class);
				intent.putExtra("event_id", mEvent.id);
				startActivity(intent);
			}
		});
	}
}