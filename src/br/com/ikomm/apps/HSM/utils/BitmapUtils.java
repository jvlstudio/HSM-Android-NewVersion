package br.com.ikomm.apps.HSM.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;

/**
 * BitmapUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since Jun 15, 2012
 */
public class BitmapUtils {
	
	//--------------------------------------------------
    // Methods
    //--------------------------------------------------
    
	/**
	 * Download an image.
	 * 
	 * @param url The URL of the image to be download.
	 * 
	 * @return The download image.
	 */
	public static Bitmap downloadBitmap(String url) {
		Bitmap bitmapImage = null;
		Bitmap bitmapReturn = null;
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
			
			// Reduce image size.
			if (bitmapImage != null) {
				bitmapReturn = Bitmap.createScaledBitmap(bitmapImage, bitmapImage.getWidth() / 2, bitmapImage.getHeight() / 2, true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bitmapReturn;
	}

    /**
     * Decodes image and scales it to reduce memory consumption.
     * 
     * @param file File image.
     * 
     * @return File image bitmap.
     */
	public static Bitmap decodeFile(File file) {
		try {
			// Decode image size.
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(file), null, o);

			// Find the correct scale value. It should be the power of 2.
			Integer requiredSize = 60, widthTmp = o.outWidth, heightTmp = o.outHeight, scale = 1;
			while (true) {
				if (widthTmp / 2 < requiredSize || heightTmp / 2 < requiredSize)
					break;
				widthTmp /= 2;
				heightTmp /= 2;
				scale++;
			}

			// Decode with inSampleSize.
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;

			return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
		} catch (FileNotFoundException e) {}

		return null;
	}

	/**
	 * Resizes a bitmap.
	 * 
	 * @param bitmap Bitmap to be resized.
	 * @param newHeight Height of the new bitmap.
	 * @param newWidth Width of the new bitmap.
	 * 
	 * @return New bitmap.
	 */
	public static Bitmap getResizedBitmap(Bitmap bitmap, int newHeight, int newWidth) {
		Integer width = bitmap.getWidth(), height = bitmap.getHeight();
		Float scaleWidth = ((float) newWidth) / width, scaleHeight = ((float) newHeight) / height;
		
		// Create a matrix for the manipulation, resize the bitmap and create the new bitmap.
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		
		return resizedBitmap;
	}
	
	/**
	 * Copy one bitmap to another.
	 * 
	 * @param in Entry bitmap.
	 * 
	 * @return The copied bitmap.
	 */
	public static Bitmap copyBitmap(Bitmap in) {
		Integer inWidth = in.getWidth();
	    Integer inHeight = in.getHeight();
		Bitmap out = Bitmap.createBitmap(inWidth, inHeight, Bitmap.Config.ARGB_8888);
		
	    int[] pixels = new int[inWidth * inHeight];
	    in.getPixels(pixels, 0, inWidth, 0, 0, inWidth, inHeight);
	    out.setPixels(pixels, 0, inWidth, 0, 0, inWidth, inHeight);
	    
	    return out;
	}
}