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
import br.com.ikomm.apps.HSM.model.Passe;
import br.com.ikomm.apps.HSM.utils.StringUtils;

/**
 * PassesAdapter.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class PassesAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ViewHolder mViewHolder;
	private Activity mActivity;
	
	private List<Passe> mPasseList = new ArrayList<Passe>();

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
	
	public PassesAdapter(Activity activity, Integer id) {
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
	public Passe getItem(int position) {
		return mPasseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		Passe passe = mPasseList.get(position); 
		return passe.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mViewHolder = new ViewHolder();
		Passe item = getItem(position);
		
		if (convertView == null) {
			// Sets layout.
			LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_pacote, parent, false);

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
	public void getData(Passe item) {
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
	public void setEventNameField(Passe item) {
		if (!StringUtils.isEmpty(item.name)) {
			if (item.name.length() > 20) {
				mViewHolder.title.setText(item.name.subSequence(0, 19));
			} else {
				mViewHolder.title.setText(item.name);
			}
		} else {
			mViewHolder.title.setText("< Cadastrar T’tulo do Evento >");
		}
	}
	
	/**
	 * Gets the field 'price_from'.
	 * 
	 * @param item
	 */
	public void setValidToField(Passe item) {
		if (!StringUtils.isEmpty(item.description)) {
			mViewHolder.validity.setText(item.description);
		} else {
			mViewHolder.validity.setText("< Cadastrar Validade >");
		}
	}
	
	/**
	 * Gets the field 'price_from'.
	 * 
	 * @param item
	 */
	public void setPriceFromField(Passe item) {
		if (!StringUtils.isEmpty(item.price_from)) {
			mViewHolder.normalPrice.setText("R$ " + item.price_from);
		} else {
			mViewHolder.normalPrice.setText("< Cadastrar Preo Antigo >");
		}
	}
	
	/**
	 * Gets the field 'price_to'.
	 * 
	 * @param item
	 */
	public void setPriceToField(Passe item) {
		if (!StringUtils.isEmpty(item.price_to)) {
			mViewHolder.appPrice.setText("R$ " + item.price_to);
		} else {
			mViewHolder.normalPrice.setText("< Cadastrar Preo Novo >");
		}
	}
	
	/**
	 * Gets the field 'color'.
	 * 
	 * @param item
	 */
	public void setPassColorField(Passe item) {
		if (item.color.equals("green")) {
			mViewHolder.layout.setBackgroundColor(Color.parseColor("#00A180"));
		}
		if (item.color.equals("gold")) {
			mViewHolder.layout.setBackgroundColor(Color.parseColor("#DCA85C"));
		}
		if (item.color.equals("red")) {
			mViewHolder.layout.setBackgroundColor(Color.parseColor("#D04840"));
		}
	}
}