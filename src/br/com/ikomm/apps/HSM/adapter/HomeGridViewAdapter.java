package br.com.ikomm.apps.HSM.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import br.com.ikomm.apps.HSM.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * HomeGridViewAdapter.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 25, 2014
 */
public class HomeGridViewAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/home/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private List<String> mUrlList;

	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	static class ViewHolder {
		private ImageView imageView;
	}
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public HomeGridViewAdapter(Activity activity, List<String> urlList) {
		super();
		mActivity = activity;
		mUrlList = urlList;
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------

	@Override
	public int getCount() {
		return mUrlList.size();
	}

	@Override
	public String getItem(int position) {
		return mUrlList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();

		if (convertView == null) {
			// Sets layout.
			LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_home, parent, false);

			// Set views.
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.id_image_view);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		setImages(position, viewHolder);
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Sets all images.
	 * 
	 * @param position
	 */
	public void setImages(Integer position, ViewHolder viewHolder) {
		String completeUrl = URL + getItem(position);
		switch (position) {
			case 0:
				setUniversalImage(completeUrl, viewHolder.imageView);
				break;
			case 1:
				setUniversalImage(completeUrl, viewHolder.imageView);
				break;
			case 2:
				setUniversalImage(completeUrl, viewHolder.imageView);
				break;
			case 3:
				setUniversalImage(completeUrl, viewHolder.imageView);
				break;
		}
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage(String url, ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mActivity));
		imageLoader.displayImage(url, imageView, cache);
	}
}