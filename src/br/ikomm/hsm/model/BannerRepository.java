package br.ikomm.hsm.model;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class BannerRepository {
	public static final String BANNER_KEY = "BANNER_KEY";
	private String jsonBanners;
	SharedPreferences preferences;
	private Gson gson = new Gson();
	private String defaultBanners = 
			"[{'banner':'http://apps.ikomm.com.br/hsm5/uploads/ads/ad_ios_banner_footer_1394890887@2x.png','url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm5/uploads/ads/ad_ios_banner_footer_1394891124@2x.png','url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm5/uploads/ads/ad_ios_banner_footer_1394891263@2x.png'}]";//,'url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm/uploads/ads/ad4.png','url':'http://hsm.com.br'},{'banner':'http://apps.ikomm.com.br/hsm/uploads/ads/ad5.png','url':'http://komm.com.br'}]";
			//"[{'banner':'http://zoundsdesign.files.wordpress.com/2011/08/thift-store-finds-blue.jpg','url':'http://hsm.com.br'},{'banner':'http://spielstein.com/images/top_blue.en.jpg','url':'http://hsm.com.br'},{'banner':'http://www.alaska4x4network.com/images/misc/logo_light_blue.jpg','url':'http://hsm.com.br'},{'banner':'http://www.sbdental.com/wp-content/uploads/2012/11/hours-banner-blue.png','url':'http://hsm.com.br'},{'banner':'http://i1284.photobucket.com/albums/a567/times4u/logo/bannerfans_78902601_zpsbf957dcb.jpg','url':'http://komm.com.br'}]";
	
	public BannerRepository(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public List<Banner> getAll() {
		try {
			jsonBanners = getJsonFromShared();
			
			if (jsonBanners.isEmpty()) {
				Log.e("Banner", "getAll = Null");
				return RetornaBannersDefault();
			}

			Log.e("Banner", "getAll = " + jsonBanners);

			Banner[] banners = gson.fromJson(jsonBanners,
					Banner[].class);
			return Arrays.asList(banners);
		} catch (Exception e) {
			//Se der algum erro (internet, JSON convert)
			return RetornaBannersDefault();
		}
	}

	private List<Banner> RetornaBannersDefault() {
		Banner[] banners = gson.fromJson(defaultBanners,
				Banner[].class);
		return Arrays.asList(banners);
	}

	private String getJsonFromShared() {
		return preferences.getString(BANNER_KEY, "");
	}

	public void setJsonShared(String bannerJson) {
		try {
			if (bannerJson.isEmpty()) {
				Log.e("Banner", "setJsonShared = Null");
				return;
			} else {
				SharedPreferences.Editor editor = preferences.edit();
				bannerJson = bannerJson.replace("\\/", "/");
				Log.e("Banner", "setJsonShared = " + bannerJson);
				editor.putString(BANNER_KEY, bannerJson);
				editor.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
