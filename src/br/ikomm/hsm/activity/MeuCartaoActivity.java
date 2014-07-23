package br.ikomm.hsm.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.R.drawable;
import br.com.ikomm.apps.HSM.R.id;
import br.com.ikomm.apps.HSM.R.layout;
import br.com.ikomm.apps.HSM.R.menu;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.repo.CartaoRepository;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * MeuCartaoActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class MeuCartaoActivity extends SherlockActivity implements OnClickListener {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private CartaoRepository mCartaoRepo;
	
	private TextView mName;
	private TextView mEmail;
	private TextView mPhone;
	private TextView mMobilePhone;
	private TextView mCompany;
	private TextView mRole;
	private TextView mWebsite;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meu_cartao);

		setActionBar();
		setButton();
		setTextView();
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
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Sets the {@link Button}.
	 */
	public void setButton() {
		// Gets the CartaoRepo.
		mCartaoRepo = new CartaoRepository(MeuCartaoActivity.this);
		
		Button createCardButton = (Button)findViewById(R.id.id_create_card_button);
		createCardButton.setOnClickListener(this);
	}
	
	public void setTextView() {
		mName = (TextView) findViewById(R.id.id_complete_name_edit_text);
		mEmail = (TextView) findViewById(R.id.id_email_edit_text);
		mPhone = (TextView) findViewById(R.id.id_phone_edit_text);
		mMobilePhone = (TextView) findViewById(R.id.id_phone_edit_text);
		mCompany = (TextView) findViewById(R.id.id_phone_edit_text);
		mRole = (TextView) findViewById(R.id.id_role_edit_text);
		mWebsite = (TextView) findViewById(R.id.id_website_edit_text);
	}
	
	/**
	 * Sets error messages.
	 */
	public void setErrorMessages() {
		if (mName.getText().toString().isEmpty()) {
			Toast.makeText(MeuCartaoActivity.this, "Por favor, preencha seu nome.", Toast.LENGTH_SHORT).show();
		} else if (mEmail.getText().toString().isEmpty()) {
			Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um e-mail v�lido.", Toast.LENGTH_SHORT).show();
		} else if (mPhone.getText().toString().isEmpty()) {
			Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um telefone v�lido.", Toast.LENGTH_SHORT).show();
		} else if (mMobilePhone.getText().toString().isEmpty()) {
			Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um celular v�lido.", Toast.LENGTH_SHORT).show();
		} else if (mCompany.getText().toString().isEmpty()) {
			Toast.makeText(MeuCartaoActivity.this, "Por favor, insira uma empresa v�lida.", Toast.LENGTH_SHORT).show();
		} else if (mRole.getText().toString().isEmpty()) {
			Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um cargo v�lido.", Toast.LENGTH_SHORT).show();
		} else if (mWebsite.getText().toString().isEmpty()) {
			Toast.makeText(MeuCartaoActivity.this, "Por favor, insira um website v�lido.", Toast.LENGTH_SHORT).show();
		} else {
			setContacts();
		}
	}
	
	/**
	 * Sets the contacts.
	 */
	public void setContacts() {
		// Gets the current card.
		Cartao card = new Cartao();
		card.nome = mName.getText().toString();
		card.email = mEmail.getText().toString();
		card.telefone = mPhone.getText().toString();
		card.celular = mMobilePhone.getText().toString();
		card.empresa = mCompany.getText().toString();
		card.cargo = mRole.getText().toString();
		card.website = mWebsite.getText().toString();

		// Refreshes list.
		List<Cartao> lista = mCartaoRepo.getMeusContatos();
		if (lista == null) {
			lista = new ArrayList<Cartao>();
			lista.add(card);
			mCartaoRepo.setMeusContatos(lista);
		}
		
		// Calls NetworkingListActivity.
		Intent intent = new Intent(MeuCartaoActivity.this, NetworkingListActivity.class);
		startActivity(intent);
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		setErrorMessages();
	}
}