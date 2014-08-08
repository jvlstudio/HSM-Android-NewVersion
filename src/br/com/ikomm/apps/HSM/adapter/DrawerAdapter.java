package br.com.ikomm.apps.HSM.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * DrawerAdapter.java class.
 * 
 * @author Rodrigo Cericatto
 * @since August 8, 2014
 */
public class DrawerAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String BANNER_URL = "http://apps.ikomm.com.br/hsm5/uploads/ads/";
	public static final Integer BANNER_POSITION = 8;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Context mContext;
	private String[] mList;
	private String mMenuBannerUrl;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public DrawerAdapter(Context context, String[] list, String menuBannerUrl) {
		mContext = context;
		mList = list;
		mMenuBannerUrl = menuBannerUrl;
	}
	
	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mList.length;
	}
	
	@Override
	public String getItem(int position) {
		return mList[position];
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Integer resourceId = R.layout.adapter_drawer_text;
		TextView menuTextView = null;
		ImageView bannerImageView = null;
		
		if (position == BANNER_POSITION) {
			resourceId = R.layout.adapter_drawer_image;
			convertView = inflater.inflate(resourceId, parent, false);
			bannerImageView = (ImageView)convertView.findViewById(R.id.id_adapter_drawer_image_view);
			setUniversalImage(BANNER_URL + mMenuBannerUrl, bannerImageView);
		} else {
			convertView = inflater.inflate(resourceId, parent, false);
			menuTextView = (TextView)convertView.findViewById(R.id.id_adapter_drawer_text_view);
			menuTextView.setText(getItem(position));
		}
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 * @param imageView The image view to be updated.
	 */
	public void setUniversalImage(String url, ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		imageLoader.displayImage(url, imageView, cache);
	}
}