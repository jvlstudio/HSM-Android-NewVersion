package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Magazine;
import br.ikomm.hsm.repo.MagazineRepo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * MagazineAdapter.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class MagazineAdapter extends BaseAdapter{

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/magazines/";
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private Activity mActivity;
	private LayoutInflater mInflater;
	private List<Magazine> mMagazineList = new ArrayList<Magazine>();
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public MagazineAdapter(Activity activity) {
		super();
		mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		getMagazineList();
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.adapter_lista_revista, parent, false);
		
		// Initializes the components.
		ImageView magazineImageView = (ImageView)view.findViewById(R.id.id_magazine_image_view);
		TextView magazineTitleTextView = (TextView) view.findViewById(R.id.id_magazine_title_text_view);
		TextView magazineDescriptionTextView = (TextView) view.findViewById(R.id.id_magazine_description_text_view);
		
		// Sets the components.
		Magazine magazine = getItem(position);
		magazineTitleTextView.setText(magazine.name);
		magazineDescriptionTextView.setText(magazine.description);
		
		// Creates URL for the image.
		String url = URL + magazine.picture;
		setUniversalImage(url, magazineImageView);
		
		return view;
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets the {@link Magazine} list.
	 */
	public void getMagazineList() {
		MagazineRepo magazineRepo = new MagazineRepo(mActivity);
		magazineRepo.open();
		mMagazineList = magazineRepo.getAllMagazine();
		magazineRepo.close();
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