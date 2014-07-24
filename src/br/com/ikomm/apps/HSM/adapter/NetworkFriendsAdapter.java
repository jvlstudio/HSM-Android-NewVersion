package br.com.ikomm.apps.HSM.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Card;
import br.com.ikomm.apps.HSM.repo.CardRepository;

/**
 * NetworkFriendsAdapter.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class NetworkFriendsAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private LayoutInflater mInflater;
	private CardRepository mCartaoRepo;
	private List<Card> mCartaoList;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public NetworkFriendsAdapter(Activity activity) {
		super();
		mInflater = LayoutInflater.from(activity);
		mCartaoRepo = new CardRepository(activity);
		mCartaoList = mCartaoRepo.getMeusContatos();
		if (mCartaoList == null) {
			mCartaoList = new ArrayList<Card>();
		}
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mCartaoList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Card cartaoAtual = mCartaoList.get(position);

		View view = convertView;
		view = mInflater.inflate(R.layout.adapter_network_friends, parent, false);

		TextView nome = (TextView) view.findViewById(R.id.lNomeNet);
		nome.setText(cartaoAtual.nome);

		return view;
	}
}