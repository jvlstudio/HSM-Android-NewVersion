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
 * RevistaAdapter.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class RevistaAdapter extends BaseAdapter{

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	private Activity mActivity;
	private LayoutInflater mInflater;
	private MagazineRepo mMagazineRepo;
	private List<Magazine> mMagazineList = new ArrayList<Magazine>();
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public RevistaAdapter(Activity activity) {
		super();
		mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		
		mMagazineRepo = new MagazineRepo(activity);
		mMagazineRepo.open();
		mMagazineList = mMagazineRepo.getAllMagazine();
		mMagazineRepo.close();
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mMagazineList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.adapter_lista_revista, parent, false);
		Magazine magazine = mMagazineList.get(position);
		
		ImageView img = (ImageView) view.findViewById(R.id.imgRevista);
		TextView titulo = (TextView) view.findViewById(R.id.txtTituloRevista);
		TextView descricao = (TextView) view.findViewById(R.id.txtDescricaoRevista);
		
		titulo.setText(magazine.name);
		descricao.setText(magazine.description);
		
		// Creates URL for the image.
		String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/mMagazineList/" + magazine.picture;
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mActivity));
		imageLoader.displayImage(imageUri, img, cache);
		
		return view;
	}
}