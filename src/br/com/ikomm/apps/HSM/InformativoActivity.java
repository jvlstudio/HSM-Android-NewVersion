package br.com.ikomm.apps.HSM;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.widget.TextView;
import br.ikomm.hsm.model.AuditorioTematico;
import br.ikomm.hsm.repo.AuditorioTematicoRepository;

public class InformativoActivity extends FragmentActivity {

	private AuditorioTematicoRepository mRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informativo);
		Intent intent = getIntent();
		int id = intent.getIntExtra("idAuditorioTematico", 0);
		if (id > 0) {
			mRepo = new AuditorioTematicoRepository();
			AuditorioTematico auditorio = mRepo.getById(id);
			
			ActionBar action = getActionBar();
			action.setLogo(R.drawable.hsm_logo);
			action.setTitle(auditorio.titulo);

			TextView textoPagina = (TextView) findViewById(R.id.textoPagina);
			textoPagina.setText(Html.fromHtml(auditorio.textoPagina));
		}
	}
}