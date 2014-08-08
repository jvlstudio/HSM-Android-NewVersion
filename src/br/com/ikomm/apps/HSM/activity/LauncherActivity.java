package br.com.ikomm.apps.HSM.activity;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Banner;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Pass;
import br.com.ikomm.apps.HSM.task.Notifiable;
import br.com.ikomm.apps.HSM.utils.DialogUtils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * LauncherActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
public class LauncherActivity extends Activity implements Notifiable {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/ads/";
	public static final String SUPERSTITIAL = "superstitial";
	
	public static final Integer TIME = 500;
	public static final Integer LIMIT = 10;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Controllers.
	private Boolean mAgendaListLoaded = false;
	private Boolean mBookListLoaded = false;
	private Boolean mEventListLoaded = false;
	private Boolean mHomeListLoaded = false;
	private Boolean mMagazineListLoaded = false;
	private Boolean mPanelistListLoaded = false;
	private Boolean mPasseListLoaded = false;
	private Boolean mBannerListLoaded = false;
	
	// Lists.
	private List<Agenda> mAgendaList;
	private List<Book> mBookList;
	private List<Event> mEventList;
	private List<Home> mHomeList;
	private List<Magazine> mMagazineList;
	private List<Panelist> mPanelistList;
	private List<Pass> mPasseList;
	private List<Banner> mBannerList;
	
	// Flag.
	private Boolean mActivityAlreadyAccessed = false;
	private Integer mCalledTasksCount = 0;
	private Handler mHandler = new Handler();
	
	//--------------------------------------------------
	// Activity Life Cycle Methods
	//--------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launcher);
        
        // Begin all the data research.
        updateContent();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	// Checks where the application will go.
    	if (mActivityAlreadyAccessed) {
			finish();
    	}
    	
        // Flag access of this activity.
        mActivityAlreadyAccessed = true;
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mHandler.removeCallbacks(mHandlerChecker);
    }
    
	//----------------------------------------------
	// Methods
	//----------------------------------------------
    
    /**
     * Updates the app content.
     */
    private void updateContent() {
    	// Count of called tasks.
    	mCalledTasksCount = 0;
    	
    	// Update database.
    	ContentManager.getInstance().getDatabaseInfo(this, LauncherActivity.this);
    }
    
    /**
     * Gets data from server or from database.
     */
    public void getData() {
        // Get structures from JSON.
    	ContentManager.getInstance().getAgendaList(this);
        ContentManager.getInstance().getBookList(this);
        ContentManager.getInstance().getEventList(this);
        ContentManager.getInstance().getHomeList(this);
        ContentManager.getInstance().getMagazineList(this);
        ContentManager.getInstance().getPanelistList(this);
        ContentManager.getInstance().getPasseList(this);
        ContentManager.getInstance().getBannerList(this);
    }

    /**
     * Calls the {@link SplashScreenActivity}.
     */
	public void callNextActivity() {
    	mCalledTasksCount++;
    	Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.callNextActivity() -> Count is " + mCalledTasksCount + ".");
    	if (mAgendaListLoaded && mBookListLoaded && mEventListLoaded && mHomeListLoaded &&
    		mMagazineListLoaded && mPanelistListLoaded && mPasseListLoaded && mBannerListLoaded) {
    		
    		setUniversalImage();
    	} else {
    		if (mCalledTasksCount > LIMIT) {
    			DialogUtils.showSimpleAlert(LauncherActivity.this, R.string.error_title_no_internet, R.string.error_msg_no_internet);
    			finish();
    		}
    	}
    }
    
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 */
	public void setUniversalImage() {
		String url = URL + getBannerSuperstitial();
		final ImageView imageView = (ImageView)findViewById(R.id.id_launcher_image_view);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
            	imageView.setImageBitmap(bitmap);
            	Log.i(AppConfiguration.COMMON_LOGGING_TAG, "------------------------- Image downloaded.");
		    	mHandler.postDelayed(mHandlerChecker, TIME);
            }
        });
	}
	
	/**
	 * Gets the {@link Banner} image URL.
	 * 
	 * @return
	 */
	public String getBannerSuperstitial() {
		List<Banner> list = ContentManager.getInstance().getCachedBannerList();
		String url = "";
		
		for (Banner banner : list) {
			if (banner.getSubtype().equals(SUPERSTITIAL)) {
				url = banner.getImage();
			}
		}
		return url;
	}
	
	//----------------------------------------------
	// Tasks
	//----------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public void taskFinished(int type, OperationResult result) {
		// Validating the error result to show the proper dialog message.
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		};
		OperationResult.validateResult(LauncherActivity.this, result, listener, false);
		
		// Tasks.
		if (type == ContentManager.FETCH_TASK.UPDATER) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> UPDATER.");
				getData();
			}
		} else if (type == ContentManager.FETCH_TASK.AGENDA) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> AGENDA.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mAgendaList = (List<Agenda>)result.getEntityList();
				if (mAgendaList.size() > 0 && mAgendaList != null) { 
					mAgendaListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.BOOK) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> BOOK.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mBookList = (List<Book>)result.getEntityList();
				if (mBookList.size() > 0 && mBookList != null) { 
					mBookListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.EVENT) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> EVENT.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mEventList = (List<Event>)result.getEntityList();
				if (mEventList.size() > 0 && mEventList != null) { 
					mEventListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.HOME) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> HOME.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mHomeList = (List<Home>)result.getEntityList();
				if (mHomeList.size() > 0 && mHomeList != null) { 
					mHomeListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.MAGAZINE) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> MAGAZINE.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mMagazineList = (List<Magazine>)result.getEntityList();
				if (mMagazineList.size() > 0 && mMagazineList != null) { 
					mMagazineListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.PANELIST) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> PANELIST.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mPanelistList = (List<Panelist>)result.getEntityList();
				if (mPanelistList.size() > 0 && mPanelistList != null) { 
					mPanelistListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.PASSE) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> PASSE.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mPasseList = (List<Pass>)result.getEntityList();
				if (mPasseList.size() > 0 && mPasseList != null) { 
					mPasseListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.BANNER) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "LauncherActivity.taskFinished() -> BANNER.");
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mBannerList = (List<Banner>)result.getEntityList();
				if (mBannerList.size() > 0 && mBannerList != null) { 
					mBannerListLoaded = true;
				}
			}
		}
		callNextActivity();
	}
	
	//--------------------------------------------------
	// Handlers
	//--------------------------------------------------
	
	/**
	 * Waits for some seconds.
	 */
	private Runnable mHandlerChecker = new Runnable() {
	    public void run() {
    		// Sets the Intent.
    		Intent intent = new Intent(LauncherActivity.this, HomeActivity.class);
    		startActivity(intent);
	    }
	};
}