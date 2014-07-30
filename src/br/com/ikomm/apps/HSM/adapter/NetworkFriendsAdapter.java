package br.com.ikomm.apps.HSM.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Card;

/**
 * NetworkFriendsAdapter.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class NetworkFriendsAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ViewHolder mViewHolder;
	private Activity mActivity;
	private List<Card> mCardList;
	
	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	static class ViewHolder {
		private TextView nameTextView;
	}
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public NetworkFriendsAdapter(Activity activity, List<Card> cardList) {
		super();
		mActivity = activity;
		mCardList = cardList;
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mCardList.size();
	}

	@Override
	public Card getItem(int position) {
		return mCardList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Card currentCard = getItem(position);
		mViewHolder = new ViewHolder();

		if (convertView == null) {
			// Sets layout.
			LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_networking_item, parent, false);

			// Set views.
			mViewHolder.nameTextView = (TextView) convertView.findViewById(R.id.id_friend_name);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder)convertView.getTag();
		}
		
		// Sets the name.
		mViewHolder.nameTextView.setText(currentCard.name);

		return convertView;
	}
}