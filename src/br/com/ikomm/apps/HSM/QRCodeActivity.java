package br.com.ikomm.apps.HSM;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.util.CartaoConverter;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class QRCodeActivity extends Activity {

	Gson gson = new Gson();
	Cartao contato;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qrcode);
		
		Intent intent = getIntent();
		final String jsonCartao = intent.getStringExtra("jsonCartao");
		
		if (!jsonCartao.isEmpty())
			contato = gson.fromJson(jsonCartao, Cartao.class);
		
		ImageView qrCode = (ImageView) findViewById(R.id.idQRCodeGrande);
		DisplayImageOptions cache = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true).build();

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(QRCodeActivity.this));

		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=500x500&chld=H|0&chl=";
		//String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=";
		
		CartaoConverter convert = new CartaoConverter();
		String textCode = convert.CartaoToString(contato);
		
		imageUri = imageUri + textCode;

		imageLoader.displayImage(imageUri, qrCode, cache);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qrcode, menu);
		return false;
	}

}
