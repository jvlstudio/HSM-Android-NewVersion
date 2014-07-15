package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.repo.AgendaRepo;
import br.ikomm.hsm.repo.PanelistRepo;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * DetalhePalestraActivity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class DetalhePalestraActivity extends SherlockFragmentActivity implements OnClickListener {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Long mPanelistId;
	private int mEventId = 0;
	
	private PanelistRepo mPanelistRepo;
	private Panelist mPanelist;
	
	private AgendaRepo mAgendaRepo;
	private Agenda mAgenda;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_palestra);

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
			
			if (mPanelist != null) {
				loadFields();
			}
			mAgendaRepo.close();
			mPanelistRepo.close();
			
			// Sets ActionBar.
			ActionBar action = getActionBar();
			action.setLogo(R.drawable.hsm_logo);
			action.setTitle(mPanelist.name);
			action.setDisplayHomeAsUpEnabled(true);
		}
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
	// Methods
	//--------------------------------------------------
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage(String url, ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imageView, cache);
	}
	
	/**
	 * Load fields.
	 */
	private void loadFields() {
		ImageView imagem = (ImageView) findViewById(R.id.imgPalestranteDetalhe);
		setUniversalImage("http://static.tumblr.com/4f4c4d16d483a9db26dd3617ac92601b/c3ujeqe/mk7myuhwp/tumblr_static_istock-potato.jpg", imagem);

		TextView nomePalestrante = (TextView) findViewById(R.id.tDetNomePalestrante);
		nomePalestrante.setText(mPanelist.name);

		TextView especialidade = (TextView) findViewById(R.id.tDetEspecialidade);
		especialidade.setText(mPanelist.name);
		
		// Tratamento de data para utilização na Interface.
		String[] dt_start = mAgenda.date_start.split(" ");
		String[] dt_end = mAgenda.date_end.split(" ");
		String[] date_start_format = dt_start[0].split("-"); 
		dt_start[0] = date_start_format[2]  +"/" + date_start_format[1]  +"/" + date_start_format[0];
		String[] date_end_format = dt_end[0].split("-");
		dt_end[0] = date_end_format[2] + "/"+date_end_format[1]+"/" + date_end_format[0];

		TextView data = (TextView) findViewById(R.id.tDetData);
		data.setText(dt_start[0] + " - " + dt_end[0]);

		TextView horario = (TextView) findViewById(R.id.tDetHorario);
		horario.setText(dt_start[1] + " - " + dt_end[1]);

		TextView resumo = (TextView) findViewById(R.id.tResumoPalestra);
		resumo.setText(mAgenda.theme_title);

		TextView content = (TextView) findViewById(R.id.tDetalhesPalestra);
		content.setText(mAgenda.theme_description);
		
		Button scheduleButton = (Button)findViewById(R.id.btnAgendar);
		scheduleButton.setOnClickListener(this);
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
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		Toast.makeText(DetalhePalestraActivity.this, "Palestra agendada com sucesso", Toast.LENGTH_SHORT).show();
	}	
}