package br.com.ikomm.apps.HSM.adapter;

import java.util.Collections;
import java.util.Comparator;
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
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.repo.PanelistRepo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * AgendaAdapter.java class.
 * Modified by Rodrigo Cericatto at June 30, 2014.
 */
public class AgendaAdapter extends BaseAdapter {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/panelists/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private LayoutInflater mInflater;
	private List<Agenda> mAgendaList;
	
	private TextView mHoraInicioTextView;
	private TextView mHoraFimTextView;
	private ImageView mPalestranteImageView;
	private TextView mNomePalestranteTextView;
	private TextView mTipoPalestraTextView;
	
	private ImageView mImageView;
	private TextView mDescriptionTextView;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public AgendaAdapter(Activity activity, List<Agenda> listAgenda) {
		super();
		
		mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		mAgendaList = listAgenda;
		
		Collections.sort(mAgendaList, new HoraComparator());
	}
	
	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------

	@Override
	public int getCount() {
		return mAgendaList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		if (mAgendaList.get(position) != null) {
			return Long.valueOf(mAgendaList.get(position).id);
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Agenda currentAgenda = mAgendaList.get(position);

		String type = currentAgenda.type;
		if (!type.equals("session") && !type.equals("speech")) {
			view = mInflater.inflate(R.layout.adapter_break, parent, false);
			initializeBreakComponents(view);
			populateBreakComponents(currentAgenda, position);
			setBreakImage(currentAgenda);
		} else {
			view = mInflater.inflate(R.layout.adapter_agenda, parent, false);
			initializePanelistComponents(view);
			populatePanelistComponents(currentAgenda, position);
		}
		return view;
	}

	//--------------------------------------------------
	// Break Layout Methods
	//--------------------------------------------------
	
	/**
	 * Initializes Break layout components.
	 *  
	 * @param view
	 */
	public void initializeBreakComponents(View view) {
		eraseBreakComponents();
		createBreakComponents(view);
	}
	
	/**
	 * Erases Break layout components.
	 */
	public void eraseBreakComponents() {
		mHoraInicioTextView = null;
		mHoraFimTextView = null;
		mImageView = null;
		mDescriptionTextView = null;
	}
	
	/**
	 * Creates Break layout components.
	 * 
	 * @param view
	 */
	public void createBreakComponents(View view) {
		mHoraInicioTextView = (TextView)view.findViewById(R.id.id_start_time_text_view);
		mHoraFimTextView = (TextView)view.findViewById(R.id.id_end_time_text_view);
		
		mImageView = (ImageView)view.findViewById(R.id.id_image_view);
		
		mDescriptionTextView = (TextView)view.findViewById(R.id.id_description_text_view);
	}

	/**
	 * Populates Break layout components. 
	 * 
	 * @param agenda
	 */
	@SuppressLint("ResourceAsColor")
	public void populateBreakComponents(Agenda agenda, Integer position) {
		formatDate(agenda);
		
		if (mDescriptionTextView != null) {
			mDescriptionTextView.setText(agenda.label);
		}
	}
	
	//--------------------------------------------------
	// Panelist Layout Methods
	//--------------------------------------------------
	
	/**
	 * Initializes  {@link Panelist} layout components.
	 *  
	 * @param view
	 */
	public void initializePanelistComponents(View view) {
		erasePanelistComponents();
		createPanelistComponents(view);
	}
	
	/**
	 * Erases {@link Panelist} layout components.
	 */
	public void erasePanelistComponents() {
		mHoraInicioTextView = null;
		mHoraFimTextView = null;
		mNomePalestranteTextView = null;
		mTipoPalestraTextView = null;
		mPalestranteImageView = null;
	}
	
	/**
	 * Creates {@link Panelist} layout components.
	 * 
	 * @param view
	 */
	public void createPanelistComponents(View view) {
		mHoraInicioTextView = (TextView) view.findViewById(R.id.id_start_time_text_view);
		mHoraFimTextView = (TextView) view.findViewById(R.id.id_end_time_text_view);

		mPalestranteImageView = (ImageView) view.findViewById(R.id.id_panelist_image_view);
		
		mNomePalestranteTextView = (TextView) view.findViewById(R.id.id_panelist_name_text_view);
		mTipoPalestraTextView = (TextView) view.findViewById(R.id.id_lecture_type_text_view);
	}

	/**
	 * Populates the {@link Panelist} layout components. 
	 * 
	 * @param agenda
	 */
	public void populatePanelistComponents(Agenda agenda, Integer position) {
		formatDate(agenda);
		
		if (mNomePalestranteTextView != null) {
			mNomePalestranteTextView.setText(getPanelistName(agenda));
		}
		if (mTipoPalestraTextView != null) {
			mTipoPalestraTextView.setText(agenda.type);
		}
		setUniversalImage(URL + getPanelistUrl(position), mPalestranteImageView);
	}
	
	//--------------------------------------------------
	// Other Methods
	//--------------------------------------------------
	
	/**
	 * Gets the {@link Panelist} name.
	 * 
	 * @param agenda
	 * @return
	 */
	public String getPanelistName(Agenda agenda) {
		PanelistRepo repo = new PanelistRepo(mActivity);
		repo.open();
		Panelist panelist = repo.getPanelist(agenda.panelist_id);
		repo.close();
		String name = panelist.name; 
		return name;
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
	
	/**
	 * Gets the {@link Panelist} picture URL.
	 * 
	 * @param position
	 * @return
	 */
	public String getPanelistUrl(Integer position) {
		Integer panelistId = mAgendaList.get(position).panelist_id;

		PanelistRepo repo = new PanelistRepo(mActivity);
		repo.open();
		Panelist panelist = repo.getPanelist((long)panelistId);
		repo.close();
		
		String url = panelist.picture;
		return url;
	}
	
	/**
	 * Sets the Break image.
	 * 
	 * @param currentAgenda
	 */
	public void setBreakImage(Agenda currentAgenda) {
		String type = currentAgenda.type;
		if (type.contains("lunch")) {
			mImageView.setBackgroundResource(R.drawable.ic_hsm_lunch);
		} else if (type.contains("happyhour")) {
			mImageView.setBackgroundResource(R.drawable.ic_hsm_happyhour);
		} else if (type.contains("credential")) {
			mImageView.setBackgroundResource(R.drawable.ic_hsm_credential);
		} else {
			mImageView.setBackgroundResource(R.drawable.ic_hsm_coffee);
		}
	}
	
	/**
	 * Formats the date.
	 * 
	 * @param agenda
	 */
	public void formatDate(Agenda agenda) {
		String fieldStart[] = agenda.date_start.split(" ");
		String startHour = fieldStart[1];
		String startParts[] = startHour.split(":");
		mHoraInicioTextView.setText(startParts[0] + ":" + startParts[1]);
		
		String fieldEnd[] = agenda.date_end.split(" ");
		String endHour = fieldEnd[1];
		String endParts[] = endHour.split(":");
		mHoraFimTextView.setText(endParts[0] + ":" + endParts[1]);
	}
	
	//--------------------------------------------------
	// Comparator
	//--------------------------------------------------
	
	public class HoraComparator implements Comparator<Agenda> {
		@Override
		public int compare(Agenda agenda, Agenda agenda2) {
			return agenda.date_start.compareTo(agenda2.date_start);
		}
	}
}