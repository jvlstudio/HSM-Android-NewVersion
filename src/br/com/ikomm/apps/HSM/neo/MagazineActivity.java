package br.com.ikomm.apps.HSM.neo;

import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.RevistaAdapter;
import br.ikomm.hsm.model.Magazine;
import br.ikomm.hsm.repo.MagazineRepo;
import br.ikomm.hsm.util.StringUtils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * BookDetailsActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class MagazineActivity extends SherlockActivity implements OnItemClickListener {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/magazines/";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------	
	
	private Magazine mMagazine = new Magazine();
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_magazine);
		
		setActionBar();
		loadFields();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.application_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	/**
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Loads all fields.
	 */
	private void loadFields() {
		getLastMagazine();
		setUniversalImage(URL + mMagazine.picture);
		setListView();
	}

	/**
	 * Gets the {@link Magazine} list.
	 * 
	 * @return
	 */
	public List<Magazine> getMagazineList() {
		MagazineRepo magazineRepo = new MagazineRepo(this);
		magazineRepo.open();
		List<Magazine> list = magazineRepo.getAllMagazine();
		return list;
	}
	
	/**
	 * Gets the last {@link Magazine}.
	 */
	public void getLastMagazine() {
		TextView lastMagazineNameTextView = (TextView)findViewById(R.id.id_last_magazine_name_text_view);
		TextView lastMagazineDescriptionTextView = (TextView)findViewById(R.id.id_last_magazine_description_text_view);
		
		// Gets the last Magazine.
		for (Magazine magazine : getMagazineList()) {
			mMagazine.id = magazine.id;
			mMagazine.name = magazine.name;
			mMagazine.picture = magazine.picture;
			mMagazine.description = magazine.description;
			mMagazine.link = magazine.link;
		}
		lastMagazineNameTextView.setText(mMagazine.name);
		lastMagazineDescriptionTextView.setText(mMagazine.description);
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 */
	public void setUniversalImage(String url) {
		ImageView imageView = (ImageView)findViewById(R.id.id_magazine_image_view);
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imageView, cache);
	}
	
	/**
	 * Sets the {@link ListView} adapter.
	 */
	public void setListView() {
		ListView magazineList = (ListView)findViewById(R.id.id_magazine_list_view);
		magazineList.setAdapter(new RevistaAdapter(this));
		magazineList.setOnItemClickListener(this);
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String url = mMagazine.link;
		if (!StringUtils.isEmpty(url)) {
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
	        intent.setData(Uri.parse(url));
	        startActivity(intent);
		} else {
			Toast.makeText(this, "Favor pedir para alguém me cadastrar no Backend!", Toast.LENGTH_LONG).show();
		}
	}
}