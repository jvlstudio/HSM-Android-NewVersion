package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Passe;
import br.com.ikomm.apps.HSM.repo.EventRepo;
import br.com.ikomm.apps.HSM.repo.PasseRepo;
import br.com.ikomm.apps.HSM.services.WebServiceCommunication;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * PaymentActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class PaymentActivity extends SherlockFragmentActivity implements OnClickListener {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private Integer mIcCadastro = 0;
	private Integer mBanners = -1;
	
	private String mNome = "";
	private String mEmail = "";
	private String mCpf = "";
	private String mEmpresa = "";
	private String mCargo = "";
	private String mCor = "";
	private String mDia = "";
	
	private Long mPasseId;
	private Integer mEventId;
	private Passe mPasse = new Passe();
	
	private TextView mDaysTextView;
	private Spinner mQuantitySpinner;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

		getExtras();
		setActionBar();
		hide();
		colorizeLinearLayout();
		initializeButtons();
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
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Gets the {@link Activity} extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras();
		Intent intent = getIntent();
		if (extras != null) {
			mPasseId = extras.getLong("passe_id");
			mEventId = extras.getInt("event_id");
			if (mEventId != null) {
				getUserInfo(intent);
				getCurrentPasse();
			}
		}
		mIcCadastro = intent.getIntExtra("ok", 0);
		mBanners = intent.getIntExtra("banner", -1);
	}
	
	/**
	 * Hides components.
	 */
	public void hide() {
		mQuantitySpinner = (Spinner)findViewById(R.id.id_quantity_spinner);
		mQuantitySpinner.setVisibility(View.GONE);
		mDaysTextView = (TextView)findViewById(R.id.id_days_text_view);
		mDaysTextView.setVisibility(View.GONE);
	}
	
	/**
	 * Colorizes the {@link LinearLayout}.
	 */
	public void colorizeLinearLayout() {
		LinearLayout paymentLinearLayout = (LinearLayout)findViewById(R.id.id_payment_linear_layout);
		
		if (mPasse.color.equals("green")) {
			mCor = mPasse.color;
			paymentLinearLayout.setBackgroundResource(R.drawable.hsm_passes_title_green);
			setSpinner();
		}
		if (mPasse.color.equals("gold")) {
			mCor = mPasse.color;
			paymentLinearLayout.setBackgroundResource(R.drawable.hsm_passes_title_gold);
		}
		if (mPasse.color.equals("red")) {
			mCor = mPasse.color;
			paymentLinearLayout.setBackgroundResource(R.drawable.hsm_passes_title_red);
		}
		
		TextView textView = (TextView)findViewById(R.id.id_title_text_view);
		textView.setText(mPasse.name);
	}
	
	/**
	 * Initializes all {@link Button}.
	 */
	public void initializeButtons() {
		Button purchaseButton = (Button)findViewById(R.id.id_purchase_button);
		purchaseButton.setOnClickListener(this);
		
		Button participantButton = (Button)findViewById(R.id.id_participant_button);
		participantButton.setOnClickListener(this);
	}
	
	/**
	 * Gets the user info.
	 * 
	 * @param intent The parent {@link Intent}. 
	 */
	public void getUserInfo(Intent intent) {
		mNome = intent.getStringExtra("nome");
		mEmail = intent.getStringExtra("email");
		mCpf = intent.getStringExtra("cpf");
		mEmpresa = intent.getStringExtra("empresa");
		mCargo = intent.getStringExtra("cargo");
	}
	
	/**
	 * Gets the current {@link Passe}.
	 */
	public void getCurrentPasse() {
		PasseRepo passeRepo = new PasseRepo(getBaseContext());
		passeRepo.open();
		mPasse = passeRepo.getPasse(mPasseId);
		passeRepo.close();
	}
	
	/**
	 * Sets the {@link Spinner}.
	 */
	public void setSpinner() {
	    String[] dates = getEventDates();
	    if (dates.length > 1) {
			// Initializes the spinner.
	    	
//			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, dates);
//			arrayAdapter.setDropDownViewResource(R.layout.spinner_text_color);
	    	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color /*simple_spinner_item*/, dates);
	    	arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mQuantitySpinner.setVisibility(View.VISIBLE);
			mQuantitySpinner.setAdapter(arrayAdapter);
			mDaysTextView.setVisibility(View.VISIBLE);
	    }
	}
	
	/**
	 * Gets all {@link Event} dates.
	 * 
	 * @return
	 */
	public String[] getEventDates() {
		// Gets the event dates.
		EventRepo eventRepo = new EventRepo(this);
		eventRepo.open();
		Event event = eventRepo.getEvent(mEventId);
		String infoDates = event.info_dates;
		eventRepo.close();
		
		// Gets each date.
		String[] dates = infoDates.replace("|", "-").split("-");
		String[] datesTrim = new String[dates.length];
		for (int i = 0; i < dates.length; i++) {
			datesTrim[i] = dates[i].trim();
		}
		
		return datesTrim;
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.id_purchase_button:
				if (mIcCadastro == 1) {
					startActivity(new Intent(PaymentActivity.this, GreetingsActivity.class));
					WebServiceCommunication ws = new WebServiceCommunication();
					if (mCor.equals("green")) {
						if (mQuantitySpinner.getSelectedItem() != null) {
							mDia = mQuantitySpinner.getSelectedItem().toString();
						}
					}
					if (!mCor.isEmpty() && !mNome.isEmpty() && !mEmail.isEmpty() && !mEmpresa.isEmpty() && !mCargo.isEmpty()  && !mCpf.isEmpty()) {
						ws.sendFormularioCompra(mCor, mDia, mNome, mEmail, mEmpresa, mCargo, mCpf);
					}
					finish();
				} else {
					Toast.makeText(PaymentActivity.this, "Cadastro com dados inv‡lidos.", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.id_participant_button:
				Intent intent = new Intent(PaymentActivity.this, ParticipantActivity.class);
				intent.putExtra("var", mBanners);
				intent.putExtra("event_id", mEventId);
				startActivity(intent);
				break;
		}		
	}
}