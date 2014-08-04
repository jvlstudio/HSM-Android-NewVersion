package br.com.ikomm.apps.HSM.activity;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Passe;
import br.com.ikomm.apps.HSM.task.Notifiable;
import br.com.ikomm.apps.HSM.utils.DialogUtils;
import br.com.ikomm.apps.HSM.utils.Utils;

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
	
	// Limit of tasks finished attempts.
	public static final Integer LIMIT = 30;
	
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
	
	// Lists.
	private List<Agenda> mAgendaList;
	private List<Book> mBookList;
	private List<Event> mEventList;
	private List<Home> mHomeList;
	private List<Magazine> mMagazineList;
	private List<Panelist> mPanelistList;
	private List<Passe> mPasseList;
	
	// Flag.
	private Boolean mLauncherActivityAlreadyAccessed = false;
	private Integer mCalledTasksCount = 0;
	
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
    	if (mLauncherActivityAlreadyAccessed) {
			finish();
    	}
    	
        // Flag access of this activity.
        mLauncherActivityAlreadyAccessed = true;
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
    }

    /**
     * Calls the {@link SplashScreenActivity}.
     */
	public void callNextActivity() {
    	mCalledTasksCount++;
    	if (mAgendaListLoaded && mBookListLoaded && mEventListLoaded && mHomeListLoaded && mMagazineListLoaded && mPanelistListLoaded && mPasseListLoaded) {
    		Integer agendaSize = ContentManager.getInstance().getCachedAgendaList().size();
    		
    		// Checks the data.
    		Integer bookSize = ContentManager.getInstance().getCachedBookList().size();
    		Integer eventSize = ContentManager.getInstance().getCachedEventList().size();
    		Integer homeSize = ContentManager.getInstance().getCachedHomeList().size();
    		Integer magazineSize = ContentManager.getInstance().getCachedMagazineList().size();
    		Integer panelistSize = ContentManager.getInstance().getCachedPanelistList().size();
    		Integer passeSize = ContentManager.getInstance().getCachedPasseList().size();
    		String text = "Agenda: " + agendaSize + ", Book: " + bookSize + ", Event: " + eventSize 
    			+ ", Home: " + homeSize + ", Magazine: " + magazineSize + ", Panelist: " + panelistSize + ", Passe: " + passeSize + ".";
    		Utils.fileLog("LauncherActivity.callNextActivity() -> " + text);
    		
    		// Sets the Intent.
    		Intent intent = new Intent(this, SplashScreenActivity.class);
    		startActivity(intent);
    	} else {
    		if (mCalledTasksCount > LIMIT) {
    			DialogUtils.showSimpleAlert(LauncherActivity.this, R.string.error_title_no_internet, R.string.error_msg_no_internet);
    			finish();
    		}
    	}
    }
    
	//----------------------------------------------
	// Tasks
	//----------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public void taskFinished(int type, OperationResult result) {
		// Validating the error result to show the proper dialog message.
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		};
		OperationResult.validateResult(LauncherActivity.this, result, listener);
		 
		if (type == ContentManager.FETCH_TASK.UPDATER) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				getData();
			}
		} else if (type == ContentManager.FETCH_TASK.AGENDA) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mAgendaList = (List<Agenda>)result.getEntityList();
				if (mAgendaList.size() > 0 && mAgendaList != null) { 
					mAgendaListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.BOOK) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mBookList = (List<Book>)result.getEntityList();
				if (mBookList.size() > 0 && mBookList != null) { 
					mBookListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.EVENT) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mEventList = (List<Event>)result.getEntityList();
				if (mEventList.size() > 0 && mEventList != null) { 
					mEventListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.HOME) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mHomeList = (List<Home>)result.getEntityList();
				if (mHomeList.size() > 0 && mHomeList != null) { 
					mHomeListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.MAGAZINE) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mMagazineList = (List<Magazine>)result.getEntityList();
				if (mMagazineList.size() > 0 && mMagazineList != null) { 
					mMagazineListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.PANELIST) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mPanelistList = (List<Panelist>)result.getEntityList();
				if (mPanelistList.size() > 0 && mPanelistList != null) { 
					mPanelistListLoaded = true;
				}
			}
		} else if (type == ContentManager.FETCH_TASK.PASSE) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				mPasseList = (List<Passe>)result.getEntityList();
				if (mPasseList.size() > 0 && mPasseList != null) { 
					mPasseListLoaded = true;
				}
			}
		}
		callNextActivity();
	}
}