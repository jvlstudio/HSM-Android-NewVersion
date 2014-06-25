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
import br.ikomm.hsm.model.PalestraBDW;
import br.ikomm.hsm.model.PalestraBDWRepository;

public class BDWData1Adapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<PalestraBDW> palestrasBDW = new ArrayList<PalestraBDW>();

	public BDWData1Adapter(Activity activity, Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(activity);

		List<PalestraBDW> all = new PalestraBDWRepository().getAll();

		palestrasBDW = new ArrayList<PalestraBDW>();

		for (int i = all.size() - 1; i >= 0; i--) {
			if (all.get(i).day.equals("4 de novembro")) {
				palestrasBDW.add(all.get(i));
			}
		}
		Collections.sort(palestrasBDW, new IdComparator());
	}

	public class IdComparator implements Comparator<PalestraBDW> {
		@Override
		public int compare(PalestraBDW o1, PalestraBDW o2) {
			int intO1 = Integer.valueOf(o1.id);
			int intO2 = Integer.valueOf(o2.id);
			return intO1 == intO2 ? 0 : intO1 > intO2 ? 1 : -1;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return palestrasBDW.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (palestrasBDW.get(position) != null
				&& !palestrasBDW.get(position).id.isEmpty()) {
			return Long.valueOf(palestrasBDW.get(position).id);
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;

		PalestraBDW palestraAtual = palestrasBDW.get(position);

		if (palestraAtual.type.equals("break")) {
			view = inflater.inflate(R.layout.adapter_break, parent, false);

			TextView legenda = (TextView) view.findViewById(R.id.tIntervalo);
			legenda.setText(palestraAtual.title);

			TextView horaInicio = (TextView) view
					.findViewById(R.id.tHorarioInicioD1);
			horaInicio.setText(palestraAtual.hour_init);
			
			horaInicio.setTextSize(10);

			TextView horaFinal = (TextView) view
					.findViewById(R.id.tHorarioFimD1);
			horaFinal.setText(palestraAtual.hour_final);
			
			horaFinal.setTextSize(10);

			ImageView imagem = (ImageView) view
					.findViewById(R.id.imgPalestranteAgendaB);

			String uri = "drawable/hsm_agenda_id_happyhour";

			int imageResource = parent
					.getContext()
					.getResources()
					.getIdentifier(uri, null,
							parent.getContext().getPackageName());

			Drawable image = parent.getContext().getResources()
					.getDrawable(imageResource);
			imagem.setImageDrawable(image);
		} else {
			view = inflater.inflate(R.layout.adapter_bdwdata1, parent, false);

			TextView horaInicio = (TextView) view
					.findViewById(R.id.tHorarioInicioD1);
			horaInicio.setText(palestraAtual.hour_init);
			TextView horaFinal = (TextView) view
					.findViewById(R.id.tHorarioFimD1);
			horaFinal.setText(palestraAtual.hour_final);

			TextView nome = (TextView) view
					.findViewById(R.id.tNomePalestranteD1);
			nome.setText(palestraAtual.title);

			TextView sub = (TextView) view.findViewById(R.id.tTipoPalestraD1);
			sub.setText(palestraAtual.subtitle);
		}

		return view;
	}

}
