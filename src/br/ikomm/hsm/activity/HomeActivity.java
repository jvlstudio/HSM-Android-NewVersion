package br.ikomm.hsm.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
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
import br.com.ikomm.apps.HSM.ListaNetworkingActivity;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.neo.EventosNovaActivity;
import br.com.ikomm.apps.HSM.neo.ListaLivrosActivity;
import br.com.ikomm.apps.HSM.neo.RevistaActivity;
import br.ikomm.hsm.model.Home;
import br.ikomm.hsm.repo.HomeRepo;
import br.ikomm.hsm.util.WebServiceCommunication;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class HomeActivity extends FragmentActivity {

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

		addImages();
		addListenerOnButton();

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
        // Get repo.
        HomeRepo repo = new HomeRepo(this);
        repo.open();
        
        // Get Home.
		Cursor cursor = repo.getHome(1);
		Home home = repo.getHomeFromCursor(cursor);
		
		// Get URL's.
		String homeEventsUrl = home.events_image_android;
		String educationUrl = home.education_image_android;
		String homeTvUrl = home.videos_image_android;
		String issuesUrl = home.magazines_image_android;
		String booksUrl = home.books_image_android;
		
		// Initializes layout components.
		ImageView homeEventsImageView = (ImageView)findViewById(R.id.ibtnGrande);
		ImageView educationImageView = (ImageView)findViewById(R.id.ibtn14o);
		ImageView homeTvImageView = (ImageView)findViewById(R.id.ibtn24o);
		ImageView issuesImageView = (ImageView)findViewById(R.id.ibtn34o);
		ImageView booksImageView = (ImageView)findViewById(R.id.ibtn44o);
		
		// Set image views and its contents.
		setUniversalImage(homeEventsUrl, homeEventsImageView);
		setUniversalImage(educationUrl, educationImageView);
		setUniversalImage(homeTvUrl, homeTvImageView);
		setUniversalImage(issuesUrl, issuesImageView);
		setUniversalImage(booksUrl, booksImageView);
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
				startActivity(new Intent(HomeActivity.this, EventosNovaActivity.class));
			} else if (id == LIVROS) {
				startActivity(new Intent(HomeActivity.this, ListaLivrosActivity.class));
			} else if (id == NETWORK) {
				startActivity(new Intent(HomeActivity.this, ListaNetworkingActivity.class));
			} else if (id == REVISTAS) {
				startActivity(new Intent(HomeActivity.this, RevistaActivity.class));
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
	// Click Listeners
	//--------------------------------------------------

	/**
	 * Click listeners.
	 */
	private void addListenerOnButton() {
		findViewById(R.id.ibtnGrande).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, EventosNovaActivity.class));
			}
		});

		findViewById(R.id.ibtn14o).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialogClick(v);
			}
		});

		findViewById(R.id.ibtn24o).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("https://www.youtube.com/channel/UCszAA4rqXiFw8WO_UUq_sAg");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		findViewById(R.id.ibtn34o).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, RevistaActivity.class));
			}
		});

		findViewById(R.id.ibtn44o).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, ListaLivrosActivity.class));
			}
		});
	}
}