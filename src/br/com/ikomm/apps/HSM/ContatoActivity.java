package br.com.ikomm.apps.HSM;

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
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.util.CartaoConverter;

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
public class ContatoActivity extends SherlockActivity {
	
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
		setContentView(R.layout.activity_contato);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
		
		addListenerButton();
		
		Intent intent = getIntent();
		final String jsonCartao = intent.getStringExtra("jsonCartao");

		if (!jsonCartao.isEmpty()) {
			mContato = mGson.fromJson(jsonCartao, Cartao.class);
		}
		TextView nome = (TextView) findViewById(R.id.lNomeNet);
		nome.setText(mContato.nome);

		TextView nomeTop = (TextView) findViewById(R.id.lNomeTop);
		nomeTop.setText(mContato.nome);

		TextView email = (TextView) findViewById(R.id.lEmailNet);
		email.setText(mContato.email);

		ImageView qrCode = (ImageView) findViewById(R.id.imgQrcodeNet);
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(ContatoActivity.this));
		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=500x500&chld=H|0&chl=";
//		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=";
		
		CartaoConverter convert = new CartaoConverter();
		String textCode = convert.CartaoToString(mContato);
		imageUri = imageUri + textCode;
		imageLoader.displayImage(imageUri, qrCode, cache);
		
		qrCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContatoActivity.this, QRCodeActivity.class);
				intent.putExtra("jsonCartao", jsonCartao);
				startActivity(intent);
			}
		});

		ImageButton imgButton = (ImageButton) findViewById(R.id.imgAddNet);
		imgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addContactIntent = new Intent(Contacts.Intents.Insert.ACTION, Contacts.People.CONTENT_URI);
				addContactIntent.putExtra(Contacts.Intents.Insert.NAME, mContato.nome);
				addContactIntent.putExtra(Contacts.Intents.Insert.PHONE, mContato.celular);
				startActivity(addContactIntent);
			}
		});
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
	 * Adds {@link Button} listeners.
	 */
	private void addListenerButton() {
		findViewById(R.id.btnEnviarEmail).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String[] { mContato.email });
				try {
					startActivity(Intent.createChooser(i, "Enviar email..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(ContatoActivity.this, "Você não possui cliente de email instalado.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}