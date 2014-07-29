package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.utils.StringUtils;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * ParticipantActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class ParticipantActivity extends SherlockFragmentActivity implements OnClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final Integer IS_PARENT = 1; 
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private EditText mNameEditText; 
	private EditText mMailEditText;
	private EditText mCpfEditText;
	private EditText mCompanyEditText;
	private EditText mRoleEditText;

	private Integer mBanner;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participant);

		setActionBar();
		getExtras();
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
	 * Gets the extras.
	 */
	public void getExtras() {
		Button button = (Button)findViewById(R.id.id_back_button);
		button.setOnClickListener(this);
		Intent intent = getIntent();
		mBanner = intent.getIntExtra("banners", -1);
	}
	
	/**
	 * Sets the components.
	 */
	public void setComponents() {
		mNameEditText = (EditText) findViewById(R.id.id_name_edit_text);
		mMailEditText = (EditText) findViewById(R.id.id_mail_edit_text);
		mCpfEditText = (EditText) findViewById(R.id.id_cpf_edit_text);
		mCompanyEditText = (EditText) findViewById(R.id.id_company_edit_text);
		mRoleEditText = (EditText) findViewById(R.id.id_role_edit_text);
	}
	
	/**
	 * Validates each field.
	 * 
	 * @return
	 */
	private boolean validateFields() {
		setComponents();

		String name = mNameEditText.getText().toString();
		String email = mMailEditText.getText().toString();
		String company = mCompanyEditText.getText().toString();
		String role = mRoleEditText.getText().toString();
		
		if (StringUtils.isEmpty(name)) {
			return false;
		} else if (StringUtils.isEmpty(email)) {
			return false;
		} else if (!validateCPF(mCpfEditText.getText().toString())) {
			return false;
		} else if (StringUtils.isEmpty(company)) {
			return false;
		} else if (StringUtils.isEmpty(role)) {
			return false;
		}
		return true;
	}

	/**
	 * Check if CPF is valid.
	 * 
	 * @param cpf
	 * 
	 * @return
	 */
	public boolean validateCPF(String cpf) {
		String cpfString = cpf;
		if (cpfString.equals("")) {
			return false;
		}

		int d1 = 0, d2 = 0, digit1 = 0, digit2 = 0, rest = 0, cpfDigit;
		String result;
		for (int count = 1; count < cpfString.length() - 1; count++) {
			cpfDigit = Integer.valueOf(cpfString.substring(count - 1, count)).intValue();
			d1 = d1 + (11 - count) * cpfDigit;
			d2 = d2 + (12 - count) * cpfDigit;
		}

		rest = (d1 % 11);
		if (rest < 2) {
			digit1 = 0;
		} else {
			digit1 = 11 - rest;
		}

		d2 += 2 * digit1;
		rest = (d2 % 11);
		if (rest < 2) {
			digit2 = 0;
		} else {
			digit2 = 11 - rest;
		}

		String verificationDigit = cpfString.substring(cpfString.length() - 2, cpfString.length());
		result = String.valueOf(digit1) + String.valueOf(digit2);
		return verificationDigit.equals(result);
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		if (validateFields()) {
			// Put extras.
			intent.putExtra("name", mNameEditText.getText().toString());
			intent.putExtra("email", mMailEditText.getText().toString());
			intent.putExtra("cpf", mCpfEditText.getText().toString());
			intent.putExtra("company", mCompanyEditText.getText().toString());
			intent.putExtra("role", mRoleEditText.getText().toString());
			intent.putExtra("banner", mBanner);

			// Returns for the Parent Activity.
	        setResult(IS_PARENT, intent);
	        finish();
		} else {
			Toast.makeText(this, "Favor informar os campos de forma correta.", Toast.LENGTH_LONG).show();
		}
	}
}