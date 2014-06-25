package br.com.ikomm.apps.HSM.neo;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.R.id;
import br.com.ikomm.apps.HSM.R.layout;
import br.com.ikomm.apps.HSM.R.menu;
import br.ikomm.hsm.adapter.RevistaAdapter;
import br.ikomm.hsm.model.Magazine;
import br.ikomm.hsm.model.MagazineRepo;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class RevistaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revista);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.revista, menu);
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
	public static class PlaceholderFragment extends Fragment implements OnItemClickListener {

		public Magazine _magazine = new Magazine();
		public List<Magazine> _magazines = new ArrayList<Magazine>();
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_revista,
					container, false);
			ListView revistas = (ListView) rootView.findViewById(R.id.lvRevistas);
			revistas.setAdapter(new RevistaAdapter(getActivity()));
			
			carregarCampos(rootView);
			
			addListener(rootView);
			
			return rootView;
		}

		private void carregarCampos(View rootView) {
			// Elementos da tela
			TextView magName = (TextView) rootView.findViewById(R.id.magazineName);
			TextView magDescription = (TextView) rootView.findViewById(R.id.magazineDescription);
			ImageView magPicture = (ImageView) rootView.findViewById(R.id.magazinePicture);
			
			// Recuperando a ultima revista cadastrada.
			MagazineRepo _mr = new MagazineRepo(getActivity());
			_mr.open();
			_magazines = _mr.getAllMagazine();
			
			for (Magazine _mag : _magazines) {
				_magazine.id = _mag.id;
				_magazine.name = _mag.name;
				_magazine.picture = _mag.picture;
				_magazine.description = _mag.description;
				_magazine.link = _mag.link;
			}
					
			
			magName.setText(_magazine.name);
			magDescription.setText(_magazine.description);
			
			String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/magazines/"+_magazine.picture;
			DisplayImageOptions _cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		
			imageLoader.displayImage(imageUri, magPicture, _cache);	
		}

		private void addListener(View rootView) {
			// TODO Auto-generated method stub
			ListView revistas = (ListView) rootView.findViewById(R.id.lvRevistas);
			revistas.setOnItemClickListener(this);
			
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
				String URL = _magazine.link;
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		        intent.setData(Uri.parse(URL));
		        this.startActivity(intent);
		}
	}

}
