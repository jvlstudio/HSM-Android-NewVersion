package br.com.ikomm.apps.HSM.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Card;
import br.com.ikomm.apps.HSM.utils.CardConverter;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * QRCodeActivity.java class.
 * Modified by Rodrigo at July 10, 2014.
 */
public class QRCodeActivity extends Activity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Gson mGson = new Gson();
	private Card mContact;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qrcode);
		
		getExtras();
		setUniversalImage((ImageView) findViewById(R.id.id_big_qrcode));
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Intent intent = getIntent();
		final String jsonCard = intent.getStringExtra(NetworkingListActivity.JSON_CARD);
		if (!jsonCard.isEmpty()) {
			mContact = mGson.fromJson(jsonCard, Card.class);
		}
	}
	
	/**
	 * Gets the QRCode url.
	 */
	public String getQRCodeUrl() {
		String url = "http://chart.apis.google.com/chart?cht=qr&chs=500x500&chld=H|0&chl=";
//		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=";
		
		CardConverter convert = new CardConverter();
		String textCode = convert.cardToString(mContact);
		url += textCode;
		
		return url;
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage(ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(getQRCodeUrl(), imageView, cache);
	}
}