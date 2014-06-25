package br.com.ikomm.apps.HSM;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.util.CartaoConverter;
import br.ikomm.hsm.util.Touch;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ContatoActivity extends Activity {
	Gson gson = new Gson();
	Cartao contato;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		addListenerButton();

		Intent intent = getIntent();
		final String jsonCartao = intent.getStringExtra("jsonCartao");

		if (!jsonCartao.isEmpty())
			contato = gson.fromJson(jsonCartao, Cartao.class);
		// else
		// fazer algo pq teve um erro.

		TextView nome = (TextView) findViewById(R.id.lNomeNet);
		nome.setText(contato.nome);

		TextView nomeTop = (TextView) findViewById(R.id.lNomeTop);
		nomeTop.setText(contato.nome);

		TextView email = (TextView) findViewById(R.id.lEmailNet);
		email.setText(contato.email);

		ImageView qrCode = (ImageView) findViewById(R.id.imgQrcodeNet);
		DisplayImageOptions cache = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true).build();

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(ContatoActivity.this));

		String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=500x500&chld=H|0&chl=";
		//String imageUri = "http://chart.apis.google.com/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=";
		
		CartaoConverter convert = new CartaoConverter();
		String textCode = convert.CartaoToString(contato);
		
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
				Intent addContactIntent = new Intent(
						Contacts.Intents.Insert.ACTION,
						Contacts.People.CONTENT_URI);
				addContactIntent.putExtra(Contacts.Intents.Insert.NAME,
						contato.nome);
				addContactIntent.putExtra(Contacts.Intents.Insert.PHONE,
						contato.celular);
				startActivity(addContactIntent);
			}
		});
		
		
	}

	private void addListenerButton() {
		// TODO Auto-generated method stub
		findViewById(R.id.btnEnviarEmail).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent i = new Intent(Intent.ACTION_SEND);
						i.setType("message/rfc822");
						i.putExtra(Intent.EXTRA_EMAIL,
								new String[] { contato.email });
						try {
							startActivity(Intent.createChooser(i,
									"Enviar email..."));
						} catch (android.content.ActivityNotFoundException ex) {
							Toast.makeText(
									ContatoActivity.this,
									"Você não possui cliente de email instalado.",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contato, menu);
		return false;
	}

}
