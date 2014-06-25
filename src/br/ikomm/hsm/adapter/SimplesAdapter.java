package br.ikomm.hsm.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.AuditorioSimples;
import br.ikomm.hsm.model.AuditorioSimplesRepository;
import br.ikomm.hsm.model.AuditorioTematico;
import br.ikomm.hsm.model.AuditorioTematicoRepository;

public class SimplesAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<AuditorioSimples> auditoriosSimples;
	private List<AuditorioTematico> auditoriosTematicos;
	private AuditorioSimplesRepository auditorioSimplesRepo;
	private AuditorioTematicoRepository auditorioTematicoRepo;
	private boolean auditoriosTipo;

	// boolean auditoriosTipo = true (auditorios simples)
	// boolean auditoriosTipo = false (estacaoConhecimento/auditorios tematicos)
	public SimplesAdapter(Activity activity, Context context,
			boolean auditoriosTipo) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(activity);
		this.auditoriosTipo = auditoriosTipo;
		if (auditoriosTipo) {
			auditorioSimplesRepo = new AuditorioSimplesRepository();
			auditoriosSimples = auditorioSimplesRepo.getAll();
		} else {
			auditorioTematicoRepo = new AuditorioTematicoRepository();
			auditoriosTematicos = auditorioTematicoRepo.getAll();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (this.auditoriosTipo)
			return auditoriosSimples.size();
		else
			return auditoriosTematicos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		if (this.auditoriosTipo) {
			if (auditoriosSimples.get(position) != null
					&& !auditoriosSimples.get(position).id.isEmpty()) {
				return Long.valueOf(auditoriosSimples.get(position).id);
			}
			return 0;

		} else {
			if (auditoriosTematicos.get(position) != null
					&& !auditoriosTematicos.get(position).id.isEmpty()) {
				return Long.valueOf(auditoriosTematicos.get(position).id);
			}
			return 0;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		view = inflater.inflate(R.layout.adapter_simples, parent, false);

		if (this.auditoriosTipo) {
			AuditorioSimples auditorioSimples = auditoriosSimples.get(position);

			TextView tituloEvento = (TextView) view
					.findViewById(R.id.tTituloEvento);
			tituloEvento.setText(auditorioSimples.titulo);

			TextView nomeAuditorio = (TextView) view
					.findViewById(R.id.tNomeAuditorio);
			nomeAuditorio.setText(auditorioSimples.auditorio);

		} else {
			AuditorioTematico auditorioTematico = auditoriosTematicos
					.get(position);

			TextView tituloEvento = (TextView) view
					.findViewById(R.id.tTituloEvento);
			tituloEvento.setText(auditorioTematico.titulo);

			TextView nomeAuditorio = (TextView) view
					.findViewById(R.id.tNomeAuditorio);
			nomeAuditorio.setText(auditorioTematico.textoBranco);
		}
		return view;
	}
}
