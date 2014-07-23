package br.ikomm.hsm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.R.id;
import br.com.ikomm.apps.HSM.R.layout;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.util.CartaoConverter;

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
	private Cartao mContato;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qrcode);
		
		Intent intent = getIntent();
		final String jsonCartao = intent.getStringExtra("jsonCartao");
		if (!jsonCartao.isEmpty()) {
			mContato = mGson.fromJson(jsonCartao, Cartao.class);
		}
		
		ImageView qrCode = (ImageView) findViewById(R.id.idQRCodeGrande);
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(QRCodeActivity.this));

		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=500x500&chld=H|0&chl=";
//		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=";
		
		CartaoConverter convert = new CartaoConverter();
		String textCode = convert.CartaoToString(mContato);
		imageUri = imageUri + textCode;
		imageLoader.displayImage(imageUri, qrCode, cache);
	}
}