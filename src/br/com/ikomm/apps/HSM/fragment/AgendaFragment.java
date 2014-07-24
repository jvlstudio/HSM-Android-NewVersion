package br.com.ikomm.apps.HSM.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.activity.LectureDetailsActivity;
import br.com.ikomm.apps.HSM.adapter.AgendaAdapter;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.repo.AgendaRepo;
import br.com.ikomm.apps.HSM.repo.EventRepo;
import br.com.ikomm.apps.HSM.utils.StringUtils;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * AgendaFragment.java class.
 * Modified by Rodrigo Cericatto at June 30, 2014.
 */
public class AgendaFragment extends SherlockFragment {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// The argument key for the page number this fragment represents.
	public static final String ARG_PAGE = "page";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private List<Agenda> mAgendaList;
	private ListView mListView;
	
	public static Integer mEventId = 0;
	
	// The fragment's page number, which is set to the argument value for ARG_PAGE.
	private Integer mPageNumber;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public AgendaFragment() {}
	
	public AgendaFragment(Integer eventId, Integer position) {
		super();
		mEventId = eventId;
	}
	
	/**
	 * Factory method for this fragment class. Constructs a new fragment for the given page number.
	 */
	public static AgendaFragment create(int pageNumber, Integer eventId) {
		AgendaFragment fragment = new AgendaFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		mEventId = eventId;
		return fragment;
	}

	//--------------------------------------------------
	// Fragment Life Cycle
	//--------------------------------------------------
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_agenda, container, false);
		mListView = (ListView) view.findViewById(R.id.listViewData1);
		mListView.setOnItemClickListener(onItemClickData1());
		setHasOptionsMenu(false);
		return view;
	}

	private OnItemClickListener onItemClickData1() {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adpterView, View view, int position, long id) {
				if (id == 0) {
					return;
				}
				Agenda currentAgenda = mAgendaList.get(position);
				String type = currentAgenda.type;
				filterByType(position, type);
			}
		};
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateView();
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Updates the {@link Fragment} view.
	 */
	private void updateView() {
		// Gets the current Event date.
		String currentDate = getCurrentEventDate();
		
		// Gets the current Agenda.
		AgendaRepo repo = new AgendaRepo(getActivity());
		repo.open();
		mAgendaList = null;
		mAgendaList = repo.byEventAndDate(mEventId, currentDate);
		repo.close();
		mListView.setAdapter(new AgendaAdapter(getActivity(), mAgendaList));
	}
	
	/**
	 * Gets the current {@link Event} date.
	 * 
	 * @return
	 */
	public String getCurrentEventDate() {
		// Gets the Event from the repository.
		EventRepo eventRepo = new EventRepo(getActivity());
		eventRepo.open();
		Event event = eventRepo.getEvent(mEventId);
		
		// Gets the current event date.
		String infoDates = event.info_dates;
		String dates[] = infoDates.replace("|", "-").split("-");
		String currentDate = dates[mPageNumber];
		currentDate = currentDate.trim();
		
		// Formats the date.
		String formattedDate = "Data vazia";
		if (!StringUtils.isEmpty(currentDate)) {
			String day = currentDate.substring(0, 2);
			String month = currentDate.substring(3, 5);
			String year = currentDate.substring(6, 10);
			formattedDate = year + "-" + month + "-" + day;
		}
		return formattedDate;
	}
	
	/**
	 * Filter by type.
	 * 
	 * @param position
	 * @param type
	 */
	public void filterByType(Integer position, String type) {
		if (!StringUtils.isEmpty(type) && !type.equals("break") &&
			!type.equals("coffeebreak") && !type.equals("lunch") &&
			!type.equals("happyhour") && !type.equals("credential")) {
			
			// Goes to LectureDetailsActivity.
			Intent intent = new Intent(getActivity(), LectureDetailsActivity.class);
			Agenda agenda = mAgendaList.get(position);
			Long panelistId = (long)agenda.panelist_id;
			Integer eventId = agenda.event_id;
			
			// Extras.
			intent.putExtra("panelist_id", panelistId);
			intent.putExtra("event_id", eventId);
			startActivity(intent);
		}
	}
}