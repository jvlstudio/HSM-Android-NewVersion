package br.com.ikomm.apps.HSM.neo;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.LivrosAdapter;

/**
 * ListaLivrosActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class ListaLivrosActivity extends FragmentActivity {

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_livros);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	//--------------------------------------------------
	// Fragment
	//--------------------------------------------------

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment{
		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_lista_livros, container, false);
			ListView listaLivros = (ListView) rootView.findViewById(R.id.listBooks);
			listaLivros.setAdapter(new LivrosAdapter(getActivity()));
			listaLivros.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
					Intent intent = new Intent(getActivity(), DetalheLivroActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
				}
			});
			return rootView;
		}
	}
}