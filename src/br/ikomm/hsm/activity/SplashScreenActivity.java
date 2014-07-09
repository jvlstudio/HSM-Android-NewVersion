package br.ikomm.hsm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;

/**
 * SplashScreenActivity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class SplashScreenActivity extends Activity implements Runnable {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private final Integer mSplashTime = 2000;
	private Boolean mSplash2 = true;
	
	//--------------------------------------------------
	// Activity life Cycle
	//--------------------------------------------------
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        
        // Calls the service.
        startService(new Intent("StartService"));
        Handler handler = new Handler();
        handler.postDelayed(this, mSplashTime);
    }
    
	//--------------------------------------------------
	// Runnable
	//--------------------------------------------------

	@Override
	public void run() {
		if (mSplash2){
			startActivity(new Intent(this, SplashScreen2Activity.class));
			finish();
		} else {
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}
	}
}