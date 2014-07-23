package br.ikomm.hsm.activity;

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
import br.ikomm.hsm.adapter.AmigoAdapter;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.repo.CartaoRepository;
import br.ikomm.hsm.util.CartaoConverter;
import br.ikomm.hsm.util.IntentIntegrator;

import com.google.gson.Gson;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo NetworkingListActivity at July 10, 2014.
 */
public class NetworkingListActivity extends FragmentActivity implements OnItemClickListener, OnClickListener {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private CartaoRepository mCartaoRepo;
	private List<Cartao> mCartaoList;
	
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
			Toast.makeText(NetworkingListActivity.this, "Ocorreu um erro na leitura do QRCode, por favor tente novamente.", Toast.LENGTH_LONG).show();
			return;
		}

		// QRCode error.
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(NetworkingListActivity.this, "Leitura de QRCode cancelada.", Toast.LENGTH_LONG).show();
			return;
		}
		recursiveCall();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_networking, menu);
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
		mCartaoRepo = new CartaoRepository(NetworkingListActivity.this);
		mCartaoList = mCartaoRepo.getMeusContatos();
		boolean hasContact = mCartaoList != null && mCartaoList.size() > 0;

		// Sets the ListView.
		ListView listView = (ListView)findViewById(R.id.id_list_view);
		if (!hasContact) {
			listView.setVisibility(View.GONE);
		} else {
			mCreateCardButton.setVisibility(View.GONE);
			listView.setAdapter(new AmigoAdapter(this));
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
		CartaoConverter converter = new CartaoConverter();
		Cartao novoContato = converter.CartaoFromString(contents);
		for (Cartao cartao : mCartaoList) {
			if (cartao.nome.equals(novoContato.nome) && cartao.email.equals(novoContato.email)) {
				Toast.makeText(NetworkingListActivity.this, "Você já possui um contato com este nome e email.", Toast.LENGTH_LONG).show();
				return;
			}
		}
		List<Cartao> contatos = mCartaoRepo.getMeusContatos();
		List<Cartao> novoContatos = new ArrayList<Cartao>();
		novoContatos.addAll(contatos);
		novoContatos.add(novoContato);
		mCartaoRepo.setMeusContatos(novoContatos);
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
		Intent intent = new Intent(NetworkingListActivity.this, ContatoActivity.class);
		Cartao cartaoClick = mCartaoList.get(position);
		Gson gson = new Gson();
		intent.putExtra("jsonCartao", gson.toJson(cartaoClick));
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(NetworkingListActivity.this, MeuCartaoActivity.class);
		startActivity(intent);
	}
}