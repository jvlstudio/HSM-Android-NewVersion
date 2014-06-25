package br.ikomm.hsm;

import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Participante;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ParticipanteActivity extends FragmentActivity implements OnClickListener {

	public static Participante participante = new Participante();
	int variavelBanner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participante);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		findViewById(R.id.btnVoltar).setOnClickListener(this);
		Intent intent = getIntent();
		variavelBanner = intent.getIntExtra("var", -1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.participante, menu);
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),
				PagamentoActivity.class);

		if (validar()) {
			// popularParticipante();
			intent.putExtra("ok", 1);
		}else{
			intent.putExtra("ok", 0);
		}
		
		intent.putExtra("banner", variavelBanner);
		
		EditText tNome = (EditText) findViewById(R.id.tNome);
		EditText tEmail = (EditText) findViewById(R.id.tEmail);
		EditText tCPF = (EditText) findViewById(R.id.tCPF);
		EditText tEmpresa = (EditText) findViewById(R.id.tEmpresa);
		EditText tCargo = (EditText) findViewById(R.id.tCargo);

		intent.putExtra("email", tEmail.getText().toString());
		intent.putExtra("cpf", tCPF.getText().toString());
		intent.putExtra("empresa", tEmpresa.getText().toString());
		intent.putExtra("cargo", tCargo.getText().toString());
		intent.putExtra("nome", tNome.getText().toString());

		startActivity(intent);
		finish();
	}

	/*
	 * private void popularParticipante() { TextView tNome = (TextView)
	 * findViewById(R.id.tNome); TextView tEmail = (TextView)
	 * findViewById(R.id.tEmail); TextView tCPF = (TextView)
	 * findViewById(R.id.tCPF); TextView tEmpresa = (TextView)
	 * findViewById(R.id.tEmpresa); TextView tCargo = (TextView)
	 * findViewById(R.id.tCargo);
	 * 
	 * if (tNome.getText() != "" ){ participante.Nome = (String)
	 * tNome.getText(); }
	 * 
	 * if (tEmail.getText() != "" ){ participante.Email = (String)
	 * tEmail.getText(); }
	 * 
	 * if (tCPF.getText() != "" ) { participante.CPF = (String) tCPF.getText();
	 * }
	 * 
	 * if (tEmpresa.getText() != "" ) { participante.Empresa = (String)
	 * tEmpresa.getText(); }
	 * 
	 * if (tCargo.getText() != "" ) { participante.Cargo = (String)
	 * tCargo.getText(); } }
	 */

	private boolean validar() {
		// TODO Auto-generated method stub
		TextView tNome = (TextView) findViewById(R.id.tNome);
		TextView tEmail = (TextView) findViewById(R.id.tEmail);
		TextView tCPF = (TextView) findViewById(R.id.tCPF);
		TextView tEmpresa = (TextView) findViewById(R.id.tEmpresa);
		TextView tCargo = (TextView) findViewById(R.id.tCargo);

		if (tNome.getText() == "") {
			return false;
		} else if (tEmail.getText() == "") {
			return false;
		} else if (!validaCPF(tCPF.getText().toString())) {
			return false;
		} else if (tEmpresa.getText() == "") {
			return false;
		} else if (tCargo.getText() == "") {
			return false;
		}

		return true;
	}

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
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount))
					.intValue();

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

		String nDigVerific = strCpf.substring(strCpf.length() - 2,
				strCpf.length());

		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		return nDigVerific.equals(nDigResult);
	}

}
