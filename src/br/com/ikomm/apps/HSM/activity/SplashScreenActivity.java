package br.com.ikomm.apps.HSM.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * SplashScreenActivity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class SplashScreenActivity extends Activity {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/events/";
	public static final Integer TIME = 3000;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Flag.
	private Boolean mActivityAlreadyAccessed = false;
	private Handler mHandler = new Handler();
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        
        mHandler.postDelayed(mHandlerChecker, TIME);
        Log.i(AppConfiguration.COMMON_LOGGING_TAG, Utils.getClassName(SplashScreenActivity.class) + "onCreate().");
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	
    	// Checks where the application will go.
    	if (mActivityAlreadyAccessed) {
			finish();
    	}
    	
        // Flag access of this activity.
    	mActivityAlreadyAccessed = true;
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mHandler.removeCallbacks(mHandlerChecker);
    }

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
    
	/**
	 * Goes to {@link HomeActivity}.
	 */
	public void callHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
	
	//--------------------------------------------------
	// Handlers
	//--------------------------------------------------
	
	/**
	 * Calls level list.
	 */
	private Runnable mHandlerChecker = new Runnable() {
	    public void run() {
	    	callHomeActivity();
	    }
	};
}