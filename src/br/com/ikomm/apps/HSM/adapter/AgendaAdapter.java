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
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.utils.DateUtils;

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
	public static final Integer LIMIT = 30;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private LayoutInflater mInflater;
	private List<Agenda> mAgendaList;
	
	private ImageView mLectureStatusImageView;
	private TextView mBeginHourTextView;
	private TextView mEndHourTextView;
	private ImageView mPanelistImageView;
	private TextView mPanelistNameTextView;
	private TextView mLectureTypeTextView;
	
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

		String type = currentAgenda.getType();
		if (!type.equals("speech")) {
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
		mBeginHourTextView = null;
		mEndHourTextView = null;
		mImageView = null;
		mDescriptionTextView = null;
	}
	
	/**
	 * Creates Break layout components.
	 * 
	 * @param view
	 */
	public void createBreakComponents(View view) {
		mBeginHourTextView = (TextView)view.findViewById(R.id.id_start_time_text_view);
		mEndHourTextView = (TextView)view.findViewById(R.id.id_end_time_text_view);
		
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
			mDescriptionTextView.setText(agenda.getLabel());
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
		mBeginHourTextView = null;
		mEndHourTextView = null;
		mPanelistNameTextView = null;
		mLectureTypeTextView = null;
		mPanelistImageView = null;
	}
	
	/**
	 * Creates {@link Panelist} layout components.
	 * 
	 * @param view
	 */
	public void createPanelistComponents(View view) {
		mLectureStatusImageView = (ImageView)view.findViewById(R.id.id_lecture_status_image_view);
		
		mBeginHourTextView = (TextView) view.findViewById(R.id.id_start_time_text_view);
		mEndHourTextView = (TextView) view.findViewById(R.id.id_end_time_text_view);

		mPanelistImageView = (ImageView) view.findViewById(R.id.id_panelist_image_view);
		
		mPanelistNameTextView = (TextView) view.findViewById(R.id.id_panelist_name_text_view);
		mLectureTypeTextView = (TextView) view.findViewById(R.id.id_lecture_type_text_view);
	}

	/**
	 * Populates the {@link Panelist} layout components. 
	 * 
	 * @param agenda
	 */
	public void populatePanelistComponents(Agenda agenda, Integer position) {
		formatDate(agenda);
		
		// Check is is the current Lecture.
		if (DateUtils.isTheCurrentLecture(agenda)) {
			mLectureStatusImageView.setImageResource(R.drawable.ic_hsm_current_event);
		} else {
			mLectureStatusImageView.setImageResource(R.drawable.ic_hsm_clock);
		}
		
		// Lecture name and type.
		if (mPanelistNameTextView != null) {
			mPanelistNameTextView.setText(getPanelistName(agenda));
		}
		if (mLectureTypeTextView != null) {
			mLectureTypeTextView.setText(cutLabelText(agenda.label));
		}
		setUniversalImage(URL + getPanelistUrl(position), mPanelistImageView);
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
		Panelist panelist = QueryHelper.getPanelist(agenda.getPanelistId());
		String name = panelist.getName(); 
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
		Integer panelistId = mAgendaList.get(position).getPanelistId();
		Panelist panelist = QueryHelper.getPanelist(panelistId);
		
		String url = panelist.getPicture();
		return url;
	}
	
	/**
	 * Sets the Break image.
	 * 
	 * @param currentAgenda
	 */
	public void setBreakImage(Agenda currentAgenda) {
		String type = currentAgenda.getType();
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
		String fieldStart[] = agenda.getDateStart().split(" ");
		String startHour = fieldStart[1];
		String startParts[] = startHour.split(":");
		mBeginHourTextView.setText(startParts[0] + ":" + startParts[1]);
		
		String fieldEnd[] = agenda.getDateEnd().split(" ");
		String endHour = fieldEnd[1];
		String endParts[] = endHour.split(":");
		mEndHourTextView.setText(endParts[0] + ":" + endParts[1]);
	}
	
	/**
	 * Trims the text.
	 * 
	 * @param text
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public String cutLabelText(String text) {
		String upper = text.toUpperCase();
		Integer length = upper.length();
		String cuttedText = upper;
		if (length > LIMIT) {
			cuttedText = upper.substring(0, LIMIT) + "...";
		}
		return cuttedText;
	}
	
	//--------------------------------------------------
	// Comparator
	//--------------------------------------------------
	
	public class HoraComparator implements Comparator<Agenda> {
		@Override
		public int compare(Agenda agenda, Agenda agenda2) {
			return agenda.getDateStart().compareTo(agenda2.getDateStart());
		}
	}
}