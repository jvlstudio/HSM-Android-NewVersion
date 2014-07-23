package br.ikomm.hsm.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.neo.EventListActivity;
import br.com.ikomm.apps.HSM.neo.BookListActivity;
import br.com.ikomm.apps.HSM.neo.MagazineActivity;
import br.ikomm.hsm.manager.ContentManager;
import br.ikomm.hsm.model.Home;
import br.ikomm.hsm.util.WebServiceCommunication;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class HomeActivity extends FragmentActivity implements OnClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/home/";
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	public static final Integer HOME = 0;
	public static final Integer EVENTOS = 1;
	public static final Integer LIVROS = 2;
	public static final Integer NETWORK = 3;
	public static final Integer REVISTAS = 4;
	public static final Integer HSM_TV = 5;
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	 
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ActionBarDrawerToggle mDrawerToggle;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		ContentManager.getInstance().setContext(this);

		addImages();

		mPlanetTitles = getResources().getStringArray(R.array.menuList);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view.
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
		// Set the list's click listener.
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			/**
			 * Called when a drawer has settled in a completely closed state.
			 */
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// Creates call to onPrepareOptionsMenu().
				invalidateOptionsMenu();
			}

			/**
			 * Called when a drawer has settled in a completely open state.
			 */
			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// Creates call to onPrepareOptionsMenu().
				invalidateOptionsMenu();
			}
		};

		// Set the drawer toggle as the DrawerListener.
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// Update banners no sharedPreferences.
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					WebServiceCommunication ws = new WebServiceCommunication();
					ws.updateBanners(HomeActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	/**
	 * Adds images to the {@link ImageView}.
	 */
	private void addImages() {
        // Get Home.
		List<Home> list = ContentManager.getInstance().getAllHome();
		Home home = list.get(0);
		
		// Get URL's.
		String homeEventsUrl = home.events_image_android;
		String educationUrl = home.education_image_android;
		String homeTvUrl = home.videos_image_android;
		String issuesUrl = home.magazines_image_android;
		String booksUrl = home.books_image_android;
		
		// Initializes layout components.
		ImageView homeEventsImageView = (ImageView)findViewById(R.id.id_events_image_button);
		homeEventsImageView.setOnClickListener(this);
		
		ImageView educationImageView = (ImageView)findViewById(R.id.id_future_content_image_button);
		educationImageView.setOnClickListener(this);
		
		ImageView homeTvImageView = (ImageView)findViewById(R.id.id_home_tv_image_button);
		homeTvImageView.setOnClickListener(this);
		
		ImageView issuesImageView = (ImageView)findViewById(R.id.id_magazines_image_button);
		issuesImageView.setOnClickListener(this);
		
		ImageView booksImageView = (ImageView)findViewById(R.id.id_books_image_button);
		booksImageView.setOnClickListener(this);
		
		// Set image views and its contents.
		setUniversalImage(URL + homeEventsUrl, homeEventsImageView);
		setUniversalImage(URL + educationUrl, educationImageView);
		setUniversalImage(URL + homeTvUrl, homeTvImageView);
		setUniversalImage(URL + issuesUrl, issuesImageView);
		setUniversalImage(URL + booksUrl, booksImageView);
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage(String url, ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imageView, cache);
	}
	
	//--------------------------------------------------
	// Drawer Layout
	//--------------------------------------------------

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			if (id == HOME) {
				startActivity(new Intent(HomeActivity.this, HomeActivity.class));
			} else if (id == EVENTOS) {
				startActivity(new Intent(HomeActivity.this, EventListActivity.class));
			} else if (id == LIVROS) {
				startActivity(new Intent(HomeActivity.this, BookListActivity.class));
			} else if (id == NETWORK) {
				startActivity(new Intent(HomeActivity.this, NetworkingListActivity.class));
			} else if (id == REVISTAS) {
				startActivity(new Intent(HomeActivity.this, MagazineActivity.class));
			} else if (id == HSM_TV) {
				Uri uri = Uri.parse("http://www.youtube.com/watch?v=ZHolmn4LBzg");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			} else {
				Toast.makeText(HomeActivity.this, "Dispon’vel em breve", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	public void showDialogClick(View v) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Conteœdo Indispon’vel");
		dialog.setMessage("Este conteœdo estar‡ dispon’vel em breve.");
		dialog.setPositiveButton("OK", null);

		final AlertDialog alert = dialog.create();
		alert.show();
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.id_events_image_button:
				startActivity(new Intent(HomeActivity.this, EventListActivity.class));
				break;
			case R.id.id_future_content_image_button:
				showDialogClick(view);
				break;
			case R.id.id_home_tv_image_button:
				Uri uri = Uri.parse("https://www.youtube.com/channel/UCszAA4rqXiFw8WO_UUq_sAg");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				break;
			case R.id.id_magazines_image_button:
				startActivity(new Intent(HomeActivity.this, MagazineActivity.class));
				break;
			case R.id.id_books_image_button:
				startActivity(new Intent(HomeActivity.this, BookListActivity.class));
				break;
		}
	}
}