package br.ikomm.hsm;

import br.com.ikomm.apps.HSM.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class DetalheEventoActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_evento);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		findViewById(R.id.btnAgenda).setOnClickListener(onClickAgenda());
		findViewById(R.id.btnPasses).setOnClickListener(onClickPasses());
	}

	private OnClickListener onClickPasses() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(DetalheEventoActivity.this, PacoteActivity.class));
			}
		};
	}

	private OnClickListener onClickAgenda() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(DetalheEventoActivity.this, AgendaActivity.class));
				
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_evento, menu);
		return false;
	}

}
