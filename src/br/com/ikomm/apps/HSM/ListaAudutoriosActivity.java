package br.com.ikomm.apps.HSM;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ikomm.hsm.adapter.SimplesAdapter;
import br.ikomm.hsm.model.AuditorioSimples;
import br.ikomm.hsm.model.AuditorioSimplesRepository;

public class ListaAudutoriosActivity extends FragmentActivity implements
		OnItemClickListener {

	private AuditorioSimplesRepository repo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_audutorios);

		Activity context = this;
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		ListView listView = (ListView) findViewById(R.id.listViewListaAuditorios);
		listView.setAdapter(new SimplesAdapter(context, this, true));

		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_audutorios, menu);
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id) {

		if (id > 0) {
			repo = new AuditorioSimplesRepository();
			AuditorioSimples auditorio = repo.getById((int) id);

			if (auditorio != null && !auditorio.link.isEmpty()) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(auditorio.link));
				startActivity(i);
			}
		}

	}

}
