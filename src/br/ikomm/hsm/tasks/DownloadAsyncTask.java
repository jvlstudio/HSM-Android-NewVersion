package br.ikomm.hsm.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import br.ikomm.hsm.util.BitmapUtils;

/**
 * DownloadAsyncTask class.
 * 
 * @author Rodrigo Cericatto
 * @since Apr 4, 2014
 */
public class DownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private String mUrl;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public DownloadAsyncTask(String url) {
		mUrl = url;
	}

	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = BitmapUtils.downloadBitmap(mUrl);
		return bitmap;
	}
}