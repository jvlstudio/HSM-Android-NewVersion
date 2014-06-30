package br.com.ikomm.apps.HSM;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;
import br.ikomm.hsm.model.AuditorioTematico;
import br.ikomm.hsm.model.AuditorioTematicoRepository;

public class InformativoActivity extends FragmentActivity {

	private AuditorioTematicoRepository repo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informativo);
		Intent intent = getIntent();
		int id = intent.getIntExtra("idAuditorioTematico", 0);
		if (id > 0) {
			repo = new AuditorioTematicoRepository();
			AuditorioTematico auditorio = repo.getById(id);
			
			ActionBar action = getActionBar();
			action.setLogo(R.drawable.hsm_logo);
			action.setTitle(auditorio.titulo);

			TextView textoPagina = (TextView) findViewById(R.id.textoPagina);
			textoPagina.setText(Html.fromHtml(auditorio.textoPagina));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.informativo, menu);
		return false;
	}

}
