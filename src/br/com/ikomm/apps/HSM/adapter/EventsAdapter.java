package br.com.ikomm.apps.HSM.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.utils.FileBitmapUtils;
import br.com.ikomm.apps.HSM.utils.StringUtils;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * EventsAdapter.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 7, 2014
 */
public class EventsAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/events/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ViewHolder mViewHolder;
	private Context mContext;
	
	private List<Event> mList;
	private FileBitmapUtils mFileManager = new FileBitmapUtils();
	private String mPath;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public EventsAdapter(Context context, List<Event> list, String path) {
		mContext = context;
		mList = list;
		mPath = path;
	}
	
	//--------------------------------------------------
	// ViewHolder
	//--------------------------------------------------
	
	static class ViewHolder {
		private LinearLayout panelistLinearLayout;
		private TextView titleTextView;
		private TextView subtitleTextView;
		private TextView dateTextView;
		private TextView placeTextView;
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
		mViewHolder = new ViewHolder();
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_event_item, parent, false);
			
			mViewHolder.panelistLinearLayout = (LinearLayout)convertView.findViewById(R.id.id_panelist_layout_events_adapter_item);
			mViewHolder.titleTextView = (TextView)convertView.findViewById(R.id.id_title_events_adapter_item);
			mViewHolder.subtitleTextView = (TextView)convertView.findViewById(R.id.id_subtitle_events_adapter_item);
			mViewHolder.dateTextView = (TextView)convertView.findViewById(R.id.id_date_events_adapter_item);
			mViewHolder.placeTextView = (TextView)convertView.findViewById(R.id.id_address_events_adapter_item);
			
			// Saves ViewHolder into the tag.
			convertView.setTag(mViewHolder);
		} else {
			// Gets ViewHolder from the tag.
			mViewHolder = (ViewHolder)convertView.getTag();
		}
		Utils.fileLog("EventsAdapter.getView() -> Calling populatesAdapter() for position " + position + ".");
		populatesAdapter(event, position);
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Populates the item adapter.
	 * 
	 * @param event
	 * @param position
	 */
	public void populatesAdapter(Event event, Integer position) {
		// Sets the image URL.
		String path = mPath + event.image_list;
		setLinearLayoutBitmap(path, position);
		
		// Sets the text views.
		mViewHolder.titleTextView.setText(event.name);
		cutSubtitleText(mViewHolder, event.name, event.description);
		mViewHolder.dateTextView.setText(formatDates(event.info_dates));
		
		// Cuts the info locale.
		String locale = event.info_locale;
		if (locale.length() > 20) {
			locale = locale.substring(0, 20);
		}
		mViewHolder.placeTextView.setText(locale + "...");
		
		// Sets the fonts.
		setFonts();
	}
	
	/**
	 * Downloads an image.
	 * 
	 * @param path The image path into the disk
	 * @param position 
	 */
	public void setLinearLayoutBitmap(final String path, final Integer position) {
		/*
		ReadImageAsyncTask task = new ReadImageAsyncTask(mFileManager, path, position) {
			@SuppressWarnings("deprecation")
			protected void onPostExecute(Bitmap bitmap) {
				Utils.fileLog("EventsAdapter.setLinearLayoutBitmap() -> Into onPostExecute(), setting the Bitmap for position " + position + " and file " + path + ".");
				BitmapDrawable drawable = new BitmapDrawable(bitmap);
				mViewHolder.panelistLinearLayout.setBackgroundDrawable(drawable);
			};
		};
		AsyncTaskUtils.execute(task, new String[] {});
		*/
//		Bitmap current = ContentManager.getInstance().getCachedBitmapList().get(position);
		
		if (mViewHolder.panelistLinearLayout.getDrawingCache() == null) {
			Utils.fileLog("EventsAdapter.setLinearLayoutBitmap() -> LinearLayout without bitmap! Getting Bitmap from the id '" + path + "'.");
			Bitmap current = ContentManager.getInstance().getCachedBitmap(path);
			Utils.fileLog("EventsAdapter.setLinearLayoutBitmap() -> Bitmap is null? " + ((current == null) ? "Sim" : "N‹o") + ".");
			BitmapDrawable drawable = new BitmapDrawable(current);
			mViewHolder.panelistLinearLayout.setBackgroundDrawable(drawable);
		} else {
			Utils.fileLog("EventsAdapter.setLinearLayoutBitmap() -> LinearLayout with bitmap.");
		}
	}
	
	/**
	 * Cuts the subtitle text.
	 * 
	 * @param viewHolder
	 * @param title
	 * @param subtitle
	 */
	public void cutSubtitleText(ViewHolder viewHolder, String title, String subtitle) {
		Integer titleSize = title.length();
		Integer subtitleSize = subtitle.length();
		String cuttedText = subtitle;
		
		// Cuts the subtitle.
		if (titleSize < 20) {
			if (subtitleSize > 40) {
				cuttedText = subtitle.substring(0, 40);
			}
		} else if (titleSize > 20 && titleSize < 30) {
			if (subtitleSize > 30) {
				cuttedText = subtitle.substring(0, 30);
			}
		} else if (titleSize > 30 && titleSize < 40) {
			if (subtitleSize > 30) {
				cuttedText = subtitle.substring(0, 25);
			}
		} else if (titleSize > 40) {
			if (subtitleSize > 20) {
				cuttedText = subtitle.substring(0, 15);
			}
		}
		subtitle = cuttedText + "...";
		viewHolder.subtitleTextView.setText(subtitle);
	}
	
	/**
	* Sets fonts of all view components.
	*/
	public void setFonts() {
		Typeface caecilia = Typeface.createFromAsset(mContext.getAssets(), "fonts/CaeciliaLTStd-Roman.otf");
		mViewHolder.titleTextView.setTypeface(caecilia);
		mViewHolder.subtitleTextView.setTypeface(caecilia);
		mViewHolder.dateTextView.setTypeface(caecilia);
		mViewHolder.placeTextView.setTypeface(caecilia);
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
			if (!StringUtils.isEmpty(trimmed)) {
				String parts[] = trimmed.split("/");
				months[i] = parts[1];
				days[i] = parts[0];
			}
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