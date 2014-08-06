package br.com.ikomm.apps.HSM.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Magazine;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * MagazineAdapter.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class MagazineAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	public static final String MAGAZINE_URL = "https://play.google.com/store/apps/details?id=com.hsm.management&hl=en";
	public static final String IMAGE_URL = "http://apps.ikomm.com.br/hsm5/uploads/magazines/";
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private ViewHolder mViewHolder;
	private Activity mActivity;
	private List<Magazine> mMagazineList;
	
	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	static class ViewHolder {
		private ImageView magazineImageView;
		private TextView magazineTitleTextView;
		private TextView magazineDescriptionTextView;
		private Button seeMoreButton;
	}
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public MagazineAdapter(Activity activity, List<Magazine> magazineList) {
		super();
		mActivity = activity;
		mMagazineList = magazineList;
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mMagazineList.size();
	}

	@Override
	public Magazine getItem(int position) {
		return mMagazineList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Magazine magazine = getItem(position);
		mViewHolder = new ViewHolder();

		if (convertView == null) {
			// Sets layout.
			LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_magazine_list, parent, false);

			// Set views.
			mViewHolder.magazineImageView = (ImageView) convertView.findViewById(R.id.id_magazine_image_view);
			mViewHolder.magazineTitleTextView = (TextView) convertView.findViewById(R.id.id_magazine_title_text_view);
			mViewHolder.magazineDescriptionTextView = (TextView) convertView.findViewById(R.id.id_magazine_description_text_view);
			mViewHolder.seeMoreButton = (Button) convertView.findViewById(R.id.id_magazine_plus_button);
			
			// Saves ViewHolder into the tag.
			convertView.setTag(mViewHolder);
		} else {
			// Gets ViewHolder from the tag.
			mViewHolder = (ViewHolder)convertView.getTag();
		}
		
		// Sets the ListView data.
		setData(magazine);
		
		return convertView;
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Sets the {@link ListView} data.
	 * 
	 * @param magazine
	 */
	public void setData(Magazine magazine) {
		// Button.
		setMoreButton();
		
		// Sets the components.
		setTexts(magazine);
		
		// Creates URL for the image.
		String url = IMAGE_URL + magazine.getPicture();
		setUniversalImage(url);
	}
	
	/**
	 * Sets all the texts.
	 * 
	 * @param magazine
	 */
	public void setTexts(Magazine magazine) {
		mViewHolder.magazineTitleTextView.setText(magazine.getName());
		mViewHolder.magazineDescriptionTextView.setText(magazine.getDescription());
	}
	
	/**
	 * Sets the More {@link Button}.
	 */
	public void setMoreButton() {
		mViewHolder.seeMoreButton.setClickable(true);
		mViewHolder.seeMoreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setData(Uri.parse(MAGAZINE_URL));
				mActivity.startActivity(intent);
			}
		});
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 */
	public void setUniversalImage(String url) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mActivity));
		imageLoader.displayImage(url, mViewHolder.magazineImageView, cache);
	}
}