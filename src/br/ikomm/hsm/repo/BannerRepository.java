package br.ikomm.hsm.repo;

import java.util.Arrays;
import java.util.List;

import br.ikomm.hsm.model.Banner;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BannerRepository {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String BANNER_KEY = "BANNER_KEY";
	
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
	
	public List<Banner> getAll() {
		try {
			mJsonBanners = getJsonFromShared();
			if (mJsonBanners.isEmpty()) {
				Log.e("Banner", "getAll = Null");
				return RetornaBannersDefault();
			}
			Log.e("Banner", "getAll = " + mJsonBanners);
			Banner[] banners = mGson.fromJson(mJsonBanners, Banner[].class);
			return Arrays.asList(banners);
		} catch (Exception e) {
			// If some error occurs (Internet, JSON parsing).
			return RetornaBannersDefault();
		}
	}

	private List<Banner> RetornaBannersDefault() {
		Banner[] banners = mGson.fromJson(mDefaultBanners, Banner[].class);
		return Arrays.asList(banners);
	}

	private String getJsonFromShared() {
		return mPreferences.getString(BANNER_KEY, "");
	}

	public void setJsonShared(String bannerJson) {
		try {
			if (bannerJson.isEmpty()) {
				Log.e("Banner", "setJsonShared = Null");
				return;
			} else {
				SharedPreferences.Editor editor = mPreferences.edit();
				bannerJson = bannerJson.replace("\\/", "/");
				Log.e("Banner", "setJsonShared = " + bannerJson);
				editor.putString(BANNER_KEY, bannerJson);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}