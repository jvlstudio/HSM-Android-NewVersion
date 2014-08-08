package br.com.ikomm.apps.HSM.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.DrawerAdapter;
import br.com.ikomm.apps.HSM.adapter.HomeGridViewAdapter;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Banner;
import br.com.ikomm.apps.HSM.model.Home;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class HomeActivity extends FragmentActivity implements OnItemClickListener, OnClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String HOME_URL = "http://apps.ikomm.com.br/hsm5/uploads/home/";
	public static final String BANNER_URL = "http://apps.ikomm.com.br/hsm5/uploads/ads/";
	
	public static final String BANNER_FOOTER = "banner_footer";
	public static final String BANNER_SCROLL = "banner_home";
	public static final String BANNER_MENU = "banner_menu";
	
	public static final int HOME = 0;
	public static final int EVENTS = 1;
	public static final int BOOKS = 2;
	public static final int NETWORK = 3;
	public static final int MAGAZINES = 4;
	public static final int HSM_TV = 5;
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	 
	private String[] mMenuTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private Home mHome;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		setActionBar();
		getCurrentHome();
		setEventImage();
		setGridView();
		setEventImage();
		setDrawerMenu();
		setDrawerToggle();
		setBanners();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns true, then it has handled the app icon touch event.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
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
		action.setHomeButtonEnabled(true);
	}
	
	/**
	 * Gets the current {@link Home}.
	 */
	public void getCurrentHome() {
		List<Home> homeList = ContentManager.getInstance().getCachedHomeList();
		if (homeList != null && homeList.size() > 0) {
			mHome = homeList.get(0);
		}
	}
	
	public void setEventImage() {
		ImageView imageView = (ImageView)findViewById(R.id.id_events_image_button);
		setUniversalImage(HOME_URL + mHome.getEventsImageAndroid(), imageView);
		imageView.setOnClickListener(this);
	}
	
	/**
	 * Sets the {@link GridView}.
	 */
	private void setGridView() {
		getCurrentHome();
		
		// Gets the url list.
		List<String> urlList = new ArrayList<String>();
		urlList.add(mHome.getEducationImageAndroid());
		urlList.add(mHome.getVideosImageAndroid());
		urlList.add(mHome.getMagazinesImageAndroid());
		urlList.add(mHome.getBooksImageAndroid());
		
		// Sets the adapter.
		GridView gridView = (GridView)findViewById(R.id.id_grid_view);
		gridView.setAdapter(new HomeGridViewAdapter(this, urlList));
		gridView.setOnItemClickListener(this);
	}
	
	/**
	 * Shows the dialog.
	 * 
	 * @param view
	 */
	public void showDialogClick(View view) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getString(R.string.home_activity_unavailable));
		dialog.setMessage(getString(R.string.home_activity_available_soon));
		dialog.setPositiveButton(getString(R.string.dialog_ok), null);

		final AlertDialog alert = dialog.create();
		alert.show();
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
	// Banners
	//--------------------------------------------------
	
	/**
	 * Sets the {@link Activity} footer.
	 */
	public void setBanners() {
		String url = "";
				
		// Horizontal Scroll View Banner.
		ImageView scrollImageView = (ImageView)findViewById(R.id.id_banner_home_image_button);
		url = BANNER_URL + getSpecificBanner(BANNER_SCROLL);
		setUniversalImage(url, scrollImageView);
		
		// Footer.
		ImageView footerImageView = (ImageView)findViewById(R.id.id_footer_image_view);
		url = BANNER_URL + getSpecificBanner(BANNER_FOOTER);
		setUniversalImage(url, footerImageView);
	}
	
	/**
	 * Gets the {@link Banner} image URL.
	 * 
	 * @return
	 */
	public String getSpecificBanner(String type) {
		List<Banner> list = ContentManager.getInstance().getCachedBannerList();
		String url = "";
		
		for (Banner banner : list) {
			if (banner.getSubtype().equals(type)) {
				url = banner.getImage();
			}
		}
		return url;
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onClick(View view) {
		startActivity(new Intent(HomeActivity.this, EventListActivity.class));
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		switch (position) {
			case 0:
				showDialogClick(view);
				break;
			case 1:
				Uri uri = Uri.parse("https://www.youtube.com/channel/UCszAA4rqXiFw8WO_UUq_sAg");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				break;
			case 2:
				startActivity(new Intent(HomeActivity.this, MagazineActivity.class));
				break;
			case 3:
				startActivity(new Intent(HomeActivity.this, BookListActivity.class));
				break;
		}
	}
	
	//--------------------------------------------------
	// Drawer
	//--------------------------------------------------

	/**
	 * Sets the Drawer menu.
	 */
	public void setDrawerMenu() {
		mMenuTitles = getResources().getStringArray(R.array.menu_list);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.id_drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.id_left_drawer);

		// Set the adapter for the list view.
		mDrawerList.setAdapter(new DrawerAdapter(this, mMenuTitles, getSpecificBanner(BANNER_MENU)));
		// Set the list's click listener.
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mTitle = mDrawerTitle = getTitle();
	}
	
	/**
	 * Sets the Drawer Toggle.
	 */
	public void setDrawerToggle() {
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
	}
	
	//--------------------------------------------------
	// Inner Class
	//--------------------------------------------------
	
	/**
	 * DrawerLayout inner class.
	 * 
	 * @author Rodrigo Cericatto
	 * Modified by Rodrigo Cericatto at July 3, 2014.
	 */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
				case HOME:
					startActivity(new Intent(HomeActivity.this, HomeActivity.class));
					break;
				case EVENTS:
					startActivity(new Intent(HomeActivity.this, EventListActivity.class));
					break;
				case BOOKS:
					startActivity(new Intent(HomeActivity.this, BookListActivity.class));
					break;
				case NETWORK:
					startActivity(new Intent(HomeActivity.this, NetworkingListActivity.class));
					break;
				case MAGAZINES:
					startActivity(new Intent(HomeActivity.this, MagazineActivity.class));
					break;
				case HSM_TV:
					Uri uri = Uri.parse("http://www.youtube.com/watch?v=ZHolmn4LBzg");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
					break;
				default:
					Toast.makeText(HomeActivity.this, getString(R.string.home_activity_available_soon), Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}
}