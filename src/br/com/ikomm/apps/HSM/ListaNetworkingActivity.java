package br.com.ikomm.apps.HSM;

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
import android.widget.ListView;
import android.widget.Toast;
import br.ikomm.hsm.adapter.AmigoAdapter;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.repo.CartaoRepository;
import br.ikomm.hsm.util.CartaoConverter;
import br.ikomm.hsm.util.IntentIntegrator;

import com.google.gson.Gson;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo ListaNetworkingActivity at July 10, 2014.
 */
public class ListaNetworkingActivity extends FragmentActivity {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private CartaoRepository mCartaoRepo;
	private List<Cartao> mCartaoList;

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_networking);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
		
		mCartaoRepo = new CartaoRepository(ListaNetworkingActivity.this);
		mCartaoList = mCartaoRepo.getMeusContatos();
		boolean temContato = mCartaoList != null && mCartaoList.size() > 0;

		ListView listView = (ListView) findViewById(R.id.listViewNetworking);
		if (!temContato) {
			listView.setVisibility(View.GONE);
		} else {
			findViewById(R.id.frameButtonNet).setVisibility(View.GONE);
			listView.setAdapter(new AmigoAdapter(this));
			listView.setOnItemClickListener(onItemClick());
		}
		addListenerButton();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			String contents = data.getStringExtra("SCAN_RESULT");
			CartaoConverter converter = new CartaoConverter();
			Cartao novoContato = converter.CartaoFromString(contents);
			for (Cartao cartao : mCartaoList) {
				if (cartao.nome.equals(novoContato.nome) && cartao.email.equals(novoContato.email)) {
					Toast.makeText(ListaNetworkingActivity.this, "Você já possui um mContato com este nome e email.", Toast.LENGTH_LONG).show();
					return;
				}
			}
			List<Cartao> contatos = mCartaoRepo.getMeusContatos();
			List<Cartao> novoContatos = new ArrayList<Cartao>();
			novoContatos.addAll(contatos);
			novoContatos.add(novoContato);
			mCartaoRepo.setMeusContatos(novoContatos);
		} catch (Exception e) {
			Toast.makeText(ListaNetworkingActivity.this, "Ocorreu um erro na leitura do QRCode, por favor tente novamente.", Toast.LENGTH_LONG).show();
			return;
		}

		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(ListaNetworkingActivity.this, "Leitura de QRCode cancelada.", Toast.LENGTH_LONG).show();
			return;
		}

		Intent intent = new Intent(ListaNetworkingActivity.this, ListaNetworkingActivity.class);
		startActivity(intent);
		finish();
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	private OnItemClickListener onItemClick() {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adpterView, View view, int pos, long id) {
				Intent intent = new Intent(ListaNetworkingActivity.this, ContatoActivity.class);
				Cartao cartaoClick = mCartaoList.get(pos);
				Gson gson = new Gson();
				intent.putExtra("jsonCartao", gson.toJson(cartaoClick));
				startActivity(intent);
			}
		};
	}

	private void addListenerButton() {
		findViewById(R.id.btnCriarCartao).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( ListaNetworkingActivity.this, MeuCartaoActivity.class);
				startActivity(intent);
			}
		});
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
		if (item.getItemId() == R.id.menu_qrcode) {
			try {
				IntentIntegrator scan = new IntentIntegrator( ListaNetworkingActivity.this);
				scan.initiateScan();
			} catch (Exception e) {
				Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
				startActivity(marketIntent);
			}
		}
		return super.onOptionsItemSelected(item);
	}
}