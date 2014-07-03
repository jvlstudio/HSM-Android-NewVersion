package br.ikomm.hsm.adapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.model.PanelistRepo;

/**
 * Data1Adapter.java class.
 * Modified by Rodrigo Cericatto at June 30, 2014.
 */
public class Data1Adapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private LayoutInflater mInflater;
	private List<Agenda> mAgendaList;
	
	private TextView mHoraInicioTextView;
	private TextView mHoraFimTextView;
	private TextView mNomePalestranteTextView;
	private TextView mTipoPalestraTextView;
	private ImageView mPalestranteImageView;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public Data1Adapter(Activity activity, List<Agenda> listAgenda) {
		super();
		
		mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		mAgendaList = listAgenda;
		
		Collections.sort(mAgendaList, new HoraComparator());
	}
	
	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------

	@Override
	public int getCount() {
		return mAgendaList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		if (mAgendaList.get(position) != null) {
			return Long.valueOf(mAgendaList.get(position).id);
		}
		return 0;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Agenda currentAgenda = mAgendaList.get(position);

		if (currentAgenda.panelist_id == 0) {
			view = mInflater.inflate(R.layout.adapter_break, parent, false);
			initializeComponents(view);
			
			populateComponents(currentAgenda);
			if (currentAgenda.type.contains("coffe-break") || currentAgenda.type.contains("coffe break")) {
				mPalestranteImageView.setBackgroundResource(R.drawable.hsm_agenda_id_coffee);
			} else if (currentAgenda.type.contains("almoço")) {
				mPalestranteImageView.setBackgroundResource(R.drawable.hsm_agenda_id_lunch);
			} else if (currentAgenda.type.contains("happy-hour")) {
				mPalestranteImageView.setBackgroundResource(R.drawable.hsm_agenda_id_happyhour);
			} else if (currentAgenda.type.contains("credenciamento") || currentAgenda.type.contains("abertura")) {
				mPalestranteImageView.setBackgroundResource(R.drawable.hsm_agenda_id_credential);
			}
		} else {
			view = mInflater.inflate(R.layout.adapter_data1, parent, false);
			initializeComponents(view);
			populateComponents(currentAgenda);
		}
		return view;
	}

	//--------------------------------------------------
	// Layout Methods
	//--------------------------------------------------
	
	/**
	 * Initializes layout components. 
	 * @param view
	 * 
	 */
	public void initializeComponents(View view) {
		eraseComponents();
		createComponents(view);
	}
	
	/**
	 * Erase layout components.
	 */
	public void eraseComponents() {
		mHoraInicioTextView = null;
		mHoraFimTextView = null;
		mNomePalestranteTextView = null;
		mTipoPalestraTextView = null;
		mPalestranteImageView = null;
	}
	
	/**
	 * Creates layout components.
	 * 
	 * @param view
	 */
	public void createComponents(View view) {
		mHoraInicioTextView = (TextView) view.findViewById(R.id.tHorarioInicioD1);
		mHoraFimTextView = (TextView) view.findViewById(R.id.tHorarioFimD1);
		
		mNomePalestranteTextView = (TextView) view.findViewById(R.id.tNomePalestranteD1);
		mTipoPalestraTextView = (TextView) view.findViewById(R.id.tTipoPalestraD1);
		
		mPalestranteImageView = (ImageView) view.findViewById(R.id.imgPalestranteAgendaD1);
	}

	/**
	 * Populates layout components. 
	 * 
	 * @param agenda
	 */
	public void populateComponents(Agenda agenda) {
		String start[] = agenda.date_start.split(" ");
		mHoraInicioTextView.setText(start[1]);
		String end[] = agenda.date_end.split(" ");
		mHoraFimTextView.setText(end[1]);
		
		if (mNomePalestranteTextView != null) {
			mNomePalestranteTextView.setText(getPanelistName(agenda));
		}
		if (mTipoPalestraTextView != null) {
			mTipoPalestraTextView.setText(agenda.type);
		}
		Bitmap bitmap = null;
		if (mPalestranteImageView != null) {
			mPalestranteImageView.setImageBitmap(bitmap);
		}
	}
	
	/**
	 * Gets the {@link Panelist} name.
	 * 
	 * @param agenda
	 * @return
	 */
	public String getPanelistName(Agenda agenda) {
		PanelistRepo repo = new PanelistRepo(mActivity);
		repo.open();
		Panelist panelist = repo.getPanelist(agenda.panelist_id);
		repo.close();
		String name = panelist.name; 
		return name;
	}
	
	//--------------------------------------------------
	// Comparator
	//--------------------------------------------------
	
	public class HoraComparator implements Comparator<Agenda> {
		@Override
		public int compare(Agenda o1, Agenda o2) {
			return o1.date_start.compareTo(o2.date_start);
		}
	}
}