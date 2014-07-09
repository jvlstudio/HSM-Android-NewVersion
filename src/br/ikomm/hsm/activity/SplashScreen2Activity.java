package br.ikomm.hsm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;

/**
 * SplashScreen2Activity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class SplashScreen2Activity extends Activity implements Runnable {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private final Integer mSplashTime = 2000;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen2);
        
        Handler handler = new Handler();
        handler.postDelayed(this, mSplashTime);
    }

	//--------------------------------------------------
	// Runnable
	//--------------------------------------------------
    
	@Override
	public void run() {
		startActivity(new Intent(this, HomeActivity.class));
		finish();
	}
}