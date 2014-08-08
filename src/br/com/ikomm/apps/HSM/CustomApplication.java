package br.com.ikomm.apps.HSM;

import android.app.Application;
import br.com.ikomm.apps.HSM.database.DatabaseHelper;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.manager.HttpManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * A custom application class to manage the global application state.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class CustomApplication extends Application {
	
	//--------------------------------------------------
	// Application Life Cycle Methods
	//--------------------------------------------------
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Setting the database helper class.
		OpenHelperManager.setOpenHelperClass(DatabaseHelper.class);
		// Initializing the http manager.
		HttpManager.getInstance().setContext(this);
		// Initializing the content manager.
		ContentManager.getInstance().setContext(this);
		// Setting device density.
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		// Shut down HTTP connections
		HttpManager.getInstance().shutdownHttpClient();
        // Cleaning cached content.
      	ContentManager.getInstance().clean();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		// Releasing the helper.
		OpenHelperManager.releaseHelper();
		// Shut down HTTP connections
		HttpManager.getInstance().shutdownHttpClient();
        // Cleaning cached content.
      	ContentManager.getInstance().clean();
	}
}