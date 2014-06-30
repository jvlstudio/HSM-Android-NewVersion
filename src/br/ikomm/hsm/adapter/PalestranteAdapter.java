package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.AgendaRepo;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.model.PanelistRepo;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PalestranteAdapter extends BaseAdapter{

	private Activity activity;
	private LayoutInflater inflater;
	private List<Panelist> panelists = new ArrayList<Panelist>();
	private PanelistRepo _pr;
	private AgendaRepo _ar;
	private List<Agenda> agendas = new ArrayList<Agenda>();
	private String listAgenda = "";
	
	public PalestranteAdapter(Activity activity, long event_id) {
		super();
		this.activity = activity;
		this.inflater = LayoutInflater.from(activity);
		
		_ar = new AgendaRepo(activity);
		_ar.open();
		agendas = _ar.byEvent(event_id);
		
		for (Agenda item : agendas) {
			if (listAgenda.isEmpty()) {
				listAgenda = String.valueOf(item.panelist_id);
			} else {
				listAgenda = listAgenda + "," + String.valueOf(item.panelist_id);
			}
		}
		_ar.close();
		
		_pr = new PanelistRepo(activity);
		_pr.open();
		panelists = _pr.getAllbyEvent(listAgenda);
		_pr.close();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return panelists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		Panelist _item = panelists.get(position);
		return _item.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.adapter_palestrante, null);
		ImageView picture = (ImageView) view.findViewById(R.id.imagemPalestrante);
		TextView name = (TextView) view.findViewById(R.id.nomePalestrante);
		 
		Panelist _panelist = panelists.get(position);
		
		name.setText(_panelist.name);
		// cria a URL para IMAGEM
		if(!_panelist.picture.isEmpty()){
			String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/panelist/"+_panelist.picture;
			DisplayImageOptions _cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
			
			imageLoader.displayImage(imageUri, picture, _cache);
		}
		
		return view;
	}
}
