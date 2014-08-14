package br.com.ikomm.apps.HSM.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.model.Pass;
import br.com.ikomm.apps.HSM.utils.StringUtils;

/**
 * PassAdapter.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class PassAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ViewHolder mViewHolder;
	private Activity mActivity;
	
	private List<Pass> mPasseList = new ArrayList<Pass>();

	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	static class ViewHolder {
		private TextView title;
		private TextView validity;
		private TextView normalPrice;
		private TextView appPrice;
		private LinearLayout layout;
	}
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public PassAdapter(Activity activity, Integer id) {
		super();
		
		mActivity = activity;
		mPasseList = QueryHelper.getPasseListByEvent(id);
	}
	
	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------

	@Override
	public int getCount() {
		return mPasseList.size();
	}

	@Override
	public Pass getItem(int position) {
		return mPasseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		Pass pass = mPasseList.get(position); 
		return pass.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mViewHolder = new ViewHolder();
		Pass item = getItem(position);
		
		if (convertView == null) {
			// Sets layout.
			LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_package, parent, false);

			// Set views.
			mViewHolder.title = (TextView) convertView.findViewById(R.id.id_passe_title_text_view);
			mViewHolder.validity = (TextView) convertView.findViewById(R.id.id_validity_text_view);
			mViewHolder.normalPrice = (TextView) convertView.findViewById(R.id.id_normal_price_text_view);
			mViewHolder.appPrice = (TextView) convertView.findViewById(R.id.id_new_price_text_view);
			mViewHolder.layout = (LinearLayout) convertView.findViewById(R.id.id_linear_layout);
			
			// Saves ViewHolder into the tag.
			convertView.setTag(mViewHolder);
		} else {
			// Gets ViewHolder from the tag.
			mViewHolder = (ViewHolder)convertView.getTag();
		}
		
		// Gets data.
		getData(item);

		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets all data.
	 * 
	 * @param item
	 */
	public void getData(Pass item) {
		setEventNameField(item);
		setValidToField(item);

		setPriceFromField(item);
		setPriceToField(item);
		setPassColorField(item);
	}
	
	/**
	 * Gets the field 'name'.
	 * 
	 * @param item
	 */
	public void setEventNameField(Pass item) {
		if (!StringUtils.isEmpty(item.getName())) {
			if (item.getName().length() > 20) {
				mViewHolder.title.setText(item.getName().subSequence(0, 19));
			} else {
				mViewHolder.title.setText(item.getName());
			}
		} else {
			mViewHolder.title.setText(mActivity.getString(R.string.pass_adapter_title_empty));
		}
	}
	
	/**
	 * Gets the field 'price_from'.
	 * 
	 * @param item
	 */
	public void setValidToField(Pass item) {
		if (!StringUtils.isEmpty(item.getDescription())) {
			mViewHolder.validity.setText(item.getDescription());
		} else {
			mViewHolder.validity.setText(mActivity.getString(R.string.pass_adapter_validity_empty));
		}
	}
	
	/**
	 * Gets the field 'price_from'.
	 * 
	 * @param item
	 */
	public void setPriceFromField(Pass item) {
		if (!StringUtils.isEmpty(item.getPriceFrom())) {
			mViewHolder.normalPrice.setText("R$ " + item.getPriceFrom());
		} else {
			mViewHolder.normalPrice.setText(mActivity.getString(R.string.pass_adapter_normal_price_empty));
		}
	}
	
	/**
	 * Gets the field 'price_to'.
	 * 
	 * @param item
	 */
	public void setPriceToField(Pass item) {
		if (!StringUtils.isEmpty(item.price_to)) {
			mViewHolder.appPrice.setText("R$ " + item.getPriceTo());
		} else {
			mViewHolder.normalPrice.setText(mActivity.getString(R.string.pass_adapter_new_price_empty));
		}
	}
	
	/**
	 * Gets the field 'color'.
	 * 
	 * @param item
	 */
	public void setPassColorField(Pass item) {
		if (item.getColor().equals("green")) {
			mViewHolder.layout.setBackgroundColor(Color.parseColor("#00A180"));
		}
		if (item.getColor().equals("gold")) {
			mViewHolder.layout.setBackgroundColor(Color.parseColor("#DCA85C"));
		}
		if (item.getColor().equals("red")) {
			mViewHolder.layout.setBackgroundColor(Color.parseColor("#D04840"));
		}
	}
}