package br.com.ikomm.apps.HSM;

import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class AuditoriosActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auditorios);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		addListenerButton();
	}

	private void addListenerButton() {
		findViewById(R.id.ibtnBDW1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AuditoriosActivity.this, BDWActivity.class));
			}
		});
		
		findViewById(R.id.ibtnAuditorio).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AuditoriosActivity.this, EstacaoConhecimentoActivity.class));
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auditorios, menu);
		return false;
	}

}
