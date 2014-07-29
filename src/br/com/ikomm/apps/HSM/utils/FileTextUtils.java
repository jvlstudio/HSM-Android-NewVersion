package br.com.ikomm.apps.HSM.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;
import br.com.ikomm.apps.HSM.R;

/**
 * FileTextUtils.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Dec 2, 2013
 */
public class FileTextUtils {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	// Path of the application text files.
	public static final String CACHE_DIR = "/Android/data/" + Utils.getPackageName(FileTextUtils.class) + "/";
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Creates a file and saves some content on it.
	 * 
	 * @param fileName The name of the file to be created.
	 * @param fileContent The content to be saved.
	 * 
	 * @throws IOException
	 */
    public void saveFile(String fileName, String fileContent)
    	throws IOException {
    	
        // If the media storage is mounted.
        if (mediaStorageIsMounted()) {
        	// Creates the directory.
        	String path = createDir();

        	// Checks if file exists.
        	File file = new File(path + fileName);
        	path += fileName;
        	if (!fileExists(file)) {
       			// Creates the file 'alarms.txt'.
       			createFile(path, fileContent);
        	} else {
        		// Edits the file.
       			editFile(path, file, fileContent);
        	}
        }
    }

    /**
     * Check if media store is mounted.
     * 
     * @return If media store is mounted, return true.
     */
    public Boolean mediaStorageIsMounted() {
    	if (Environment.getExternalStorageState().equals(
    		Environment.MEDIA_MOUNTED))
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
     * @return path The path of this folder.
     */
    public String createDir() {
    	String path = Environment.getExternalStorageDirectory().
    		getPath() + CACHE_DIR;
    	File dir;
    	
		// Creates directory (if exists).
		dir = new File(path);
		dir.mkdir();
		
		// Gets return value.
		return path;
    }

    /**
     * Creates a file (if media store is mounted).
     * 
     * @param path File path.
     * @param text Text to be append into the file.
     * 
     * @throws IOException
     */
    public void createFile(String path, String text) throws
    	IOException {
    	File file = new File(path);
    	
    	// Check if file exists and gets return value.
    	if (fileExists(file)) {
    		file = null;
    	}
    	// Creates file and gets return value.
    	else {
    		// Creates file.
			FileOutputStream out = new FileOutputStream(file);
			OutputStreamWriter writer = new
				OutputStreamWriter(out);
			writer.append(text);
			writer.close();
			out.close();
    	}
    }
    
    /**
     * Read the content of a file.
     * 
     * @param file The file to be read.
     * @return The content of the file.
     * @throws IOException
     */
    public String readFile(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new
			InputStreamReader(in));
		String buffer = "";
		String row = "";
		
		// Reads file content.
		while ((row = reader.readLine()) != null)
			buffer += row + "\n";
		reader.close();
		
		return buffer;
    }

    /**
     * Creates a file (if media store is mounted).
     * 
     * @param appPath Path of the file.
     * @param file File to be changed.
     * @param text Text to be append into the file.
     * 
     * @throws IOException
     */
    public Boolean editFile(String path, File file, String
    	text) throws IOException {
    	Boolean fileExists = false;
    	
    	// Check if file exists and gets return value.
    	if (fileExists(file)) {
    		// Gets return value.
    		fileExists = true;
    		
    		// Checks if 'text' will be attached or not.
			String fileContent = readFile(file);
			fileContent += text;
			
			// Saves 'text' in the file.
			FileOutputStream out = new FileOutputStream(file);
			OutputStreamWriter writer = new
				OutputStreamWriter(out);
			writer.append(fileContent);
			writer.close();
			out.close();
    	}
		// Gets return value.
    	else {
    		fileExists = false;
    	}
    	
		return fileExists;
    }
 
	//--------------------------------------------------
	// Log
	//--------------------------------------------------
    
    /**
	 * Debugs what is happening putting texts into a text file.
	 * This is the only way to know what is happening, because
	 * we can't debug at the boot of android.
	 * 
	 * @param context The context.
	 * @param text The log text.
	 */
	public void logFile(Context context, String text) {
		try {
			String fileName = context.getString(R.string.output_file);
			saveFile(fileName, text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}