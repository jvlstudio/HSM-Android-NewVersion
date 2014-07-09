package br.com.ikomm.apps.HSM.neo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.activity.AgendaActivity;
import br.ikomm.hsm.activity.PacoteActivity;
import br.ikomm.hsm.activity.PalestrantesActivity;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.repo.EventRepo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * DetalheEventoNeoActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class DetalheEventoNeoActivity extends FragmentActivity {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/events/";
	
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
			loadFields();
		}
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);		
		addListenerButton();
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 * 
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage(String url) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, (ImageView)findViewById(R.id.imgEventoDet), cache);
	}
	
	/**
	 * Load all fields of this {@link Activity}. 
	 */
	private void loadFields() {
		TextView txtEvento = (TextView) findViewById(R.id.textEvent);
		txtEvento.setText(mEvent.name);
		
		String url = URL + mEvent.image_single;
		setUniversalImage(url);

		TextView txtDescricao = (TextView) findViewById(R.id.textDescriptionEvent);
		txtDescricao.setText(mEvent.description);
		
		mAgendaButton = (Button) findViewById(R.id.btnAgenda);
		mPassesButton = (Button) findViewById(R.id.btnPasses);
		mPalestrantesButton = (Button) findViewById(R.id.btnPalestrante);
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	/**
	 * Add all {@link Button} listeners.
	 */
	public void addListenerButton() {
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