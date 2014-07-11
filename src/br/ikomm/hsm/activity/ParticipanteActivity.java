package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Participante;
import br.ikomm.hsm.util.StringUtils;

/**
 * ParticipanteActivity.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class ParticipanteActivity extends FragmentActivity implements OnClickListener {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	public static Participante mParticipante = new Participante();
	int mVariavelBanner;
	private Long mEventId;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participante);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);

		findViewById(R.id.btnVoltar).setOnClickListener(this);
		Intent intent = getIntent();
		mVariavelBanner = intent.getIntExtra("var", -1);
		mEventId = intent.getLongExtra("event_id", -1);
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(getApplicationContext(), PagamentoActivity.class);
		if (validar()) {
			intent.putExtra("ok", 1);
		}else{
			intent.putExtra("ok", 0);
		}
		
		// Put extras.
		intent.putExtra("banner", mVariavelBanner);
		EditText tNome = (EditText) findViewById(R.id.tNome);
		EditText tEmail = (EditText) findViewById(R.id.tEmail);
		EditText tCPF = (EditText) findViewById(R.id.tCPF);
		EditText tEmpresa = (EditText) findViewById(R.id.tEmpresa);
		EditText tCargo = (EditText) findViewById(R.id.tCargo);

		intent.putExtra("passe", mEventId);
		intent.putExtra("email", tEmail.getText().toString());
		intent.putExtra("cpf", tCPF.getText().toString());
		intent.putExtra("empresa", tEmpresa.getText().toString());
		intent.putExtra("cargo", tCargo.getText().toString());
		intent.putExtra("nome", tNome.getText().toString());

		// Calls Intent.
		startActivity(intent);
		finish();
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	/**
	 * Validates each field.
	 * 
	 * @return
	 */
	private boolean validar() {
		TextView tNome = (TextView) findViewById(R.id.tNome);
		TextView tEmail = (TextView) findViewById(R.id.tEmail);
		TextView tCPF = (TextView) findViewById(R.id.tCPF);
		TextView tEmpresa = (TextView) findViewById(R.id.tEmpresa);
		TextView tCargo = (TextView) findViewById(R.id.tCargo);

		String nome = tNome.getText().toString();
		String email = tEmail.getText().toString();
		String empresa = tEmpresa.getText().toString();
		String cargo = tCargo.getText().toString();
		if (StringUtils.isEmpty(nome)) {
			return false;
		} else if (StringUtils.isEmpty(email)) {
			return false;
		} else if (!validaCPF(tCPF.getText().toString())) {
			return false;
		} else if (StringUtils.isEmpty(empresa)) {
			return false;
		} else if (StringUtils.isEmpty(cargo)) {
			return false;
		}
		return true;
	}

	/**
	 * Check if CPF is valid.
	 * 
	 * @param cpf
	 * @return
	 */
	public boolean validaCPF(String cpf) {
		String strCpf = cpf;
		if (strCpf.equals("")) {
			return false;
		}

		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;
		for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();
			d1 = d1 + (11 - nCount) * digitoCPF;
			d2 = d2 + (12 - nCount) * digitoCPF;
		}

		resto = (d1 % 11);
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;
		resto = (d2 % 11);
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
		return nDigVerific.equals(nDigResult);
	}
}