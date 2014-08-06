package br.com.ikomm.apps.HSM.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Panelist;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * PanelistGridViewAdapter.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 11, 2014
 */
public class PanelistGridViewAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/panelists/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ViewHolder mViewHolder;
	private Activity mActivity;
	private List<Panelist> mPanelistList;

	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	static class ViewHolder {
		private TextView nameTextView;
		private ImageView pictureImageView;
	}
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public PanelistGridViewAdapter(Activity activity, List<Panelist> panelistList) {
		super();
		mActivity = activity;
		mPanelistList = panelistList;
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------

	@Override
	public int getCount() {
		return mPanelistList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		Panelist item = mPanelistList.get(position);
		return item.id;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		mViewHolder = new ViewHolder();

		if (convertView == null) {
			// Sets layout.
			LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_panelist, parent, false);

			// Set views.
			mViewHolder.nameTextView = (TextView)convertView.findViewById(R.id.nomePalestrante);
			mViewHolder.pictureImageView = (ImageView)convertView.findViewById(R.id.imagemPalestrante);
			
			// Saves ViewHolder into the tag.
			convertView.setTag(mViewHolder);
		} else {
			// Gets ViewHolder from the tag.
			mViewHolder = (ViewHolder)convertView.getTag();
		}
		
		// Sets data.
		setData(position);
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Sets the data of this {@link Adapter}.
	 * 
	 * @param position
	 */
	public void setData(Integer position) {
		Panelist panelist = mPanelistList.get(position);
		mViewHolder.nameTextView.setText(panelist.getName());

		// Creates URL for the image.
		String completeUrl = URL + panelist.picture;
		setUniversalImage(completeUrl, mViewHolder.pictureImageView);
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