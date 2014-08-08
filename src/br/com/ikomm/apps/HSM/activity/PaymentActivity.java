package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Pass;
import br.com.ikomm.apps.HSM.task.Notifiable;
import br.com.ikomm.apps.HSM.utils.DialogUtils;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * PaymentActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class PaymentActivity extends SherlockFragmentActivity implements OnClickListener, Notifiable {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String EXTRA_PARENT = "extra_parent";
	
	public static final String EXTRA_NAME = "name";
	public static final String EXTRA_EMAIL = "email";
	public static final String EXTRA_CPF = "cpf";
	public static final String EXTRA_COMPANY = "company";
	public static final String EXTRA_ROLE = "role";
	
	public static final Integer TIME = 500;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private Integer mParent;
	
	private String mName = "";
	private String mEmail = "";
	private String mCpf = "";
	private String mCompany = "";
	private String mRole = "";
	
	private Long mPassId;
	private Integer mEventId;
	private Pass mPasse = new Pass();
	
	private TextView mDaysTextView;
	private Spinner mQuantitySpinner;
	
	private Handler mHandler = new Handler();

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
		if (intent != null) {
			getUserInfo(intent);
		} else {
			DialogUtils.showSimpleAlert(this, R.string.payment_activity_user_not_registered_title,
				R.string.payment_activity_user_not_registered_message);
		}
	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mHandler.removeCallbacks(mHandlerChecker);
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
		Intent intent = getIntent();
		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				mParent = extras.getInt(EXTRA_PARENT);
				mEventId = extras.getInt(PassActivity.EXTRA_EVENT_ID);
				mPassId = extras.getLong(PassActivity.EXTRA_PASSE_ID);
				getCurrentPasse();
				switch (mParent) {
					case PassActivity.PARENT_IS_PARTICIPANT:
						if (mEventId != null) {
							getUserInfo(intent);
						}
						break;
				}
			}
		}
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
		mName = intent.getStringExtra(EXTRA_NAME);
		mEmail = intent.getStringExtra(EXTRA_EMAIL);
		mCpf = intent.getStringExtra(EXTRA_CPF);
		mCompany = intent.getStringExtra(EXTRA_COMPANY);
		mRole = intent.getStringExtra(EXTRA_ROLE);
	}
	
	/**
	 * Gets the current {@link Pass}.
	 */
	public void getCurrentPasse() {
		mPasse = QueryHelper.getPasse(mPassId);
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
				if (mParent == PassActivity.PARENT_IS_PARTICIPANT) {
					if (!mName.isEmpty() && !mEmail.isEmpty() && !mCompany.isEmpty() && !mRole.isEmpty()  && !mCpf.isEmpty()) {
						ContentManager.getInstance().setPassPurchase(PaymentActivity.this, mName, mEmail, mCompany, mRole, mCpf, (int)mPassId.intValue());
					}
				} else {
					Toast.makeText(PaymentActivity.this, getString(R.string.payment_activity_invalid_data), Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.id_register_button:
				Intent intent = new Intent(PaymentActivity.this, ParticipantActivity.class);
				// If we had already returned from ParticipantActivity, the values below won't be null.
				startActivityForResult(intent, 0);
				break;
		}		
	}

	//--------------------------------------------------
	// Notifiable
	//--------------------------------------------------
	
	public void taskFinished(int type, OperationResult result) {
		// Validating the error result to show the proper dialog message.
		OperationResult.validateResult(PaymentActivity.this, result, null, false);
		
		// Tasks.
		if (type == ContentManager.FETCH_TASK.PASS_PURCHASE) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mHandler.postDelayed(mHandlerChecker, TIME);
			} else {
				DialogUtils.showSimpleAlert(this, R.string.error_title_bad_request, R.string.error_msg_bad_request);
			}
		}
	}
	
	//--------------------------------------------------
	// Handler
	//--------------------------------------------------
	
	/**
	 * Waits for some seconds.
	 */
	private Runnable mHandlerChecker = new Runnable() {
	    public void run() {
	    	startActivity(new Intent(PaymentActivity.this, GreetingsActivity.class));
	    }
	};
}