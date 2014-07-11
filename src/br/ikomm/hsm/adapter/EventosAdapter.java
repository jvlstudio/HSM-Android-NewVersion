package br.ikomm.hsm.adapter;

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
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.tasks.DownloadAsyncTask;
import br.ikomm.hsm.util.AsyncTaskUtils;

/**
 * EventosAdapter.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 7, 2014
 */
public class EventosAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
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
		private LinearLayout mPanelistLinearLayout;
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
			
			viewHolder.mPanelistLinearLayout = (LinearLayout)convertView.findViewById(R.id.id_panelist_layout_eventos_adapter_item);
			viewHolder.mTitleTextView = (TextView)convertView.findViewById(R.id.id_title_eventos_adapter_item);
			viewHolder.mSubtitleTextView = (TextView)convertView.findViewById(R.id.id_subtitle_eventos_adapter_item);
			viewHolder.mDateTextView = (TextView)convertView.findViewById(R.id.id_date_eventos_adapter_item);
			viewHolder.mPlaceTextView = (TextView)convertView.findViewById(R.id.id_address_eventos_adapter_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag(); 
		}
		populatesAdapter(viewHolder, event);
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Populates the item adapter.
	 * 
	 * @param viewHolder
	 * @param event
	 */
	public void populatesAdapter(ViewHolder viewHolder, Event event) {
		// Sets the image URL.
		String imageUrl = URL + event.image_list;
		setLinearLayoutBitmap(viewHolder.mPanelistLinearLayout, imageUrl);
		
		// Sets the text views.
		viewHolder.mTitleTextView.setText(event.name);
		cutSubtitleText(viewHolder, event.name, event.description);
		viewHolder.mDateTextView.setText(formatDates(event.info_dates));
		
		// Cuts the info locale.
		String locale = event.info_locale;
		if (locale.length() > 20) {
			locale = locale.substring(0, 20);
		}
		viewHolder.mPlaceTextView.setText(locale + "...");
		
		// Sets the fonts.
		setFonts(viewHolder);
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
		viewHolder.mSubtitleTextView.setText(subtitle);
	}
	
	/**
	 * Downloads an image.
	 * 
	 * @param layout The layout to be updated.
	 * @param url The image url. 
	 */
	public void setLinearLayoutBitmap(final LinearLayout layout, String url) {
		DownloadAsyncTask task = new DownloadAsyncTask(url) {
			protected void onPostExecute(Bitmap result) {
				BitmapDrawable drawable = new BitmapDrawable(result);
				layout.setBackgroundDrawable(drawable);
			};
		};
		AsyncTaskUtils.execute(task, new String[] {});
	}
	
	/**
	* Sets fonts of all view components.
	*/
	public void setFonts(ViewHolder viewHolder) {
		Typeface caecilia = Typeface.createFromAsset(mContext.getAssets(), "fonts/CaeciliaLTStd-Roman.otf");
		viewHolder.mTitleTextView.setTypeface(caecilia);
		viewHolder.mSubtitleTextView.setTypeface(caecilia);
		viewHolder.mDateTextView.setTypeface(caecilia);
		viewHolder.mPlaceTextView.setTypeface(caecilia);
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