package br.com.ikomm.apps.HSM;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import br.ikomm.hsm.model.AuditorioTematico;
import br.ikomm.hsm.repo.AuditorioTematicoRepository;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * InformativoActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class InformativoActivity extends SherlockFragmentActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private AuditorioTematicoRepository mRepo;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
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
			action.setDisplayHomeAsUpEnabled(true);

			TextView textoPagina = (TextView) findViewById(R.id.textoPagina);
			textoPagina.setText(Html.fromHtml(auditorio.textoPagina));
		}
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu. This code adds items to the action bar.
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
}