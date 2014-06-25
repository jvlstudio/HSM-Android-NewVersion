package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Pacote;
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.model.PasseRepo;

public class PacoteAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	PasseRepo _pr;
	List<Passe> passes = new ArrayList<Passe>();

	public PacoteAdapter(Activity activity, Context context, int id) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(activity);
		
		_pr = new PasseRepo(context);
		_pr.open();
		passes = _pr.byEvent(id);
		_pr.close();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return passes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		Passe _p = passes.get(position); 
		return _p.id;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View view = converView;
		view = inflater.inflate(R.layout.adapter_pacote, parent, false);
		
		Passe _item = passes.get(position);

		TextView titulo = (TextView) view.findViewById(R.id.tTituloPacote);
		if (_item.event_name.length() > 20) {
			titulo.setText(_item.event_name.subSequence(0, 19));
		} else {
			titulo.setText(_item.event_name);
		}
		

		TextView validade = (TextView) view.findViewById(R.id.tValidade);
		validade.setText(_item.valid_to);

		TextView locais = (TextView) view.findViewById(R.id.tLocais);
		locais.setText("");

		TextView precoNormal = (TextView) view.findViewById(R.id.tPrecoNormal);
		precoNormal.setText("R$ " + _item.price_from);

		TextView precoApp = (TextView) view.findViewById(R.id.tValor);
		precoApp.setText("R$ " + _item.price_to);
		
		FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.framePasseDescricao);
		
		if (_item.color.equals("green")){
			frameLayout.setBackgroundColor(Color.parseColor("#00a180"));
		}
		if (_item.color.equals("gold")){
			frameLayout.setBackgroundColor(Color.parseColor("#dca85c"));
		}
		if (_item.color.equals("red")){
			frameLayout.setBackgroundColor(Color.parseColor("#d04840"));
		}
		
		return view;
	}

}
