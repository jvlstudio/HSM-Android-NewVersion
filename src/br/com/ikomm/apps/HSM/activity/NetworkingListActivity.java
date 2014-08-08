package br.com.ikomm.apps.HSM.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.NetworkFriendsAdapter;
import br.com.ikomm.apps.HSM.database.CardRepository;
import br.com.ikomm.apps.HSM.model.Card;
import br.com.ikomm.apps.HSM.utils.CardConverter;
import br.com.ikomm.apps.HSM.utils.qrcode.IntentIntegrator;

import com.google.gson.Gson;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo NetworkingListActivity at July 10, 2014.
 */
public class NetworkingListActivity extends FragmentActivity implements OnItemClickListener, OnClickListener {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
		
	public static final String JSON_CARD = "json_card";
		
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private CardRepository mCardRepo;
	private List<Card> mCardList;
	
	private Button mCreateCardButton;

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_networking_list);

		setActionBar();
		setLayout();
		setAdapter();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Gets the card.
		try {
			setCard(data);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, getString(R.string.networking_list_activity_qrcode_error), Toast.LENGTH_LONG).show();
			return;
		}

		// QRCode error.
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this, getString(R.string.networking_list_activity_qrcode_canceled), Toast.LENGTH_LONG).show();
			return;
		}
		recursiveCall();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_networking_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			case R.id.menu_qrcode:
				try {
					IntentIntegrator scan = new IntentIntegrator(NetworkingListActivity.this);
					scan.initiateScan();
				} catch (Exception e) {
					Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
					Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
					startActivity(marketIntent);
				}
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
	
	public void setLayout() {
		mCreateCardButton = (Button)findViewById(R.id.id_create_card_button);
		mCreateCardButton.setOnClickListener(this);
	}
	
	
	/**
	 * Sets the {@link ListView}.
	 */
	public void setAdapter() {
		mCardRepo = new CardRepository(NetworkingListActivity.this);
		mCardList = mCardRepo.getMyContacts();
		boolean hasContact = mCardList != null && mCardList.size() > 0;

		// Sets the ListView.
		ListView listView = (ListView)findViewById(R.id.id_list_view);
		if (!hasContact) {
			listView.setVisibility(View.GONE);
		} else {
			mCreateCardButton.setVisibility(View.GONE);
			List<Card> cardList = mCardRepo.getMyContacts();
			if (cardList == null) {
				cardList = new ArrayList<Card>();
			}
			listView.setAdapter(new NetworkFriendsAdapter(this, cardList));
			listView.setOnItemClickListener(this);
		}
	}
	
	/**
	 * Sets the card.
	 * 
	 * @param data 
	 */
	public void setCard(Intent data) {
		String contents = data.getStringExtra("SCAN_RESULT");
		CardConverter converter = new CardConverter();
		Card newContact = converter.cardFromString(contents);
		for (Card card : mCardList) {
			if (card.name.equals(newContact.name) && card.email.equals(newContact.email)) {
				Toast.makeText(this, getString(R.string.networking_list_activity_existent_contact), Toast.LENGTH_LONG).show();
				return;
			}
		}
		List<Card> contactList = mCardRepo.getMyContacts();
		List<Card> newContactList = new ArrayList<Card>();
		newContactList.addAll(contactList);
		newContactList.add(newContact);
		mCardRepo.setMyContacts(newContactList);
	}
	
	/**
	 * Recursive call.
	 */
	public void recursiveCall() {
		Intent intent = new Intent(NetworkingListActivity.this, NetworkingListActivity.class);
		startActivity(intent);
		finish();
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(NetworkingListActivity.this, ContactActivity.class);
		Card cardClick = mCardList.get(position);
		Gson gson = new Gson();
		intent.putExtra(JSON_CARD, gson.toJson(cardClick));
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(NetworkingListActivity.this, AddCardActivity.class);
		startActivity(intent);
	}
}