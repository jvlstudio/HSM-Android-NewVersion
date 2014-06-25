package br.com.ikomm.apps.HSM;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ikomm.hsm.adapter.SimplesAdapter;
import br.ikomm.hsm.model.AuditorioTematicoRepository;

public class EstacaoConhecimentoActivity extends FragmentActivity implements
		OnItemClickListener {

	private AuditorioTematicoRepository auditorioTematicoRepo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estacao_conhecimento);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		Activity context = this;

		ListView listView = (ListView) findViewById(R.id.listViewEstacaoConhecimento);
		listView.setAdapter(new SimplesAdapter(context, this, false));

		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estacao_conhecimento, menu);
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> s, View v, int pos, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(EstacaoConhecimentoActivity.this,
				InformativoActivity.class);
		intent.putExtra("idAuditorioTematico", (int)id);
		startActivity(intent);
	}

}
