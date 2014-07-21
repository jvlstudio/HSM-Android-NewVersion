package br.com.ikomm.apps.HSM;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.repo.CartaoRepository;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * MeuCartaoActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class MeuCartaoActivity extends SherlockActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private CartaoRepository mCartaoRepo;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meu_cartao);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
		
		mCartaoRepo = new CartaoRepository(MeuCartaoActivity.this);
		addListenerButton();
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
	
	private void addListenerButton() {
		findViewById(R.id.btnCriarMeuCartao).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView nome = (TextView) findViewById(R.id.tNomeNet);
				TextView email = (TextView) findViewById(R.id.tEmailNet);
				TextView telefone = (TextView) findViewById(R.id.tTelefoneNet);
				TextView celular = (TextView) findViewById(R.id.tCelularNet);
				TextView empresa = (TextView) findViewById(R.id.tEmpresaNet);
				TextView cargo = (TextView) findViewById(R.id.tCargoNet);
				TextView website = (TextView) findViewById(R.id.tWebsiteNet);

				if (nome.getText().toString().isEmpty()) {
					Toast.makeText(MeuCartaoActivity.this, "Por favor, preencha seu nome.", Toast.LENGTH_SHORT).show();
				} else if (email.getText().toString().isEmpty()) {
					Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um e-mail v‡lido.", Toast.LENGTH_SHORT).show();
				} else if (telefone.getText().toString().isEmpty()) {
					Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um telefone v‡lido.", Toast.LENGTH_SHORT).show();
				} else if (celular.getText().toString().isEmpty()) {
					Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um celular v‡lido.", Toast.LENGTH_SHORT).show();
				} else if (empresa.getText().toString().isEmpty()) {
					Toast.makeText(MeuCartaoActivity.this, "Por favor, insira uma empresa v‡lida.", Toast.LENGTH_SHORT).show();
				} else if (cargo.getText().toString().isEmpty()) {
					Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um cargo v‡lido.", Toast.LENGTH_SHORT).show();
				} else if (website.getText().toString().isEmpty()) {
					Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um website v‡lido.", Toast.LENGTH_SHORT).show();
				} else {
					Cartao meuCartao = new Cartao();
					meuCartao.nome = nome.getText().toString();
					meuCartao.email = email.getText().toString();
					meuCartao.telefone = telefone.getText().toString();
					meuCartao.celular = celular.getText().toString();
					meuCartao.empresa = empresa.getText().toString();
					meuCartao.cargo = cargo.getText().toString();
					meuCartao.website = website.getText().toString();

					// Refreshes list.
					List<Cartao> lista = mCartaoRepo.getMeusContatos();
					if (lista == null) {
						lista = new ArrayList<Cartao>();
						lista.add(meuCartao);
						mCartaoRepo.setMeusContatos(lista);
					}
					Intent intent = new Intent(MeuCartaoActivity.this, ListaNetworkingActivity.class);
					startActivity(intent);
				}
			}
		});
	}
}