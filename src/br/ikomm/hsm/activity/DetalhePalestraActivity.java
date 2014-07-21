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
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/panelists/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Long mPanelistId;
	private Integer mEventId = 0;
	
	private Panelist mPanelist;
	private Agenda mAgenda;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_palestra);

		getExtras();
		getData();
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
	 * Gets the current {@link Panelist} and {@link Agenda}. 
	 */
	public void getData() {
		if (mEventId > 0 && mPanelistId > 0) {
			getCurrentPanelist();
			getCurrentAgenda();
			
			if (mPanelist != null) {
				loadFields();
			}
			
			setActionBar();
		}
	}
	
	/**
	 * Gets the current {@link Panelist}.
	 */
	public void getCurrentPanelist() {
		PanelistRepo panelistRepo = new PanelistRepo(getApplication());
		panelistRepo.open();
		mPanelist = panelistRepo.getPanelist(mPanelistId);
		panelistRepo.close();
	}
	
	/**
	 * Gets the current {@link Agenda}.
	 */
	public void getCurrentAgenda() {
		AgendaRepo agendaRepo = new AgendaRepo(getApplication());
		agendaRepo.open();
		mAgenda = agendaRepo.byEventAndPanelist(mPanelistId, mEventId);
		agendaRepo.close();
	}
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			mPanelistId = extras.getLong("panelist_id");
			mEventId = extras.getInt("event_id");
		}
	}
	
	/**
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setTitle(mPanelist.name);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
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
		// Format dates.
		String[] dateStart = mAgenda.date_start.split(" ");
		String[] dateEnd = mAgenda.date_end.split(" ");
		String[] dateStartFormat = dateStart[0].split("-"); 
		dateStart[0] = dateStartFormat[2]  + "/" + dateStartFormat[1]  + "/" + dateStartFormat[0];
		String[] dateEndFormat = dateEnd[0].split("-");
		dateEnd[0] = dateEndFormat[2] + "/" + dateEndFormat[1] + "/" + dateEndFormat[0];

		// Load fields.
		TextView dateTextView = (TextView)findViewById(R.id.id_date_text_view);
		dateTextView.setText(dateStart[0] + " - " + dateEnd[0]);

		TextView timeTextView = (TextView)findViewById(R.id.id_time_text_view);
		timeTextView.setText(dateStart[1] + " - " + dateEnd[1]);

		ImageView panelistImageView = (ImageView)findViewById(R.id.id_panelist_image_view);
		setUniversalImage(URL + mPanelist.picture, panelistImageView);

		TextView panelistNameTextView = (TextView)findViewById(R.id.id_panelist_name_text_view);
		panelistNameTextView.setText(mPanelist.name);

		TextView specialtyTextView = (TextView)findViewById(R.id.id_specialty_text_view);
		specialtyTextView.setText(mPanelist.name);
		
		TextView abstractTextView = (TextView)findViewById(R.id.id_abstract_text_view);
		abstractTextView.setText(mAgenda.theme_title);

		TextView contentTextView = (TextView)findViewById(R.id.id_content_text_view);
		contentTextView.setText(mAgenda.theme_description);
		
		Button scheduleButton = (Button)findViewById(R.id.id_schedule_button);
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