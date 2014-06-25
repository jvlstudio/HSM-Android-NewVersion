package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Magazine;
import br.ikomm.hsm.model.MagazineRepo;
import br.ikomm.hsm.model.RevistaRepository;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RevistaAdapter extends BaseAdapter{

	private Activity activity;
	private LayoutInflater inflater;
	private List<RevistaRepository> revistas;
	private MagazineRepo _mr;
	private List<Magazine> magazines = new ArrayList<Magazine>();
	
	
	public RevistaAdapter(Activity activity) {
		super();
		this.activity = activity;
		inflater = LayoutInflater.from(activity);
		
		_mr = new MagazineRepo(activity);
		_mr.open();
		magazines = _mr.getAllMagazine();
		_mr.close();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return magazines.size();
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
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.adapter_lista_revista, parent, false);
		Magazine _magazine = magazines.get(position);
		
		ImageView img = (ImageView) view.findViewById(R.id.imgRevista);
		TextView titulo = (TextView) view.findViewById(R.id.txtTituloRevista);
		TextView descricao = (TextView) view.findViewById(R.id.txtDescricaoRevista);
		
		titulo.setText(_magazine.name);
		descricao.setText(_magazine.description);
		// cria a URL para IMAGEM
		String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/magazines/"+_magazine.picture;
		DisplayImageOptions _cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
	
		imageLoader.displayImage(imageUri, img, _cache);
		
		return view;
	}
}
