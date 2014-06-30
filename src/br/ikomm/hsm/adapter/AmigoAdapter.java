package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Cartao;
import br.ikomm.hsm.model.CartaoRepositorio;

public class AmigoAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	CartaoRepositorio cartaoRepo;
	List<Cartao> cartoes;

	public AmigoAdapter(Activity activity, Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(activity);
		cartaoRepo = new CartaoRepositorio(context);
		cartoes = cartaoRepo.getMeusContatos();
		if (cartoes == null) {
			cartoes = new ArrayList<Cartao>();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cartoes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Cartao cartaoAtual = cartoes.get(position);

		View view = convertView;
		view = inflater.inflate(R.layout.adapter_amigo, parent, false);

		TextView nome = (TextView) view.findViewById(R.id.lNomeNet);
		nome.setText(cartaoAtual.nome);

		return view;
	}

}
