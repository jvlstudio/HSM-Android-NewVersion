package br.ikomm.hsm.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Event;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * EventosAdapter.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 7, 2014
 */
public class EventosAdapter extends BaseAdapter {

	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/events/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Context mContext;
	private List<Event> mList;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public EventosAdapter(Context context, List<Event> list) {
		mContext = context;
		mList = list;
	}
	
	//--------------------------------------------------
	// ViewHolder
	//--------------------------------------------------
	
	static class ViewHolder {
		private ImageView mPanelistImageView;
		private TextView mTitleTextView;
		private TextView mSubtitleTextView;
		private TextView mDateTextView;
		private TextView mPlaceTextView;
	}
	
	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mList.size();
	}
	
	@Override
	public Event getItem(int position) {
		Event event = mList.get(position);
		return event;
	}
	
	@Override
	public long getItemId(int position) {
		return mList.get(position).id;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Gets the Unity of the current position.
		Event event = (Event) getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.eventos_adapter_item, null);
			viewHolder = new ViewHolder();
			
			viewHolder.mPanelistImageView = (ImageView)convertView.findViewById(R.id.id_panelist_image_eventos_adapter_item);
			viewHolder.mTitleTextView = (TextView)convertView.findViewById(R.id.id_title_eventos_adapter_item);
			viewHolder.mSubtitleTextView = (TextView)convertView.findViewById(R.id.id_subtitle_eventos_adapter_item);
			viewHolder.mDateTextView = (TextView)convertView.findViewById(R.id.id_date_eventos_adapter_item);
			viewHolder.mPlaceTextView = (TextView)convertView.findViewById(R.id.id_address_eventos_adapter_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag(); 
		}
		
		// Populates adapter.
		String imageUrl = URL + event.image_list;
		setUniversalImage(imageUrl, viewHolder.mPanelistImageView);
		viewHolder.mTitleTextView.setText(event.name);
		viewHolder.mSubtitleTextView.setText(event.description);
		viewHolder.mDateTextView.setText(formatDates(event.info_dates));
		viewHolder.mPlaceTextView.setText(event.info_locale);
		
		return convertView;
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
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		imageLoader.displayImage(url, imageView, cache);
	}
	
	/**
	 * Formats the date.
	 * 
	 * @param text
	 * @return
	 */
	public String formatDates(String text) {
		String dates[] = text.replace("|", "-").split("-");
		Integer length = dates.length;
		String days[] = new String[length];
		String months[] = new String[length];
		
		// Get days and months from each date.
		for (int i = 0; i < length; i++) {
			String trimmed = dates[i].trim();
			String parts[] = trimmed.split("/");
			months[i] = parts[1];
			days[i] = parts[0];
		}
		
		// Apply the logic.
		String formattedDate = "";
		
		if (length == 1) {
			formattedDate = days[0] + " " + getMonth(Integer.valueOf(months[0]));
		} else {
			if (length > 1) {
				formattedDate = days[0] + " e " + days[1] + " " + getMonth(Integer.valueOf(months[0]));
			} else {
				formattedDate = days[0] + " a " + days[length - 1] + " " + getMonth(Integer.valueOf(months[0]));
			}
		}
		
		return formattedDate;
	}
	
	/**
	 * Get the month in words.
	 * 
	 * @param value
	 * @return
	 */
	public String getMonth(Integer value) {
		String month = "";
		switch (value) {
			case 1:
				month = "jan"; 
				break;
			case 2:
				month = "fev"; 
				break;
			case 3:
				month = "mar"; 
				break;
			case 4:
				month = "abr"; 
				break;
			case 5:
				month = "mai"; 
				break;
			case 6:
				month = "jun"; 
				break;
			case 7:
				month = "jul"; 
				break;
			case 8:
				month = "ago"; 
				break;
			case 9:
				month = "set"; 
				break;
			case 10:
				month = "out"; 
				break;
			case 11:
				month = "nov"; 
				break;
			case 12:
				month = "dez"; 
				break;
		}
		return month;
	}
}