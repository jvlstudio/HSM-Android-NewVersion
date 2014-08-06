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
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Passe;
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
	// Constants
	//--------------------------------------------------
	
	public static final String EXTRA_BANNERS = "banners";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private Integer mBanners = -1;
	private Integer mParent = 0;
	
	private String mNome = "";
	private String mEmail = "";
	private String mCpf = "";
	private String mCompany = "";
	private String mRole = "";
	private String mColor = "";
	private String mDay = "";
	
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
	
	@Override
	protected void onActivityResult(int code, int result, Intent intent) {
		mParent = result;
		getUserInfo(intent);
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
	 * Gets the {@link Activity} extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras();
		Intent intent = getIntent();
		if (extras != null) {
			mPasseId = extras.getLong(PassesActivity.EXTRA_PASSE_ID);
			mEventId = extras.getInt(PassesActivity.EXTRA_EVENT_ID);
			if (mEventId != null) {
				getUserInfo(intent);
				getCurrentPasse();
			}
		}
		mBanners = intent.getIntExtra(ParticipantActivity.EXTRA_BANNER, -1);
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
		mColor = mPasse.getColor();
		if (mPasse.color.equals("green")) {
			paymentLinearLayout.setBackgroundResource(R.drawable.hsm_passes_title_green);
			setSpinner();
		}
		if (mPasse.color.equals("gold")) {
			paymentLinearLayout.setBackgroundResource(R.drawable.hsm_passes_title_gold);
		}
		if (mPasse.color.equals("red")) {
			paymentLinearLayout.setBackgroundResource(R.drawable.hsm_passes_title_red);
		}
		
		TextView textView = (TextView)findViewById(R.id.id_title_text_view);
		textView.setText(mPasse.getName());
	}
	
	/**
	 * Initializes all {@link Button}.
	 */
	public void initializeButtons() {
		Button purchaseButton = (Button)findViewById(R.id.id_purchase_button);
		purchaseButton.setOnClickListener(this);
		
		Button participantButton = (Button)findViewById(R.id.id_register_button);
		participantButton.setOnClickListener(this);
	}
	
	/**
	 * Gets the user info.
	 * 
	 * @param intent The parent {@link Intent}. 
	 */
	public void getUserInfo(Intent intent) {
		mNome = intent.getStringExtra(ParticipantActivity.EXTRA_NAME);
		mEmail = intent.getStringExtra(ParticipantActivity.EXTRA_EMAIL);
		mCpf = intent.getStringExtra(ParticipantActivity.EXTRA_CPF);
		mCompany = intent.getStringExtra(ParticipantActivity.EXTRA_COMPANY);
		mRole = intent.getStringExtra(ParticipantActivity.EXTRA_ROLE);
	}
	
	/**
	 * Gets the current {@link Passe}.
	 */
	public void getCurrentPasse() {
		mPasse = QueryHelper.getPasse(mPasseId);
	}
	
	/**
	 * Sets the {@link Spinner}.
	 */
	public void setSpinner() {
	    String[] dates = getEventDates();
	    if (dates.length > 1) {
			// Initializes the spinner.
	    	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, dates);
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
		Event event = QueryHelper.getEvent(mEventId);
		String infoDates = event.getInfoDates();
		
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
				if (mParent == ParticipantActivity.IS_PARENT) {
					startActivity(new Intent(PaymentActivity.this, GreetingsActivity.class));
					WebServiceCommunication ws = new WebServiceCommunication();
					if (mColor.equals("green")) {
						if (mQuantitySpinner.getSelectedItem() != null) {
							mDay = mQuantitySpinner.getSelectedItem().toString();
						}
					}
					if (!mColor.isEmpty() && !mNome.isEmpty() && !mEmail.isEmpty() && !mCompany.isEmpty() && !mRole.isEmpty()  && !mCpf.isEmpty()) {
						ws.sendPurchaseForm(mColor, mDay, mNome, mEmail, mCompany, mRole, mCpf);
					}
					finish();
				} else {
					Toast.makeText(PaymentActivity.this, getString(R.string.payment_activity_invalid_data), Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.id_register_button:
				Intent intent = new Intent(PaymentActivity.this, ParticipantActivity.class);
				intent.putExtra(EXTRA_BANNERS, mBanners);
				startActivityForResult(intent, 0);
				break;
		}		
	}
}