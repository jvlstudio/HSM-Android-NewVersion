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
	
    protected FileBitmapUtils mFileManager;
    protected String mFilePath;
    private Integer mPosition;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public ReadImageAsyncTask(FileBitmapUtils fileManager, String filePath, Integer position) {
	    mFileManager = fileManager;
	    mFilePath = filePath;
	    mPosition = position;
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
			Utils.fileLog("ReadImageAsyncTask.getBitmapFromDisk() -> Reading file " + mFilePath + ".");
			bitmap = mFileManager.readFile(mFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * Gets the {@link Bitmap} from the disk.
	 * 
	 * @retun The {@link Bitmap} from the disk.
	 */
	/*
	public Bitmap getBitmapFromDisk() {
		Bitmap bitmap = null;
		
		Utils.fileLog("ReadImageAsyncTask.getBitmapFromDisk() -> At position " + mPosition + ", reading file " + mFilePath + ".");
		bitmap = ContentManager.getInstance().getBitmapList().get(mPosition);
		return bitmap;
	}
	*/
}