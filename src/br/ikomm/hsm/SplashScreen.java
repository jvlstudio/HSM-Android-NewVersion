package br.ikomm.hsm;

import br.com.ikomm.apps.HSM.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class SplashScreen extends Activity implements Runnable{
	private final int splashTime = 2000;
	boolean splash2 = true;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        
        // Inicia o serviço.
        startService(new Intent("StartService"));
        
        Handler handler = new Handler();
        handler.postDelayed(this, splashTime);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }


	@Override
	public void run() {
		if (splash2){
			startActivity(new Intent(this, SplashScreen2.class));
			finish();
		} else {
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}
		
	}
    
}
