package br.ikomm.hsm;

import android.app.ActionBar;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.AgendaRepo;
import br.ikomm.hsm.model.Palestra;
import br.ikomm.hsm.model.PalestraRepository;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.model.PanelistRepo;

public class DetalhePalestraActivity extends FragmentActivity {
	private Long panelist_id;
	private int event_id = 0;
	private Panelist _panelist;
	private Agenda _agenda;
	private PanelistRepo _pr;
	private AgendaRepo _ar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_palestra);

		addListenerOnButton();

		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			panelist_id = extras.getLong("panelist_id");
			event_id = extras.getInt("event_id");
		}
		
		if (event_id > 0 && panelist_id > 0) {
			_pr = new PanelistRepo(getApplication());
			_pr.open();
			_panelist = _pr.getPanelist(panelist_id);
			
			_ar = new AgendaRepo(getApplication());
			_ar.open();
			_agenda = _ar.detailAgenda(panelist_id, event_id);
			
			if(_panelist != null){
				carregaCampos();
			}
			_ar.close();
			_pr.close();
		}
	}

	private void carregaCampos() {
		ImageView imagem = (ImageView) findViewById(R.id.imgPalestranteDetalhe);

		TextView nomePalestrante = (TextView) findViewById(R.id.tDetNomePalestrante);
		nomePalestrante.setText(_panelist.name);

		TextView especialidade = (TextView) findViewById(R.id.tDetEspecialidade);
		especialidade.setText(_panelist.name);
		
		// Tratamento de data para utilização na Interface.
		String[] dt_start = _agenda.date_start.split(" ");
		String[] dt_end = _agenda.date_end.split(" ");
		String[] date_start_format = dt_start[0].split("-"); 
		dt_start[0] = date_start_format[2]+"/"+date_start_format[1]+"/"+date_start_format[0];
		String[] date_end_format = dt_end[0].split("-");
		dt_end[0] = date_end_format[2]+"/"+date_end_format[1]+"/"+date_end_format[0];
		

		TextView data = (TextView) findViewById(R.id.tDetData);
		data.setText(dt_start[0] + " - " + dt_end[0]);

		TextView horario = (TextView) findViewById(R.id.tDetHorario);
		horario.setText(dt_start[1] + " - " + dt_end[1]);

		TextView resumo = (TextView) findViewById(R.id.tResumoPalestra);
		resumo.setText(_agenda.theme_title);

		TextView content = (TextView) findViewById(R.id.tDetalhesPalestra);
		content.setText(_agenda.theme_description);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setTitle(_panelist.name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_palestra, menu);
		return false;
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
			// TODO: handle exception
		}
	}
	 */
}