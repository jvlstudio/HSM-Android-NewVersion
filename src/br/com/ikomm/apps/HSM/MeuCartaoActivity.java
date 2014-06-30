package br.com.ikomm.apps.HSM;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.model.CartaoRepositorio;

public class MeuCartaoActivity extends Activity {

	CartaoRepositorio cartaoRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meu_cartao);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		cartaoRepo = new CartaoRepositorio(MeuCartaoActivity.this);
		addListenerButton();
	}

	private void addListenerButton() {
		// TODO Auto-generated method stub
		findViewById(R.id.btnCriarMeuCartao).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						TextView nome = (TextView) findViewById(R.id.tNomeNet);
						TextView email = (TextView) findViewById(R.id.tEmailNet);
						TextView telefone = (TextView) findViewById(R.id.tTelefoneNet);
						TextView celular = (TextView) findViewById(R.id.tCelularNet);
						TextView empresa = (TextView) findViewById(R.id.tEmpresaNet);
						TextView cargo = (TextView) findViewById(R.id.tCargoNet);
						TextView website = (TextView) findViewById(R.id.tWebsiteNet);

						if (nome.getText().toString().isEmpty()) {
							Toast.makeText(MeuCartaoActivity.this,
									"Por favor, preencha seu nome.",
									Toast.LENGTH_SHORT).show();
						} else if (email.getText().toString().isEmpty()) {
							Toast.makeText(MeuCartaoActivity.this,
									"Por favor, insira um e-mail válido.",
									Toast.LENGTH_SHORT).show();

						} else if (telefone.getText().toString().isEmpty()) {
							Toast.makeText(MeuCartaoActivity.this,
									"Por favor, insira um telefone válido.",
									Toast.LENGTH_SHORT).show();

						} else if (celular.getText().toString().isEmpty()) {
							Toast.makeText(MeuCartaoActivity.this,
									"Por favor, insira um celular válido.",
									Toast.LENGTH_SHORT).show();

						} else if (empresa.getText().toString().isEmpty()) {
							Toast.makeText(MeuCartaoActivity.this,
									"Por favor, insira uma empresa válida.",
									Toast.LENGTH_SHORT).show();

						} else if (cargo.getText().toString().isEmpty()) {
							Toast.makeText(MeuCartaoActivity.this,
									"Por favor, insira um cargo válido.",
									Toast.LENGTH_SHORT).show();

						} else if (website.getText().toString().isEmpty()) {
							Toast.makeText(MeuCartaoActivity.this,
									"Por favor, insira um website válido.",
									Toast.LENGTH_SHORT).show();
						} else {
							// estou retornando para a tela de listanet. pois
							// não sei o fluxo.
							Cartao meuCartao = new Cartao();
							meuCartao.nome = nome.getText().toString();
							meuCartao.email = email.getText().toString();
							meuCartao.telefone = telefone.getText().toString();
							meuCartao.celular = celular.getText().toString();
							meuCartao.empresa = empresa.getText().toString();
							meuCartao.cargo = cargo.getText().toString();
							meuCartao.website = website.getText().toString();

							// Atualiza lista
							List<Cartao> lista = cartaoRepo.getMeusContatos();
							if (lista == null) {
								lista = new ArrayList<Cartao>();
								lista.add(meuCartao);
								cartaoRepo.setMeusContatos(lista);
							}

							Intent intent = new Intent(MeuCartaoActivity.this,
									ListaNetworkingActivity.class);
							startActivity(intent);

						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meu_cartao, menu);
		return false;
	}

}
