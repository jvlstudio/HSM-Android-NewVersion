package br.ikomm.hsm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;

public class SplashScreen extends Activity implements Runnable{
	private final int splashTime = 2000;
	boolean splash2 = true;
	public static String FAKE_AGENDA_JSON = "";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        
        // Inicia o serviço.
        startService(new Intent("StartService"));
        
        Handler handler = new Handler();
        handler.postDelayed(this, splashTime);
        
        // If we don't have data, we must create fake data.
//        FAKE_AGENDA_JSON = readFromAssetfile("agenda.json", this);
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
	
	//--------------------------------------------------
	// Fake Data
	//--------------------------------------------------
	
	public String readFromAssetfile(String fileName, Context context) {
	    StringBuilder returnString = new StringBuilder();
	    InputStream inputStream = null;
	    InputStreamReader inputStreamReader = null;
	    BufferedReader bufferedReader = null;
	    try {
	        inputStream = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
	        inputStreamReader = new InputStreamReader(inputStream);
	        bufferedReader = new BufferedReader(inputStreamReader);
	        String line = "";
	        while ((line = bufferedReader.readLine()) != null) {
	            returnString.append(line);
	        }
	    } catch (Exception e) {
	        e.getMessage();
	    } finally {
	        try {
	            if (inputStreamReader != null)
	                inputStreamReader.close();
	            if (inputStream != null)
	                inputStream.close();
	            if (bufferedReader != null)
	                bufferedReader.close();
	        } catch (Exception e2) {
	            e2.getMessage();
	        }
	    }
	    return returnString.toString();
	}
    
}
