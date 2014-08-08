package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Card;
import br.com.ikomm.apps.HSM.utils.CardConverter;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
@SuppressWarnings("deprecation")
public class ContactActivity extends SherlockActivity implements OnClickListener {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Gson mGson = new Gson();
	private Card mContact;
	
	private String mJsonCard;
	
	private ImageView mQrCodeImageView;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		setActionBar();
		getContact();
		setLayout();
		setUniversalImage();
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
	 * Sets the layout.
	 */
	public void setLayout() {
		setButton();
		setTextView();
	}
	
	/**
	 * Sets the {@link Button}.
	 */
	public void setButton() {
		ImageButton addButton = (ImageButton) findViewById(R.id.id_add_image_button);
		addButton.setOnClickListener(this);
		
		mQrCodeImageView = (ImageView) findViewById(R.id.id_qr_code_image_view);
		mQrCodeImageView.setOnClickListener(this);
		
		Button sendEmailButton = (Button)findViewById(R.id.id_send_email_button);
		sendEmailButton.setOnClickListener(this);
	}
	
	/**
	 * Sets the {@link TextView}.
	 */
	public void setTextView() {
		TextView contactNameTop = (TextView) findViewById(R.id.id_contact_name_top_text_view);
		contactNameTop.setText(mContact.getName());
		
		TextView contactNameBelow = (TextView) findViewById(R.id.id_contact_name_below_text_view);
		contactNameBelow.setText(mContact.getName());

		TextView email = (TextView) findViewById(R.id.id_contact_email_text_view);
		email.setText(mContact.getEmail());
	}

	/**
	 * Gets the contact.
	 */
	public void getContact() {
		Intent intent = getIntent();
		mJsonCard = intent.getStringExtra(NetworkingListActivity.JSON_CARD);

		if (!mJsonCard.isEmpty()) {
			mContact = mGson.fromJson(mJsonCard, Card.class);
		}
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>
	 * If it exists, get from cache.
	 * <br>If isn't, download it.
	 */
	public void setUniversalImage() {
		String url = "http://chart.apis.google.com/chart?cht=qr&chs=500x500&chld=H|0&chl=";
//		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=";
		
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(getCardUrl(url), mQrCodeImageView, cache);
	}
	
	/**
	 * Gets the correct card url.
	 * 
	 * @param url
	 * 
	 * @return
	 */
	public String getCardUrl(String url) {
		CardConverter convert = new CardConverter();
		String textCode = convert.cardToString(mContact);
		String completeUrl = url + textCode;
		
		return completeUrl;
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.id_add_image_button:
				Intent addContactIntent = new Intent(Contacts.Intents.Insert.ACTION, Contacts.People.CONTENT_URI);
				addContactIntent.putExtra(Contacts.Intents.Insert.NAME, mContact.getName());
				addContactIntent.putExtra(Contacts.Intents.Insert.PHONE, mContact.getMobilePhone());
				startActivity(addContactIntent);
				break;
			case R.id.id_qr_code_image_view:
				Intent intent = new Intent(ContactActivity.this, QRCodeActivity.class);
				intent.putExtra(NetworkingListActivity.JSON_CARD, mJsonCard);
				startActivity(intent);
				break;
			case R.id.id_send_email_button:
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String[] { mContact.getEmail() });
				try {
					startActivity(Intent.createChooser(i, getString(R.string.contact_activity_send_email) + "..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(ContactActivity.this, getString(R.string.contact_activity_email_issue), Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
}