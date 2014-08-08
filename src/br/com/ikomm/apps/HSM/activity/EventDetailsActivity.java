package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.model.Event;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * EventDetailsActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class EventDetailsActivity extends SherlockFragmentActivity implements OnClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/events/";
	
	public static final String EXTRA_EVENT_ID = "event_id";
	public static final String EXTRA_DATES = "dates";
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private Long mId;
	private Event mEvent;
	
	private LinearLayout mAboutLinearLayout;
	private TextView mTitleTextView;
	private TextView mDescriptionTextView;
	
	private LinearLayout mInfoLinearLayout;
	private TextView mDateTextView;
	private TextView mTimeTextView;
	private TextView mLocalTextView;
	
	private Button mAgendaButton;
	private Button mPassesButton;
	private Button mPalestrantesButton;
	
	private Button mAboutButton;
	private Button mInfoButton;
	private ImageView mAboutImageView;
	private ImageView mInfoImageView;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		
		setActionBar();
		getExtras();
		getCurrentEvent();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_application, menu);
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
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mId = extras.getLong(EventListActivity.EXTRA_EVENT_ID);
		}
	}
	
	/**
	 * Gets the current {@link Event}.
	 */
	public void getCurrentEvent() {
		mEvent = QueryHelper.getEvent(mId);
		if (mEvent != null) {
			setLayout();
			showAboutLinearLayout();
		}
	}
	
	/**
	 * Sets the layout.
	 */
	public void setLayout() {
		// About LinearLayout.
		mAboutLinearLayout = (LinearLayout)findViewById(R.id.id_about_linear_layout);
		mTitleTextView = (TextView)findViewById(R.id.id_title_event);
		mDescriptionTextView = (TextView)findViewById(R.id.id_description_event);
		
		// Info LinearLayout.
		mInfoLinearLayout = (LinearLayout)findViewById(R.id.id_info_linear_layout);
		mDateTextView = (TextView)findViewById(R.id.id_date_event);
		mTimeTextView = (TextView)findViewById(R.id.id_time_event);
		mLocalTextView = (TextView)findViewById(R.id.id_local_event);
		
		setUniversalImage();
		setButtons();
		setViewSwitches();
	}
	
	/**
	 * Shows only the About {@link LinearLayout}.
	 */
	public void showAboutLinearLayout() {
		// Sets texts.
		mTitleTextView.setText(mEvent.name);
		mDescriptionTextView.setText(mEvent.description);
		
		// Sets visibilites.
		mAboutLinearLayout.setVisibility(View.VISIBLE);
		mInfoLinearLayout.setVisibility(View.GONE);
		
		// Sets text colors.
		mAboutButton.setTextColor(getResources().getColor(R.color.hsm_color_white));
		mInfoButton.setTextColor(getResources().getColor(R.color.hsm_color_gray_dark));
		
		// Sets icons.
		mAboutImageView.setBackgroundResource(R.drawable.ic_hsm_about_clicked);
		mInfoImageView.setBackgroundResource(R.drawable.ic_hsm_info_normal);
	}
	
	/**
	 * Shows only the Info {@link LinearLayout}.
	 */
	public void showInfoLinearLayout() {
		// Sets texts.
		mDateTextView.setText(mEvent.info_dates);
		mTimeTextView.setText(mEvent.info_hours);
		mLocalTextView.setText(mEvent.info_locale);
		
		// Sets visibilites.
		mAboutLinearLayout.setVisibility(View.GONE);
		mInfoLinearLayout.setVisibility(View.VISIBLE);
		
		// Sets text colors.
		mAboutButton.setTextColor(getResources().getColor(R.color.hsm_color_gray_dark));
		mInfoButton.setTextColor(getResources().getColor(R.color.hsm_color_white));
		
		// Sets icons.
		mAboutImageView.setBackgroundResource(R.drawable.ic_hsm_about_normal);
		mInfoImageView.setBackgroundResource(R.drawable.ic_hsm_info_clicked);
	}
	
	/**
	 * Sets the {@link Button}.
	 */
	public void setButtons() {
		mAgendaButton = (Button)findViewById(R.id.id_agenda_button);
		mAgendaButton.setOnClickListener(this);
		mPassesButton = (Button)findViewById(R.id.id_passes_button);
		mPassesButton.setOnClickListener(this);
		mPalestrantesButton = (Button) findViewById(R.id.id_panelist_button);
		mPalestrantesButton.setOnClickListener(this);
	}
	
	/**
	 * Sets all view switches.
	 */
	public void setViewSwitches() {
		mAboutButton = (Button)findViewById(R.id.id_about_button);
		mAboutButton.setOnClickListener(this);
		mInfoButton = (Button) findViewById(R.id.id_info_button);
		mInfoButton.setOnClickListener(this);
		
		mAboutImageView = (ImageView)findViewById(R.id.id_about_image);
		mAboutImageView.setOnClickListener(this);
		mInfoImageView = (ImageView)findViewById(R.id.id_info_image);
		mInfoImageView.setOnClickListener(this);
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage() {
		String url = URL + mEvent.getImageSingle();
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, (ImageView)findViewById(R.id.id_event_image_view), cache);
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
			case R.id.id_agenda_button:
				intent = new Intent(EventDetailsActivity.this, AgendaActivity.class);
				intent.putExtra(EXTRA_EVENT_ID, mEvent.getId());
				intent.putExtra(EXTRA_DATES, mEvent.getInfoDates());
				startActivity(intent);
				break;
			case R.id.id_passes_button:
				intent = new Intent(EventDetailsActivity.this, PassActivity.class);
				intent.putExtra(EXTRA_EVENT_ID, mEvent.getId());
				startActivity(intent);
				break;
			case R.id.id_panelist_button:
				intent = new Intent(EventDetailsActivity.this, PanelistActivity.class);
				intent.putExtra(EXTRA_EVENT_ID, mEvent.getId());
				startActivity(intent);
				break;
			case R.id.id_about_button:
				showAboutLinearLayout();
				break;
			case R.id.id_info_button:
				showInfoLinearLayout();
				break;
			case R.id.id_about_image:
				showAboutLinearLayout();
				break;
			case R.id.id_info_image:
				showInfoLinearLayout();
				break;
		}
	}
}