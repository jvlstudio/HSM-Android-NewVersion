package br.ikomm.hsm;

import java.util.ArrayList;
import java.util.List;

import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.model.PasseRepo;
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
	private Long event_id;
	PasseRepo _pr;
	Passe passe = new Passe();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagamento);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			event_id = extras.getLong("passe");
		}
		
		Intent intent = getIntent();
		
		ic_cadastro = intent.getIntExtra("ok", 0);
		banners = intent.getIntExtra("banner", -1);

		nome = intent.getStringExtra("nome");
		email = intent.getStringExtra("email");
		cpf = intent.getStringExtra("cpf");
		empresa = intent.getStringExtra("empresa");
		cargo = intent.getStringExtra("cargo");
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		_pr = new PasseRepo(getBaseContext());
		_pr.open();
		passe = _pr.getPasse(event_id);
		_pr.close();
		
		ImageView imgView = (ImageView) findViewById(R.id.imgPagamento);
		TextView tData = (TextView) findViewById(R.id.lbDias);
		Spinner dias = (Spinner) findViewById(R.id.spinerQuantidade);
		
		if (passe.color.equals("green")) {
			cor = passe.color;
			imgView.setImageResource(R.drawable.hsm_passes_title_green);
			tData.setVisibility(View.VISIBLE);
			dias.setVisibility(View.VISIBLE);
		}
		if (passe.color.equals("gold")) {
			cor = passe.color;
			imgView.setImageResource(R.drawable.hsm_passes_title_gold);
		}
		if (passe.color.equals("red")) {
			cor = passe.color;
			imgView.setImageResource(R.drawable.hsm_passes_title_red);
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
							"Cadastro com dados inv‡lidos.", Toast.LENGTH_LONG)
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
