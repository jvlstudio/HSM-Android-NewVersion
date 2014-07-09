package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.repo.AgendaRepo;
import br.ikomm.hsm.repo.PanelistRepo;

/**
 * DetalhePalestraActivity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class DetalhePalestraActivity extends FragmentActivity {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Long mPanelistId;
	private int mEventId = 0;
	private Panelist mPanelist;
	private Agenda mAgenda;
	private PanelistRepo mPanelistRepo;
	private AgendaRepo mAgendaRepo;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_palestra);

		addListenerOnButton();
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			mPanelistId = extras.getLong("panelist_id");
			mEventId = extras.getInt("event_id");
		}
		
		if (mEventId > 0 && mPanelistId > 0) {
			mPanelistRepo = new PanelistRepo(getApplication());
			mPanelistRepo.open();
			mPanelist = mPanelistRepo.getPanelist(mPanelistId);
			
			mAgendaRepo = new AgendaRepo(getApplication());
			mAgendaRepo.open();
			mAgenda = mAgendaRepo.detailAgenda(mPanelistId, mEventId);
			
			if(mPanelist != null){
				carregaCampos();
			}
			mAgendaRepo.close();
			mPanelistRepo.close();
		}
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private void carregaCampos() {
		ImageView imagem = (ImageView) findViewById(R.id.imgPalestranteDetalhe);

		TextView nomePalestrante = (TextView) findViewById(R.id.tDetNomePalestrante);
		nomePalestrante.setText(mPanelist.name);

		TextView especialidade = (TextView) findViewById(R.id.tDetEspecialidade);
		especialidade.setText(mPanelist.name);
		
		// Tratamento de data para utilização na Interface.
		String[] dt_start = mAgenda.date_start.split(" ");
		String[] dt_end = mAgenda.date_end.split(" ");
		String[] date_start_format = dt_start[0].split("-"); 
		dt_start[0] = date_start_format[2]+"/"+date_start_format[1]+"/"+date_start_format[0];
		String[] date_end_format = dt_end[0].split("-");
		dt_end[0] = date_end_format[2]+"/"+date_end_format[1]+"/"+date_end_format[0];
		

		TextView data = (TextView) findViewById(R.id.tDetData);
		data.setText(dt_start[0] + " - " + dt_end[0]);

		TextView horario = (TextView) findViewById(R.id.tDetHorario);
		horario.setText(dt_start[1] + " - " + dt_end[1]);

		TextView resumo = (TextView) findViewById(R.id.tResumoPalestra);
		resumo.setText(mAgenda.theme_title);

		TextView content = (TextView) findViewById(R.id.tDetalhesPalestra);
		content.setText(mAgenda.theme_description);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setTitle(mPanelist.name);
	}

	private void addListenerOnButton() {
		findViewById(R.id.btnAgendar).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				addEvent();
				Toast.makeText(DetalhePalestraActivity.this, "Palestra agendada com sucesso", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	 * REMOVIDO TEMPORARIAMENTE.
	
	@SuppressLint("NewApi")
	protected void addEvent() {
		try {
			long calID = 1;
			long startMillis = 0;
			long endMillis = 0;

			Calendar beginTime = Calendar.getInstance();

			Calendar endTime = Calendar.getInstance();

			endMillis = endTime.getTimeInMillis();

			String[] init = palestra.hour_init.split("h");
			int initHour = Integer.valueOf(init[0]);
			int initMin = Integer.valueOf(init[1]);
			String[] fim = palestra.hour_final.split("h");
			int fimHour = Integer.valueOf(fim[0]);
			int fimMin = Integer.valueOf(fim[1]);

			if (palestra.day.contains("4")) {
				beginTime.set(2013, Calendar.NOVEMBER, 4, initHour, initMin);
				endTime.set(2013, Calendar.NOVEMBER, 4, fimHour, fimMin);
			}
			if (palestra.day.contains("5")) {
				beginTime.set(2013, Calendar.NOVEMBER, 5, initHour, initMin);
				endTime.set(2013, Calendar.NOVEMBER, 5, fimHour, fimMin);
			}
			if (palestra.day.contains("6")) {
				beginTime.set(2013, Calendar.NOVEMBER, 6, initHour, initMin);
				endTime.set(2013, Calendar.NOVEMBER, 6, fimHour, fimMin);
			}

			ContentResolver cr = getContentResolver();
			ContentValues values = new ContentValues();
			
			values.put(Events.DTSTART, beginTime.getTimeInMillis());
			values.put(Events.DTEND, endTime.getTimeInMillis());
			values.put("allDay", 0);
			values.put("hasAlarm", 0);
			
			values.put(Events.TITLE, palestra.title);
			values.put(Events.DESCRIPTION, palestra.subtitle);
			values.put(Events.CALENDAR_ID, calID);
			
			TimeZone timeZone = TimeZone.getDefault();
			values.put(Events.EVENT_TIMEZONE, timeZone.getID());

			Uri uri = cr.insert(Events.CONTENT_URI, values);
			
			//get the event ID that is the last element in the Uri
			long eventID = Long.parseLong(uri.getLastPathSegment());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */
}