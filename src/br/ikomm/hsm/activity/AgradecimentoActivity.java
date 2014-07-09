package br.ikomm.hsm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;

public class AgradecimentoActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_agradecimento);
		
		findViewById(R.id.btnVoltar).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, HomeActivity.class));
		finish();
	}
}