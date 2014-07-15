package br.ikomm.hsm.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.repo.EventRepo;
import br.ikomm.hsm.tasks.DownloadAsyncTask;
import br.ikomm.hsm.util.AsyncTaskUtils;
import br.ikomm.hsm.util.FileUtils;

/**
 * SplashScreen2Activity.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class SplashScreen2Activity extends Activity {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/events/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private FileUtils mFileManager = new FileUtils();
	private Integer mEventsSize = 0;
	private Integer mCount = 0;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen2);
        
        // Gets the Event list.
        EventRepo repo = new EventRepo(this);
        repo.open();
        List<Event> list = repo.getAllEvent();
        mEventsSize = list.size();
        repo.close();
        
        // Sets the image URL.
        String path = mFileManager.createDir(FileUtils.CACHE_DIR);
        if (list != null) {
	        for (Event event : list) {
	        	String imageUrl = URL + event.image_list;
	    		String completePath = path + event.image_list;
	    		// Checks the image URL.
	    		if (imageUrl != null) {
	    			downloadImage(imageUrl, completePath);
	    		}
	        }
        }
    }

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
    
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
				
				// Checks the number of images downloaded.
				if (mCount == mEventsSize) {
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