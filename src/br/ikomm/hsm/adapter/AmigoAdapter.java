package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.repo.CartaoRepository;

/**
 * AmigoAdapter.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class AmigoAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private LayoutInflater mInflater;
	private CartaoRepository mCartaoRepo;
	private List<Cartao> mCartaoList;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public AmigoAdapter(Activity activity) {
		super();
		mInflater = LayoutInflater.from(activity);
		mCartaoRepo = new CartaoRepository(activity);
		mCartaoList = mCartaoRepo.getMeusContatos();
		if (mCartaoList == null) {
			mCartaoList = new ArrayList<Cartao>();
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
		Cartao cartaoAtual = mCartaoList.get(position);

		View view = convertView;
		view = mInflater.inflate(R.layout.adapter_amigo, parent, false);

		TextView nome = (TextView) view.findViewById(R.id.lNomeNet);
		nome.setText(cartaoAtual.nome);

		return view;
	}
}