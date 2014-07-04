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
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.model.PasseRepo;
import br.ikomm.hsm.util.StringUtils;

/**
 * PacoteAdapter.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class PacoteAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private LayoutInflater mInflater;
	private PasseRepo mPasseRepo;
	private List<Passe> mPasseList = new ArrayList<Passe>();

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public PacoteAdapter(Activity activity, Context context, int id) {
		super();
		mInflater = LayoutInflater.from(activity);
		
		mPasseRepo = new PasseRepo(context);
		mPasseRepo.open();
		mPasseList = mPasseRepo.byEvent(id);
		mPasseRepo.close();
	}
	
	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------

	@Override
	public int getCount() {
		return mPasseList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		Passe passe = mPasseList.get(position); 
		return passe.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		view = mInflater.inflate(R.layout.adapter_pacote, parent, false);
		
		Passe item = mPasseList.get(position);

		TextView titulo = (TextView) view.findViewById(R.id.tTituloPacote);
		// Event name.
		if (!StringUtils.isEmpty(item.event_name)) {
			if (item.event_name.length() > 20) {
				titulo.setText(item.event_name.subSequence(0, 19));
			} else {
				titulo.setText(item.event_name);
				titulo.setText("< Cadastrar T’tulo do Evento >");
			}
		}

		// Valid To.
		TextView validade = (TextView) view.findViewById(R.id.tValidade);
		if (!StringUtils.isEmpty(item.valid_to)) {
			validade.setText(item.valid_to);
			validade.setText("< Cadastrar Validade >");
		}

		TextView locais = (TextView) view.findViewById(R.id.tLocais);
		locais.setText("");

		// Price From.
		TextView precoNormal = (TextView) view.findViewById(R.id.tPrecoNormal);
		if (!StringUtils.isEmpty(item.price_from)) {
			precoNormal.setText("R$ " + item.price_from);
		} else {
			precoNormal.setText("< Cadastrar Preo Antigo >");
		}

		// Price To.
		TextView precoApp = (TextView) view.findViewById(R.id.tValor);
		if (!StringUtils.isEmpty(item.price_to)) {
			precoApp.setText("R$ " + item.price_to);
			precoNormal.setText("< Cadastrar Preo Novo >");
		}
		
		// Pass Color.
		FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.framePasseDescricao);
		if (item.color.equals("green")){
			frameLayout.setBackgroundColor(Color.parseColor("#00a180"));
		}
		if (item.color.equals("gold")){
			frameLayout.setBackgroundColor(Color.parseColor("#dca85c"));
		}
		if (item.color.equals("red")){
			frameLayout.setBackgroundColor(Color.parseColor("#d04840"));
		}
		return view;
	}
}