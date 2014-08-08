package br.com.ikomm.apps.HSM.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.database.CardRepository;
import br.com.ikomm.apps.HSM.model.Card;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * AddCardActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class AddCardActivity extends SherlockActivity implements OnClickListener {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private CardRepository mCardRepo;
	
	private TextView mName;
	private TextView mEmail;
	private TextView mPhone;
	private TextView mMobilePhone;
	private TextView mCompany;
	private TextView mRole;
	private TextView mWebsite;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);

		setActionBar();
		setButton();
		setTextView();
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
	 * Sets the {@link Button}.
	 */
	public void setButton() {
		// Gets the CartaoRepo.
		mCardRepo = new CardRepository(AddCardActivity.this);
		
		Button createCardButton = (Button)findViewById(R.id.id_create_card_button);
		createCardButton.setOnClickListener(this);
	}
	
	public void setTextView() {
		mName = (TextView) findViewById(R.id.id_complete_name_edit_text);
		mEmail = (TextView) findViewById(R.id.id_email_edit_text);
		mPhone = (TextView) findViewById(R.id.id_phone_edit_text);
		mMobilePhone = (TextView) findViewById(R.id.id_mobile_phone_edit_text);
		mCompany = (TextView) findViewById(R.id.id_company_edit_text);
		mRole = (TextView) findViewById(R.id.id_role_edit_text);
		mWebsite = (TextView) findViewById(R.id.id_website_edit_text);
	}
	
	/**
	 * Sets error messages.
	 */
	public void setErrorMessages() {
		if (mName.getText().toString().isEmpty()) {
			Toast.makeText(AddCardActivity.this, getString(R.string.add_card_activity_name_empty), Toast.LENGTH_SHORT).show();
		} else if (mEmail.getText().toString().isEmpty()) {
			Toast.makeText(AddCardActivity.this, getString(R.string.add_card_activity_email_empty), Toast.LENGTH_SHORT).show();
		} else if (mPhone.getText().toString().isEmpty()) {
			Toast.makeText(AddCardActivity.this, getString(R.string.add_card_activity_phone_empty), Toast.LENGTH_SHORT).show();
		} else if (mMobilePhone.getText().toString().isEmpty()) {
			Toast.makeText(AddCardActivity.this, getString(R.string.add_card_activity_mobile_empty), Toast.LENGTH_SHORT).show();
		} else if (mCompany.getText().toString().isEmpty()) {
			Toast.makeText(AddCardActivity.this, getString(R.string.add_card_activity_company_empty), Toast.LENGTH_SHORT).show();
		} else if (mRole.getText().toString().isEmpty()) {
			Toast.makeText(AddCardActivity.this, getString(R.string.add_card_activity_role_empty), Toast.LENGTH_SHORT).show();
		} else if (mWebsite.getText().toString().isEmpty()) {
			Toast.makeText(AddCardActivity.this, getString(R.string.add_card_activity_website_empty), Toast.LENGTH_SHORT).show();
		} else {
			setContacts();
		}
	}
	
	/**
	 * Sets the contacts.
	 */
	public void setContacts() {
		// Gets the current card.
		Card card = 
		new Card(mName.getText().toString(), mEmail.getText().toString(), mPhone.getText().toString(),
			mMobilePhone.getText().toString(), mCompany.getText().toString(), mRole.getText().toString(), mWebsite.getText().toString());

		// Refreshes list.
		List<Card> list = mCardRepo.getMyContacts();
		if (list == null) {
			list = new ArrayList<Card>();
			list.add(card);
			mCardRepo.setMyContacts(list);
		}
		
		// Calls NetworkingListActivity.
		Intent intent = new Intent(AddCardActivity.this, NetworkingListActivity.class);
		startActivity(intent);
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		setErrorMessages();
	}
}