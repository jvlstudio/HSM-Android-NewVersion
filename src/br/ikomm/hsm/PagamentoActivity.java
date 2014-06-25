package br.ikomm.hsm;

import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.util.WebServiceCommunication;

import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PagamentoActivity extends FragmentActivity {

	private int ic_cadastro = 0;
	private int banners = -1;
	private String nome = "";
	private String email = "";
	private String cpf = "";
	private String empresa = "";
	private String cargo = "";
	private String cor = "";
	private String dia = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagamento);

		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		Intent intent = getIntent();
		
		ImageView imgView = (ImageView) findViewById(R.id.imgPagamento);
		TextView tData = (TextView) findViewById(R.id.lbDias);
		Spinner dias = (Spinner) findViewById(R.id.spinerQuantidade);
		
		ic_cadastro = intent.getIntExtra("ok", 0);
		banners = intent.getIntExtra("banner", -1);

		nome = intent.getStringExtra("nome");
		email = intent.getStringExtra("email");
		cpf = intent.getStringExtra("cpf");
		empresa = intent.getStringExtra("empresa");
		cargo = intent.getStringExtra("cargo");

		String a = null;

		switch (banners) {
		case 0:
			cor = "green";
			imgView.setImageResource(R.drawable.hsm_passes_title_green);
			tData.setVisibility(View.VISIBLE);
			dias.setVisibility(View.VISIBLE);
			break;
		case 1:
			cor = "gold";
			imgView.setImageResource(R.drawable.hsm_passes_title_gold);
			break;
		case 2:
			cor = "red";
			imgView.setImageResource(R.drawable.hsm_passes_title_red);
			break;
		}

		addListnerOnButton();

	}

	private void addListnerOnButton() {
		// TODO Auto-generated method stub
		findViewById(R.id.btnComprar).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ic_cadastro == 1) {
					startActivity(new Intent(PagamentoActivity.this,
							AgradecimentoActivity.class));
					WebServiceCommunication ws = new WebServiceCommunication();
					if (cor.equals("green")) {
						Spinner SpinnerDia = (Spinner) findViewById(R.id.spinerQuantidade);
						if (SpinnerDia.getSelectedItem() != null) {
							dia = SpinnerDia.getSelectedItem().toString();
						}
					}
					if (!cor.isEmpty() && !nome.isEmpty()
							&& !email.isEmpty() && !empresa.isEmpty()
							&& !cargo.isEmpty() && !cpf.isEmpty()) {
						ws.sendFormularioCompra(cor, dia, nome, email, empresa,
								cargo, cpf);
					}

					finish();
				} else {
					Toast.makeText(PagamentoActivity.this,
							"Cadastro com dados inválidos.", Toast.LENGTH_LONG)
							.show();
				}
			}

		});

		findViewById(R.id.btnParticipante).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(PagamentoActivity.this,
								ParticipanteActivity.class);
						intent.putExtra("var", banners);
						startActivity(intent);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pagamento, menu);
		return false;
	}
}
