package br.com.ikomm.apps.HSM.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.repo.EventRepo;
import br.com.ikomm.apps.HSM.task.DownloadAsyncTask;
import br.com.ikomm.apps.HSM.utils.AsyncTaskUtils;
import br.com.ikomm.apps.HSM.utils.FileUtils;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * SplashScreenEndActivity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class SplashScreenEndActivity extends Activity {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/events/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private FileUtils mFileManager = new FileUtils();
	private List<Event> mEventList;
	private Integer mEventListSize = 0;
	private Integer mCount = 0;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen_end);
        
        downloadEventImages();
        Log.i(SplashScreenActivity.TAG, Utils.getClassName(SplashScreenEndActivity.class) + "onCreate().");
    }

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
    
    /**
     * Download all {@link Event} images.
     */
    public void downloadEventImages() {
    	Log.i(SplashScreenActivity.TAG, Utils.getClassName(SplashScreenEndActivity.class) + "downloadEventImages().");
    	
    	// Gets Event list.
    	getEventListSize();
    	
    	// Sets the image URL.
        String path = mFileManager.createDir(FileUtils.CACHE_DIR);
        if (mEventList != null) {
	        for (Event event : mEventList) {
	        	String imageUrl = URL + event.image_list;
	    		String completePath = path + event.image_list;
	    		Log.i(SplashScreenActivity.TAG, Utils.getClassName(SplashScreenEndActivity.class) +
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
        EventRepo repo = new EventRepo(this);
        repo.open();
        mEventList = repo.getAllEvent();
        mEventListSize = mEventList.size();
        repo.close();
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
				
				Log.i(SplashScreenActivity.TAG, Utils.getClassName(SplashScreenEndActivity.class) +
		    		"downloadImage(). Count is " + mCount + " and Event List size is " + mEventListSize + ".");
				// Checks the number of images downloaded.
				if (mCount == mEventListSize) {
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
		startActivity(new Intent(this, HomeActivity.class));
		finish();
	}
}