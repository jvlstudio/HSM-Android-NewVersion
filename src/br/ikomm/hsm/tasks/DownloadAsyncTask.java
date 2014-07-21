package br.ikomm.hsm.tasks;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import br.ikomm.hsm.util.BitmapUtils;
import br.ikomm.hsm.util.FileUtils;
import br.ikomm.hsm.util.StringUtils;

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
    protected FileUtils mFileManager;
    protected String mFilePath;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public DownloadAsyncTask(String url, FileUtils fileManager, String filePath) {
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