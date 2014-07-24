package br.com.ikomm.apps.HSM.utils;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

/**
 * A group of utility methods.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class Utils {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private static Float mDensity;

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------	
	
	/**
	 * Returns the class name without package, if any.
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassName(Class<?> clazz) {
		String className = getFullClassName(clazz);
		int firstChar = className.lastIndexOf('.') + 1;
		if (firstChar > 0) {
			className = className.substring(firstChar);
		}
		return className;
	}
	
	/**
	 * Returns the full class name.
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getFullClassName(Class<?> clazz) {
		return clazz.getName();
	}

	/**
	 * Returns the package name of the given class.
	 * 
	 * @param clazz
	 * @return The package name.
	 */
	public static String getPackageName(Class<?> clazz) {
		return clazz.getPackage().getName();
	}
	
	/**
	 * Returns if the Wi-Fi or GPS location service is enabled.
	 * 
	 * @return
	 */
	public static boolean isLocationServiceEnabled(Context context) {
		String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		return !StringUtils.isEmpty(provider);
	}

	/**
     * Sleep current thread.
     * 
     * @param milliseconds
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Do nothing.
        }
    }

	/**
	 * Saves density of the screen.
	 * 
	 * @param density Screen density
	 */
	public static void setScreenDensity(Float density) {
		mDensity = density;
	}
	
	/**
	 * Converts density pixels to pixels.
	 * 
	 * @param dp Density pixels value to be converted to pixels.
	 * @return The conversion.
	 */
	public static Integer dpToPixel(int dp) {
		return (int)(dp * mDensity);
	}
	
    /**
     * Sets the preference of the application.
     * 
     * @param context The current context.
     * @param key The key of the preference.
     * @param status If the data were loaded.
     */
	public static void setPreference(Context context, String key, Boolean status) {
		// Gets the preference.
		SharedPreferences pref = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = pref.edit();
	    
		// Saves the preference.
	    editor.putBoolean(key, status);
	    editor.commit();
	}
	
    /**
     * Gets the preference of the application.
     * 
     * @param context The current context.
     * @páram key The key to be caught.
     * 
     * @return The preference value.
     */
	public static Boolean getPreference(Context context, String key) {
		SharedPreferences pref = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
	    Boolean status = pref.getBoolean(key, false);
	    return status;
	}
	
	/**
	 * Gets the current time in mili seconds.
	 * 
	 * @return The current time.
	 */
	public static Long getCurrentInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}
}