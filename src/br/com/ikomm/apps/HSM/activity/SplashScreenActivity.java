package br.com.ikomm.apps.HSM.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.CustomApplication;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.task.DownloadAsyncTask;
import br.com.ikomm.apps.HSM.utils.AsyncTaskUtils;
import br.com.ikomm.apps.HSM.utils.FileBitmapUtils;
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
	public static final String IMAGES_DOWNLOADED = "images_already_downloaded"; 
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private FileBitmapUtils mFileManager = new FileBitmapUtils();
	private List<Event> mEventList;
	private Integer mEventListSize = 0;
	private Integer mCount = 0;
	
	private Boolean mEventListImagesDownloaded = false;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        
        Boolean imagesDownloaded = getPreference(this, IMAGES_DOWNLOADED);
        if (!imagesDownloaded) {
        	downloadEventImages();
        }
        callHomeActivity();
        Log.i(AppConfiguration.COMMON_LOGGING_TAG, Utils.getClassName(SplashScreenActivity.class) + "onCreate().");
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	finish();
    }

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
    
    /**
     * Download all {@link Event} images.
     */
    public void downloadEventImages() {
    	Log.i(AppConfiguration.COMMON_LOGGING_TAG, Utils.getClassName(SplashScreenActivity.class) + "downloadEventImages().");
    	
    	// Gets Event list.
    	getEventListSize();
    	
    	// Sets the image URL.
        String path = mFileManager.createDir(CustomApplication.CACHE_DIR);
        if (mEventList != null) {
	        for (Event event : mEventList) {
	        	String imageUrl = URL + event.image_list;
	    		String completePath = path + event.image_list;
	    		Log.i(AppConfiguration.COMMON_LOGGING_TAG, Utils.getClassName(SplashScreenActivity.class) +
	    			"downloadEventImages(). Image url is " + imageUrl + " and image path is " + completePath + ".");
	    		// Checks the image URL.
	    		if (imageUrl != null) {
	    			downloadImage(imageUrl, completePath);
	    		}
	        }
        }
    }
    
    /**
     * Gets the {@link Event} list size.
     */
    public void getEventListSize() {
    	mEventList = ContentManager.getInstance().getCachedEventList();
        mEventListSize = mEventList.size();
    }
    
	/**
	 * Downloads an image.
	 * 
	 * @param url The image url.
	 * @param path The image path into the phone. 
	 */
	public void downloadImage(String url, String path) {
		DownloadAsyncTask task = new DownloadAsyncTask(url, mFileManager, path) {
			protected void onPostExecute(Bitmap bitmap) {
				// Increments the counter.
				mCount++;
				
				Log.i(AppConfiguration.COMMON_LOGGING_TAG, Utils.getClassName(SplashScreenActivity.class) +
		    		"downloadImage(). Count is " + mCount + " and Event List size is " + mEventListSize + ".");
				// Checks the number of images downloaded.
				if (mCount == mEventListSize) {
					mEventListImagesDownloaded = true;
					setPreference(SplashScreenActivity.this, IMAGES_DOWNLOADED, mEventListImagesDownloaded);
					callHomeActivity();
				}
			};
		};
		AsyncTaskUtils.execute(task, new String[] {});
	}
	
	/**
	 * Goes to {@link HomeActivity}.
	 */
	public void callHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivityForResult(intent, 0);
	}
	
	//--------------------------------------------------
	// Preferences
	//--------------------------------------------------
	
    /**
     * Sets the preference of the application.
     * 
     * @param context The current context.
     * @param key The key of the preference.
     * @param status If the data were loaded.
     */
	public static void setPreference(Context context, String key, Boolean status) {
		// Gets the preference.
		SharedPreferences pref = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = pref.edit();
	    
		// Saves the preference.
	    editor.putBoolean(key, status);
	    editor.commit();
	}
	
    /**
     * Gets the preference of the application.
     * 
     * @param context The current context.
     * @páram key The key to be caught.
     * 
     * @return The preference value.
     */
	public static Boolean getPreference(Context context, String key) {
		SharedPreferences pref = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
	    Boolean status = pref.getBoolean(key, false);
	    return status;
	}
}