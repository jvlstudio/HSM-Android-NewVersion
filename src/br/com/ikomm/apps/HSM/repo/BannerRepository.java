package br.com.ikomm.apps.HSM.repo;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.model.Banner;

import com.google.gson.Gson;

/**
 * BannerRepository.java class.
 * Modified by Rodrigo Cericatto at July 30, 2014.
 */
public class BannerRepository {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String BANNER_KEY = "banner_key";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String mJsonBanners;
	private SharedPreferences mPreferences;
	private Gson mGson = new Gson();
	private String mDefaultBanners = 
		"[{'banner':'http://apps.ikomm.com.br/hsm5/uploads/ads/ad_ios_banner_footer_1394890887@2x.png','url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm5/uploads/ads/ad_ios_banner_footer_1394891124@2x.png','url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm5/uploads/ads/ad_ios_banner_footer_1394891263@2x.png'}]";//,'url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm/uploads/ads/ad4.png','url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm/uploads/ads/ad5.png','url':'http://komm.com.br'}]";
		//"[{'banner':'http://zoundsdesign.files.wordpress.com/2011/08/thift-store-finds-blue.jpg','url':'http://hsm.com.br'},{'banner':'http://spielstein.com/images/top_blue.en.jpg','url':'http://hsm.com.br'},{'banner':'http://www.alaska4x4network.com/images/misc/logo_light_blue.jpg','url':'http://hsm.com.br'},{'banner':'http://www.sbdental.com/wp-content/uploads/2012/11/hours-banner-blue.png','url':'http://hsm.com.br'},{'banner':'http://i1284.photobucket.com/albums/a567/times4u/logo/bannerfans_78902601_zpsbf957dcb.jpg','url':'http://komm.com.br'}]";
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public BannerRepository(Context context) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets all {@link Banner} list.
	 * 
	 * @return
	 */
	public List<Banner> getAll() {
		try {
			mJsonBanners = getJsonFromShared();
			if (mJsonBanners.isEmpty()) {
				Log.i(AppConfiguration.COMMON_LOGGING_TAG, "Banner getAll is Null.");
				return getDefaultBanners();
			}
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "getAll is " + mJsonBanners);
			Banner[] banners = mGson.fromJson(mJsonBanners, Banner[].class);
			return Arrays.asList(banners);
		} catch (Exception e) {
			// If some error occurs (Internet, JSON parsing).
			return getDefaultBanners();
		}
	}

	/**
	 * Gets the default {@link Banner} list.
	 *  
	 * @return
	 */
	private List<Banner> getDefaultBanners() {
		Banner[] banners = mGson.fromJson(mDefaultBanners, Banner[].class);
		return Arrays.asList(banners);
	}

	/**
	 * Gets {@link Banner} JSON from the {@link Preference}. 
	 * 
	 * @return
	 */
	private String getJsonFromShared() {
		return mPreferences.getString(BANNER_KEY, "");
	}
	
	/**
	 * Sets the {@link Banner} into a {@link Preference}.
	 * 
	 * @param bannerJson
	 */
	public void setJsonShared(String bannerJson) {
		try {
			if (bannerJson.isEmpty()) {
				Log.e("Banner", "setJsonShared = Null");
				return;
			} else {
				SharedPreferences.Editor editor = mPreferences.edit();
				bannerJson = bannerJson.replace("\\/", "/");
				Log.i(AppConfiguration.COMMON_LOGGING_TAG, "setJsonShared is " + bannerJson);
				editor.putString(BANNER_KEY, bannerJson);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}