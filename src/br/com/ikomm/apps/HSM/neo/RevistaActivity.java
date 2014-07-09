package br.com.ikomm.apps.HSM.neo;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.RevistaAdapter;
import br.ikomm.hsm.model.Magazine;
import br.ikomm.hsm.repo.MagazineRepo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class RevistaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revista);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnItemClickListener {
		public Magazine mMagazine = new Magazine();
		public List<Magazine> mMagazineList = new ArrayList<Magazine>();
		
		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_revista, container, false);
			ListView revistas = (ListView) rootView.findViewById(R.id.lvRevistas);
			revistas.setAdapter(new RevistaAdapter(getActivity()));
			
			carregarCampos(rootView);
			addListener(rootView);
			
			return rootView;
		}

		private void carregarCampos(View rootView) {
			// Elementos da tela.
			TextView magName = (TextView) rootView.findViewById(R.id.magazineName);
			TextView magDescription = (TextView) rootView.findViewById(R.id.magazineDescription);
			ImageView magPicture = (ImageView) rootView.findViewById(R.id.magazinePicture);
			
			// Recuperando a ultima revista cadastrada.
			MagazineRepo magazineRepo = new MagazineRepo(getActivity());
			magazineRepo.open();
			mMagazineList = magazineRepo.getAllMagazine();
			
			for (Magazine _mag : mMagazineList) {
				mMagazine.id = _mag.id;
				mMagazine.name = _mag.name;
				mMagazine.picture = _mag.picture;
				mMagazine.description = _mag.description;
				mMagazine.link = _mag.link;
			}
			magName.setText(mMagazine.name);
			magDescription.setText(mMagazine.description);
			
			String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/mMagazineList/"+mMagazine.picture;
			DisplayImageOptions _cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		
			imageLoader.displayImage(imageUri, magPicture, _cache);	
		}

		private void addListener(View rootView) {
			ListView revistas = (ListView) rootView.findViewById(R.id.lvRevistas);
			revistas.setOnItemClickListener(this);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String URL = mMagazine.link;
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
	        intent.setData(Uri.parse(URL));
	        this.startActivity(intent);
		}
	}
}