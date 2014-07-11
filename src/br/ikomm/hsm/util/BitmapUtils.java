package br.ikomm.hsm.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * BitmapUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since Apr 4, 2014
 */
public class BitmapUtils {
	
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