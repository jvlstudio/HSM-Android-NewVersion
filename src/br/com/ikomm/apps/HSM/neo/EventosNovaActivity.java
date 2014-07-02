package br.com.ikomm.apps.HSM.neo;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.model.EventRepo;

public class EventosNovaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventos_nova);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eventos_nova, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private EventRepo _er;
		private List<Event> events = new ArrayList<Event>();
		int total = 0;
		
		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_eventos_nova, container, false);
			
			LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.listaEventos);
			
			_er = new EventRepo(getActivity());
			_er.open();
			events = _er.getAllEvent();
			
			total = events.size();
			ImageButton[] imgButton = new ImageButton[total];
			LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 580);
			params.leftMargin = 10;
			params.rightMargin = 40;
			params.topMargin = 20;
			
			if (events.size() > 0) {
				for (int i = 0; i < events.size();) {
					imgButton[i] = new ImageButton(getActivity());
					
					imgButton[i].setImageResource(R.drawable.hsm_cel_um_damodaran);
					imgButton[i].setBackgroundResource(0);
					imgButton[i].setAdjustViewBounds(true);
					
					final Event _event = events.get(i);
					imgButton[i].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(getActivity(), DetalheEventoNeoActivity.class);
							intent.putExtra("id", _event.id);
							startActivity(intent);
						}
					});
					linear.addView(imgButton[i], params);
					i++;
				}
			}
			return rootView;
		}
	}
}