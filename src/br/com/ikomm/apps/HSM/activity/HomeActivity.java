package br.com.ikomm.apps.HSM.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.CustomApplication;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.HomeGridViewAdapter;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.services.WebServiceCommunication;
import br.com.ikomm.apps.HSM.task.ReadImageAsyncTask;
import br.com.ikomm.apps.HSM.utils.AsyncTaskUtils;
import br.com.ikomm.apps.HSM.utils.FileBitmapUtils;
import br.com.ikomm.apps.HSM.utils.Utils;

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
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/home/";

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
		setThread();
		setEventListImages();
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
	 * Sets the Drawer menu.
	 */
	public void setDrawerMenu() {
		mMenuTitles = getResources().getStringArray(R.array.menuList);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.id_drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.id_left_drawer);

		// Set the adapter for the list view.
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mMenuTitles));
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
	
	/**
	 * Sets the Thread.
	 */
	public void setThread() {
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
		setUniversalImage(URL + mHome.getEventsImageAndroid(), imageView);
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
	
	/**
	 * Sets the {@link Event} list images to be used into {@link EventListActivity}.
	 */
	public void setEventListImages() {
		List<Event> list = ContentManager.getInstance().getCachedEventList();
		Utils.fileLog("HomeActivity.setEventListImages() -> ----------------------------------------------------------------------------------------------------");
		Utils.fileLog("HomeActivity.setEventListImages(). There's " + list.size() + " events into this app.");
		Boolean imagesInCache = ContentManager.getInstance().eventImagesInCache();
		Utils.fileLog("HomeActivity.setEventListImages(). Images in cache? " + imagesInCache + ".");
		
		Integer cont = 0;
		if (!imagesInCache) {
			putImagesInCache(list);
			Utils.fileLog("HomeActivity.setEventListImages() -> Into " + cont++ + "iteraction we have " + ContentManager.getInstance().getMapSize()  + " bitmaps.");
		}
	}
	
	/**
	 * Put images into the {@link ContentManager}. 
	 * 
	 * @param list
	 */
	public void putImagesInCache(List<Event> list) {
		FileBitmapUtils fileManager = new FileBitmapUtils();
		for (Event event : list) {
			// Gets the path.
			String path = fileManager.createDir(CustomApplication.CACHE_DIR);
			path += event.getImageList();
			Utils.fileLog("HomeActivity.setEventListImages(). Inside the FOR. The path is " + path + ".");
			
			// Puts each image into the ContentManager.
			final String completePath = path;
			ReadImageAsyncTask task = new ReadImageAsyncTask(fileManager, completePath) {
				protected void onPostExecute(Bitmap bitmap) {
					if (bitmap != null) {
						Utils.fileLog("HomeActivity.setEventListImages() -> Into ReadImageAsyncTask.onPostExecute().\n" +
							"Setting the Bitmap for file " + completePath + ".");
						ContentManager.getInstance().addBitmap(completePath, bitmap);
					}
				};
			};
			AsyncTaskUtils.execute(task, new String[] {});
		}
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
	// Drawer Layout
	//--------------------------------------------------

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
}