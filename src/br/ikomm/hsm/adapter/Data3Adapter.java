package br.ikomm.hsm.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Palestra;
import br.ikomm.hsm.model.PalestraRepository;

public class Data3Adapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Palestra> palestras;

	public Data3Adapter(Activity activity, Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(activity);
		List<Palestra> all = new PalestraRepository().getAll();

		palestras = new ArrayList<Palestra>();

		for (int i = all.size() - 1; i >= 0; i--) {
			if (all.get(i).day.equals("6 de novembro")) {
				palestras.add(all.get(i));
			}
		}
		Collections.sort(palestras, new HoraComparator());
	}

	public class HoraComparator implements Comparator<Palestra> {
		@Override
		public int compare(Palestra o1, Palestra o2) {
			return o1.hour_init.compareTo(o2.hour_init);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return palestras.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (palestras.get(position) != null
				&& !palestras.get(position).id.isEmpty()) {
			return Long.valueOf(palestras.get(position).id);
		}
		return 0;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		Palestra palestraAtual = palestras.get(position);

		if (palestraAtual.id == null || palestraAtual.id.isEmpty()){
			view = inflater.inflate(R.layout.adapter_break, parent, false);
			
			TextView legenda = (TextView) view.findViewById(R.id.tIntervalo);
			legenda.setText(palestraAtual.title);
			
			TextView horaInicio = (TextView) view.findViewById(R.id.tHorarioInicioD1);
			horaInicio.setText(palestraAtual.hour_init);
	
			TextView horaFinal = (TextView) view.findViewById(R.id.tHorarioFimD1);
			horaFinal.setText(palestraAtual.hour_final);
			
			ImageView imagem = (ImageView) view
					.findViewById(R.id.imgPalestranteAgendaB);
			
			if(palestraAtual.title.contains("coffe-break") || palestraAtual.title.contains("coffe break")) {
				String uri = "drawable/hsm_agenda_id_coffee";
				
				int imageResource = parent
						.getContext()
						.getResources()
						.getIdentifier(uri, null,
								parent.getContext().getPackageName());
	
				Drawable image = parent.getContext().getResources()
						.getDrawable(imageResource);
				imagem.setImageDrawable(image);
			} else if (palestraAtual.title.contains("almoço")){
				String uri = "drawable/hsm_agenda_id_lunch";
				
				int imageResource = parent
						.getContext()
						.getResources()
						.getIdentifier(uri, null,
								parent.getContext().getPackageName());
	
				Drawable image = parent.getContext().getResources()
						.getDrawable(imageResource);
				imagem.setImageDrawable(image);
			} else if (palestraAtual.title.contains("happy-hour")){
				String uri = "drawable/hsm_agenda_id_happyhour";
				
				int imageResource = parent
						.getContext()
						.getResources()
						.getIdentifier(uri, null,
								parent.getContext().getPackageName());
	
				Drawable image = parent.getContext().getResources()
						.getDrawable(imageResource);
				imagem.setImageDrawable(image);
			} else if (palestraAtual.title.contains("credenciamento") || palestraAtual.title.contains("abertura")){
				String uri = "drawable/hsm_agenda_id_credential";
				
				int imageResource = parent
						.getContext()
						.getResources()
						.getIdentifier(uri, null,
								parent.getContext().getPackageName());
	
				Drawable image = parent.getContext().getResources()
						.getDrawable(imageResource);
				imagem.setImageDrawable(image);
			}
			
		} else {
			
			view = inflater.inflate(R.layout.adapter_data3, parent, false);

			TextView horaInicio = (TextView) view
					.findViewById(R.id.tHorarioInicioD3);
			horaInicio.setText(palestraAtual.hour_init);
	
			TextView horaFinal = (TextView) view.findViewById(R.id.tHorarioFimD3);
			horaFinal.setText(palestraAtual.hour_final);
	
			TextView nome = (TextView) view
					.findViewById(R.id.tNomePalestranteD3);
			nome.setText(palestraAtual.author);
			
			TextView subtitle = (TextView) view.findViewById(R.id.tTipoPalestraD3);
			subtitle.setText(palestraAtual.subtitle);
	
			ImageView imagem = (ImageView) view
					.findViewById(R.id.imgPalestranteAgendaD3);
	
			if (palestraAtual.slug != null && !palestraAtual.slug.isEmpty()) {
				String uri = "drawable/" + palestraAtual.slug;
	
				int imageResource = parent
						.getContext()
						.getResources()
						.getIdentifier(uri, null,
								parent.getContext().getPackageName());
	
				Drawable image = parent.getContext().getResources()
						.getDrawable(imageResource);
				imagem.setImageDrawable(image);
			} else {
				imagem.setImageResource(android.R.color.transparent);
			}
		}
		return view;
	}

}
