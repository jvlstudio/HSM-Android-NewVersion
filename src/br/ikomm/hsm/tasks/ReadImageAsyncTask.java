package br.ikomm.hsm.tasks;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import br.ikomm.hsm.util.FileUtils;

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
	
    protected FileUtils mFileManager;
    protected String mFilePath;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public ReadImageAsyncTask(FileUtils fileManager, String filePath) {
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
	 * Gets the {@link Bitmap} from the disk.
	 * 
	 * @retun The {@link Bitmap} from the disk.
	 */
	public Bitmap getBitmapFromDisk() {
		Bitmap bitmap = null;
		try {
			bitmap = mFileManager.readFile(mFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}