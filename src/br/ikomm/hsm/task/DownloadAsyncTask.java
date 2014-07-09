package br.ikomm.hsm.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.LinearLayout;

/**
 * DownloadAsyncTask class.
 * 
 * @author Rodrigo Cericatto
 * @since July 7, 2014
 */
public class DownloadAsyncTask extends AsyncTask<Void, Integer, Bitmap> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String mUrl;
	private LinearLayout mLinearLayout;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	/**
	 * Creates a new DownloadAsyncTask instance.
	 */
	public DownloadAsyncTask(String url, LinearLayout linearLayout) {
		mUrl = url;
		mLinearLayout = linearLayout;
	}

	//----------------------------------------------
	// Async Task
	//----------------------------------------------
	
	@Override
	protected Bitmap doInBackground(Void... params) {
		Bitmap bitmap = downloadBitmap(mUrl);
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		Drawable drawable = new BitmapDrawable(result);
		mLinearLayout.setBackgroundDrawable(drawable);
	}
	
	//----------------------------------------------
	// Methods
	//----------------------------------------------
	
	/**
	 * Download an image.
	 * 
	 * @param url The URL of the image to be download.
	 * 
	 * @return The download image.
	 */
	public static Bitmap downloadBitmap(String url) {
		Bitmap bitmapImage = null;
		URL fileUrl = null;
		
		try {
			fileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			// Gets the connection with the URL.
			HttpURLConnection conn = (HttpURLConnection)fileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();

			// If data couldn't be downloaded.
			if (conn.getResponseCode() == -1) {
				int[] colors = new int[1];
				colors[0] = Color.BLACK;
				Bitmap defaultBitmap = Bitmap.createBitmap(colors, 60, 60, Bitmap.Config.ARGB_8888);
				
				return defaultBitmap;
			}

			// Gets the image from URL.
			InputStream is = conn.getInputStream();
			bitmapImage = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bitmapImage;
	}
}