package br.com.ikomm.apps.HSM.task;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.utils.FileBitmapUtils;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * ReadImageAsyncTask class.
 * 
 * @author Rodrigo Cericatto
 * @since July 15, 2014
 */
public class ReadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
    private FileBitmapUtils mFileManager;
    private String mFilePath;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public ReadImageAsyncTask(FileBitmapUtils fileManager, String filePath) {
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
		if (mFileManager.fileExists(file)) {
			bitmap = getBitmapFromDisk();
		}
		
		return bitmap;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets the {@link Bitmap} from the disk and save it into the {@link ContentManager}.
	 * 
	 * @retun The {@link Bitmap} from the disk.
	 */
	public Bitmap getBitmapFromDisk() {
		Bitmap bitmap = null;
		try {
			bitmap = mFileManager.readFile(mFilePath);
			Utils.fileLog("ReadAsyncTask.getBitmapFromDisk() -> Adding to cache the file " + mFilePath + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}