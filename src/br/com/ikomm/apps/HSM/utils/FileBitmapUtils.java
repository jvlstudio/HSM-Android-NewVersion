package br.com.ikomm.apps.HSM.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * FileBitmapUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since Jun 15, 2012
 */
public class FileBitmapUtils {
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

    /**
     * Check if media store is mounted.
     * 
     * @return If media store is mounted, return true.
     */
    public Boolean mediaStorageIsMounted() {
    	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
    		return true;
    	return false;
    }
    
    /**
     * Checks if file exists.
     * 
     * @param file The file to be checked.
     * 
     * @return If file exists, return true, otherwise false.
     */
    public Boolean fileExists(File file) {
    	if (file.isFile() && file.exists())
    		return true;
    	return false;
    }

    /**
     * Creates a folder (is media store is mounted).
     *
     * @param appFolder The folder of the application.
     * 
     * @return The path of this folder.
     */
    public String createDir(String appFolder) {
    	String systemPath;
    	File dir;

		// Creates directory (if exists).
		systemPath = Environment.getExternalStorageDirectory().getPath() + appFolder;
		dir = new File(systemPath);
		dir.mkdir();

		return systemPath;
    }

    /**
     * Creates a file (if media store is mounted).
     * 
     * @param filePath Complete path of the file.
     * @param bitmap Bitmap to be saved into the file.
     * 
     * @return The created file.
     * 
     * @throws IOException
     */
    public File createFile(String filePath, Bitmap bitmap) throws IOException {
    	File file = new File(filePath);
    	
		// Creates file.
		OutputStream out = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		out.flush();
		out.close();
		
		return file;
    }
    
    /**
     * Read the content of a file.
     * 
     * @param filePath Path of the file to be read.
     * 
     * @return The content of the file.
     * 
     * @throws IOException
     */
    public Bitmap readFile(String filePath) throws IOException {
		Bitmap bitmap = null;
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		options.inPurgeable = true;
		options.inScaled = true;
		
		bitmap = BitmapFactory.decodeFile(filePath, options);
		
		return bitmap;
    }

    /**
     * Delete a file.
     */
    public void deleteFile(File file) {
   		file.delete();
    }
}