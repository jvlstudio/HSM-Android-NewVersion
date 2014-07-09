package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.AgendaRepo;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.model.PanelistRepo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * PalestranteAdapter.java class.
 * Modified by Rodrigo Cericatto at July 4, 2014.
 */
public class PalestranteAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/panelists/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private LayoutInflater mInflater;
	
	private List<Panelist> mPanelistList = new ArrayList<Panelist>();
	private PanelistRepo mPanelistRepo;
	private AgendaRepo mAgendaRepo;
	private List<Agenda> mAgendaList = new ArrayList<Agenda>();
	private String mAgendaData = "";
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public PalestranteAdapter(Activity activity, long eventId) {
		super();
		mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		
		mAgendaRepo = new AgendaRepo(activity);
		mAgendaRepo.open();
		mAgendaList = mAgendaRepo.byEvent(eventId);
		
		for (Agenda item : mAgendaList) {
			if (mAgendaData.isEmpty()) {
				mAgendaData = String.valueOf(item.panelist_id);
			} else {
				mAgendaData = mAgendaData + "," + String.valueOf(item.panelist_id);
			}
		}
		mAgendaRepo.close();
		mPanelistRepo = new PanelistRepo(activity);
		mPanelistRepo.open();
		mPanelistList = mPanelistRepo.getAllbyEvent(mAgendaData);
		mPanelistRepo.close();
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.adapter_palestrante, null);
		ImageView picture = (ImageView) view.findViewById(R.id.imagemPalestrante);
		TextView name = (TextView) view.findViewById(R.id.nomePalestrante);
		 
		Panelist panelist = mPanelistList.get(position);
		name.setText(panelist.name);
		
		// Cria a URL para a imagem.
		if (!panelist.picture.isEmpty()) {
			String completeUrl = URL + panelist.picture;
			setUniversalImage(completeUrl, picture);
		}
		return view;
	}
}