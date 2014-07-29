package br.com.ikomm.apps.HSM.task;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import br.com.ikomm.apps.HSM.utils.BitmapUtils;
import br.com.ikomm.apps.HSM.utils.FileBitmapUtils;
import br.com.ikomm.apps.HSM.utils.StringUtils;

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
    protected FileBitmapUtils mFileManager;
    protected String mFilePath;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public DownloadAsyncTask(String url, FileBitmapUtils fileManager, String filePath) {
		mUrl = url;
	    mFileManager = fileManager;
	    mFilePath = filePath;
	}

	//--------------------------------------------------
	// Async Task
	//--------------------------------------------------
	
	protected Bitmap doInBackground(String... params) {
		File file = new File(mFilePath);
		Bitmap bitmap = null;
		
		// Gets the bitmap from web or from disk.
		if (!mFileManager.fileExists(file)) {
			bitmap = getBitmapFromWeb(file);
		}
		
		return bitmap;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets the {@link Bitmap} from the web.
	 * 
	 * @param file The file to be created.
	 * 
	 * @return The {@link Bitmap} from the web.
	 */
	public Bitmap getBitmapFromWeb(File file) {
		Bitmap bitmap = null;
		
		// Check if the URL is null.
		if (!StringUtils.isEmpty(mUrl)) {
			bitmap = BitmapUtils.downloadBitmap(mUrl);
		}
		
		// Gets the Bitmap from the web.
		try {
			if (bitmap != null) {
				file = mFileManager.createFile(mFilePath, bitmap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}