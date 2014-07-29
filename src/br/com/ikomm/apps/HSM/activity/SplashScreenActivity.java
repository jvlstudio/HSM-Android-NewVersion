package br.com.ikomm.apps.HSM.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * SplashScreenActivity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class SplashScreenActivity extends Activity implements Runnable {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String TAG = "hsm";
	
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
        
        // Sets the global context of the application.
        ContentManager.getInstance().setContext(this);
        Utils.fileLog("SplashScreenActivity.onCreate() -> " + "--------------------------------------------------");
        
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
			startActivity(new Intent(this, SplashScreenEndActivity.class));
			finish();
		} else {
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}
	}
}