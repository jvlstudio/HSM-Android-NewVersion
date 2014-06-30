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
import br.ikomm.hsm.adapter.Data1Adapter.HoraComparator;
import br.ikomm.hsm.model.Palestra;
import br.ikomm.hsm.model.PalestraRepository;

public class DataAdapterE1 extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private int id;

	public DataAdapterE1(Activity activity, Context context, int id) {
		super();
		this.context = context;
		this.id = id;
		inflater = LayoutInflater.from(activity);
		List<Palestra> all = new PalestraRepository().getAll();
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
		return 1;
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

		View view = convertView;
		view = inflater.inflate(R.layout.adapter_data1, parent, false);

			if (id == 1){
				TextView horaInicio = (TextView) view
						.findViewById(R.id.tHorarioInicioD1);
				horaInicio.setText("14h00");

				TextView horaFinal = (TextView) view
						.findViewById(R.id.tHorarioFimD1);
				horaFinal.setText("18h00");

				TextView nome = (TextView) view
						.findViewById(R.id.tNomePalestranteD1);
				nome.setText("RICARDO ALMEIDA");

				TextView subtitle = (TextView) view
						.findViewById(R.id.tTipoPalestraD1);
				subtitle.setText("Entendendo o valor...");

				ImageView imagem = (ImageView) view
						.findViewById(R.id.imgPalestranteAgendaD1);
				imagem.setImageResource(R.drawable.p_ricardo_jose_de_almeida_large);
			} 
			
			if (id == 2){
				TextView horaInicio = (TextView) view
						.findViewById(R.id.tHorarioInicioD1);
				horaInicio.setText("09h00");

				TextView horaFinal = (TextView) view
						.findViewById(R.id.tHorarioFimD1);
				horaFinal.setText("17h30");

				TextView nome = (TextView) view
						.findViewById(R.id.tNomePalestranteD1);
				nome.setText("ASWATH DAMORADAN");

				TextView subtitle = (TextView) view
						.findViewById(R.id.tTipoPalestraD1);
				subtitle.setText("2 eventos");

				ImageView imagem = (ImageView) view
						.findViewById(R.id.imgPalestranteAgendaD1);
				imagem.setImageResource(R.drawable.p_aswath_damodaran_large);
			}
		return view;
	}

}
