package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.repo.PasseRepo;
import br.ikomm.hsm.util.StringUtils;

/**
 * PassesAdapter.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class PassesAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private LayoutInflater mInflater;
	private PasseRepo mPasseRepo;
	private List<Passe> mPasseList = new ArrayList<Passe>();

	private TextView mPrecoNormal;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public PassesAdapter(Activity activity, int id) {
		super();
		
		mInflater = LayoutInflater.from(activity);
		mPasseRepo = new PasseRepo(activity);
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
		View view = convertView;
		view = mInflater.inflate(R.layout.adapter_pacote, parent, false);
		
		Passe item = getItem(position);
		getData(item, view);

		return view;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets all data.
	 * 
	 * @param item
	 * @param view
	 */
	public void getData(Passe item, View view) {
		TextView titulo = (TextView) view.findViewById(R.id.id_passe_title_text_view);
		
		setEventNameField(item, titulo);
		setValidToField(item, view);

		setPriceFromField(item, view);
		setPriceToField(item, view);
		setPassColorField(item, view);
	}
	
	/**
	 * Gets the field 'name'.
	 * 
	 * @param item
	 * @param title
	 */
	public void setEventNameField(Passe item, TextView title) {
		if (!StringUtils.isEmpty(item.name)) {
			if (item.name.length() > 20) {
				title.setText(item.name.subSequence(0, 19));
			} else {
				title.setText(item.name);
			}
		} else {
			title.setText("< Cadastrar T’tulo do Evento >");
		}
	}
	
	/**
	 * Gets the field 'price_from'.
	 * 
	 * @param item
	 * @param view
	 */
	public void setValidToField(Passe item, View view) {
		TextView validade = (TextView)view.findViewById(R.id.id_validity_text_view);
		if (!StringUtils.isEmpty(item.description)) {
			validade.setText(item.description);
		} else {
			validade.setText("< Cadastrar Validade >");
		}
	}
	
	/**
	 * Gets the field 'price_from'.
	 * 
	 * @param item
	 * @param view
	 */
	public void setPriceFromField(Passe item, View view) {
		mPrecoNormal = (TextView) view.findViewById(R.id.id_normal_price_text_view);
		if (!StringUtils.isEmpty(item.price_from)) {
			mPrecoNormal.setText("R$ " + item.price_from);
		} else {
			mPrecoNormal.setText("< Cadastrar Preo Antigo >");
		}
	}
	
	/**
	 * Gets the field 'price_to'.
	 * 
	 * @param item
	 * @param view
	 */
	public void setPriceToField(Passe item, View view) {
		TextView precoApp = (TextView) view.findViewById(R.id.id_new_price_text_view);
		if (!StringUtils.isEmpty(item.price_to)) {
			precoApp.setText("R$ " + item.price_to);
		} else {
			mPrecoNormal.setText("< Cadastrar Preo Novo >");
		}
	}
	
	/**
	 * Gets the field 'color'.
	 * 
	 * @param item
	 * @param view
	 */
	public void setPassColorField(Passe item, View view) {
		LinearLayout layout = (LinearLayout)view.findViewById(R.id.id_linear_layout);
		if (item.color.equals("green")) {
			layout.setBackgroundColor(Color.parseColor("#00a180"));
		}
		if (item.color.equals("gold")){
			layout.setBackgroundColor(Color.parseColor("#dca85c"));
		}
		if (item.color.equals("red")){
			layout.setBackgroundColor(Color.parseColor("#d04840"));
		}
	}
}