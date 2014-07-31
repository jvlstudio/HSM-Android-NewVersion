package br.com.ikomm.apps.HSM.activity;

import java.util.GregorianCalendar;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.utils.DateUtils;
import br.com.ikomm.apps.HSM.utils.StringUtils;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * LectureDetailsActivity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class LectureDetailsActivity extends SherlockFragmentActivity implements OnClickListener {
	
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecture_details);

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
				setLayout();
			}
			
			setActionBar();
		} else {
			ActionBar action = getActionBar();
			action.setLogo(R.drawable.hsm_logo);
			action.setTitle("Algum erro ocorreu...");
			action.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	/**
	 * Gets the current {@link Panelist}.
	 */
	public void getCurrentPanelist() {
//		PanelistRepo panelistRepo = new PanelistRepo(getApplication());
//		panelistRepo.open();
//		mPanelist = panelistRepo.getPanelist(mPanelistId);
//		panelistRepo.close();
		mPanelist = QueryHelper.getPanelist(mPanelistId);
	}
	
	/**
	 * Gets the current {@link Agenda}.
	 */
	public void getCurrentAgenda() {
//		AgendaRepo agendaRepo = new AgendaRepo(getApplication());
//		agendaRepo.open();
//		mAgenda = agendaRepo.byEventAndPanelist(mPanelistId, mEventId);
//		agendaRepo.close();
		mAgenda = QueryHelper.getAgendaByEventAndPanelist(mPanelistId, mEventId);
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
	 * Sets the layout.
	 */
	public void setLayout() {
		// Format dates.
		if (StringUtils.isEmpty(mAgenda.getDateStart())) {
			Toast.makeText(this, "Palestrante n‹o associado corretamente ˆ Evento.", Toast.LENGTH_LONG).show();
		} else {
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
		}
		
		Button scheduleButton = (Button)findViewById(R.id.id_schedule_button);
		scheduleButton.setOnClickListener(this);
	}

	/**
	 * Adds the {@link Event}.
	 */
	protected void addIntentEvent() {
		Intent calIntent = new Intent(Intent.ACTION_INSERT); 
		calIntent.setType("vnd.android.cursor.item/event");    
		calIntent.putExtra(Events.TITLE, mAgenda.theme_title); 
		calIntent.putExtra(Events.DESCRIPTION, mAgenda.theme_description); 
	
		String startAgenda = mAgenda.date_start;
		String endAgenda = mAgenda.date_end;
		Integer[] startValues = DateUtils.stringToDate(startAgenda);
		Integer[] endValues = DateUtils.stringToDate(endAgenda);
		
		GregorianCalendar startDate = new GregorianCalendar();
		startDate.set(startValues[0], startValues[1] - 1, startValues[2], startValues[3], startValues[4], 0);
		GregorianCalendar endDate = new GregorianCalendar();
		startDate.set(endValues[0], endValues[1] - 1, endValues[2], endValues[3], endValues[4], 0);
		
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate.getTimeInMillis()); 
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate.getTimeInMillis()); 
				
		startActivity(calIntent);
	}
	
	/**
	 * Gets the calendar id.
	 * 
	 * @return
	 */
	public Long getCalId() {
		String[] projection = new String[] { Calendars._ID, Calendars.NAME, Calendars.ACCOUNT_NAME, Calendars.ACCOUNT_TYPE };
		Cursor calCursor = getContentResolver().query(Calendars.CONTENT_URI, projection, Calendars.VISIBLE + " = 1", null, Calendars._ID + " ASC");
		long id = 0;
		if (calCursor.moveToFirst()) {
			do {
				id = calCursor.getLong(0);
//				String displayName = calCursor.getString(1);
			} while (calCursor.moveToNext());
		}
		return id;
	}
	
	/**
	 * Adds an {@link Event} into the Google Calendar.
	 */
	/*
	public void addEvent() {
		try {
//			long calId = getCalId();
//			Integer calID = 1;
			Integer calID = 1;

			Calendar beginTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			beginTime.set(2014, 6, 28, 16, 00);
			endTime.set(2014, 6, 28, 16, 30);

			ContentResolver contentResolver = getContentResolver();
			ContentValues values = new ContentValues();
			
			values.put(Events.DTSTART, beginTime.getTimeInMillis());
			values.put(Events.DTEND, endTime.getTimeInMillis());
//			values.put(Events.EVENT_COLOR, "blue");
			values.put("allDay", 0);
			values.put("hasAlarm", 1);
			
			values.put(Events.TITLE, mAgenda.theme_title);
			values.put(Events.DESCRIPTION, mAgenda.theme_description);
			values.put(Events.CALENDAR_ID, calID);
			
			Uri uri = contentResolver.insert(Events.CONTENT_URI, values);
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
//		addEvent();
		addIntentEvent();
	}	
}