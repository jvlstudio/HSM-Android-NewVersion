package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.repo.PasseRepo;
import br.ikomm.hsm.util.WebServiceCommunication;

/**
 * PagamentoActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class PagamentoActivity extends FragmentActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private int mIcCadastro = 0;
	private int mBanners = -1;
	private String mNome = "";
	private String mEmail = "";
	private String mCpf = "";
	private String mEmpresa = "";
	private String mCargo = "";
	private String mCor = "";
	private String mDia = "";
	private Long mEventId;
	
	private PasseRepo mPasseRepo;
	private Passe mPasse = new Passe();

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagamento);

		Bundle extras = getIntent().getExtras();
		Intent intent = getIntent();
		if (extras != null) {
			mEventId = extras.getLong("passe");
			if (mEventId != null) {
				// Gets user info.
				mNome = intent.getStringExtra("nome");
				mEmail = intent.getStringExtra("email");
				mCpf = intent.getStringExtra("cpf");
				mEmpresa = intent.getStringExtra("empresa");
				mCargo = intent.getStringExtra("cargo");
				
				// Gets the current passe.
				mPasseRepo = new PasseRepo(getBaseContext());
				mPasseRepo.open();
				mPasse = mPasseRepo.getPasse(mEventId);
				mPasseRepo.close();
			}
		}
		mIcCadastro = intent.getIntExtra("ok", 0);
		mBanners = intent.getIntExtra("banner", -1);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		ImageView imgView = (ImageView) findViewById(R.id.imgPagamento);
		TextView tData = (TextView) findViewById(R.id.lbDias);
		
		// Quantidade Spinner.
		Spinner dias = (Spinner) findViewById(R.id.spinerQuantidade);

		// Colorizes the image view.
		if (mPasse.color.equals("green")) {
			mCor = mPasse.color;
			imgView.setImageResource(R.drawable.hsm_passes_title_green);
			tData.setVisibility(View.VISIBLE);
			dias.setVisibility(View.VISIBLE);
		}
		if (mPasse.color.equals("gold")) {
			mCor = mPasse.color;
			imgView.setImageResource(R.drawable.hsm_passes_title_gold);
		}
		if (mPasse.color.equals("red")) {
			mCor = mPasse.color;
			imgView.setImageResource(R.drawable.hsm_passes_title_red);
		}
		addListenerOnButton();
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	private void addListenerOnButton() {
		findViewById(R.id.btnComprar).setOnClickListener(
			new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mIcCadastro == 1) {
						startActivity(new Intent(PagamentoActivity.this, AgradecimentoActivity.class));
						WebServiceCommunication ws = new WebServiceCommunication();
						if (mCor.equals("green")) {
							Spinner SpinnerDia = (Spinner) findViewById(R.id.spinerQuantidade);
							if (SpinnerDia.getSelectedItem() != null) {
								mDia = SpinnerDia.getSelectedItem().toString();
							}
						}
						if (!mCor.isEmpty() && !mNome.isEmpty() && !mEmail.isEmpty() && !mEmpresa.isEmpty() && !mCargo.isEmpty()  && !mCpf.isEmpty()) {
							ws.sendFormularioCompra(mCor, mDia, mNome, mEmail, mEmpresa, mCargo, mCpf);
						}
						finish();
					} else {
						Toast.makeText(PagamentoActivity.this, "Cadastro com dados inv‡lidos.", Toast.LENGTH_LONG).show();
					}
				}
			}
		);

		findViewById(R.id.btnParticipante).setOnClickListener(
			new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(PagamentoActivity.this, ParticipanteActivity.class);
					intent.putExtra("var", mBanners);
					intent.putExtra("event_id", mEventId);
					startActivity(intent);
				}
			}
		);
	}
}