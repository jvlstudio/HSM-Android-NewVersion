package br.com.ikomm.apps.HSM.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.activity.LauncherActivity;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Banner;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Pass;
import br.com.ikomm.apps.HSM.task.AgendaAsyncTask;
import br.com.ikomm.apps.HSM.task.BannerAsyncTask;
import br.com.ikomm.apps.HSM.task.BookAsyncTask;
import br.com.ikomm.apps.HSM.task.EventAsyncTask;
import br.com.ikomm.apps.HSM.task.HomeAsyncTask;
import br.com.ikomm.apps.HSM.task.MagazineAsyncTask;
import br.com.ikomm.apps.HSM.task.Notifiable;
import br.com.ikomm.apps.HSM.task.PanelistAsyncTask;
import br.com.ikomm.apps.HSM.task.PassAsyncTask;
import br.com.ikomm.apps.HSM.task.PassPurchaseAsyncTask;
import br.com.ikomm.apps.HSM.task.UpdaterAsyncTask;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * ContentManager.java class. <br>
 * In this class I keep all Memory Cache variables, lists and data.
 * 
 * @author Rodrigo Cericatto
 * @since July 21, 2014
 */
public class ContentManager {

	//----------------------------------------------
	// Statics
	//----------------------------------------------
	
	public void _______________STATICS_______________() {}
	
	// Fetch task types.
	public static final class FETCH_TASK {
		public static final int UPDATER = 0;
		public static final int AGENDA = 1;
		public static final int BOOK = 2;
		public static final int EVENT = 3;
		public static final int HOME = 4;
		public static final int MAGAZINE = 5;
		public static final int PANELIST = 6;
		public static final int PASSE = 7;
		public static final int PASS_PURCHASE = 8;
		public static final int BANNER = 9;
	}
	
	// The singleton instance.
	private static ContentManager sInstance = null;
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------

	public void _______________ATTRIBUTES_______________() {}
	
	// The application context.
	private Context mContext;
	
	// Cached list values.
	private Boolean mDatabaseNeedsUpdate;
	
	private List<Agenda> mAgendaList = new ArrayList<Agenda>();
	private List<Book> mBookList = new ArrayList<Book>();
	private List<Event> mEventList = new ArrayList<Event>();
	private List<Home> mHomeList = new ArrayList<Home>();
	private List<Magazine> mMagazineList = new ArrayList<Magazine>();
	private List<Panelist> mPanelistList = new ArrayList<Panelist>();
	private List<Pass> mPasseList = new ArrayList<Pass>();
	private List<Banner> mBannerList = new ArrayList<Banner>();
	
	// Notifiables map.
	private Map<Object, Notifiable> mTaskNotifiables = new HashMap<Object, Notifiable>();
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	public void _______________CONSTRUCTOR_______________() {}
	
	/**
	 * Private constructor.
	 */
	private ContentManager() {}
	
	/**
	 * @return The singleton instance of ContentManager.
	 */
	public static ContentManager getInstance() {
		if (sInstance == null) {
			sInstance = new ContentManager();
		}
		return sInstance;
	}

	//----------------------------------------------
	// Global Methods
	//----------------------------------------------
	
	public void _______________GLOBALS_______________() {}
	
	/**
	 * Sets the application context.
	 * 
	 * @param context The context to be used.
	 */
	public void setContext(Context context) {
		mContext = context;
	}
	
	/**
	 * Gets the application context.
	 */
	public Context getContext() {
		return mContext;
	}
	
	/**
	 * Cleans all cached content.
	 */
	public void clean() {
		mAgendaList = null;
		mBookList = null;
		mEventList = null;
		mHomeList = null;
		mMagazineList = null;
		mPanelistList = null;
		mPasseList = null;
		mBannerList = null;
	}
	
	//----------------------------------------------
	// Updater
	//----------------------------------------------

	public void _______________UPDATER_______________() {}
	
	/**
	 * Gets the database info from cache.
	 * 
	 * @return
	 */
	public Boolean getCachedDatabaseInfo() {
		return mDatabaseNeedsUpdate;
	}
	
	/**
	 * Gets the database info.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getDatabaseInfo(Notifiable notifiable, Context context) {
		UpdaterAsyncTask task = new UpdaterAsyncTask(context);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Agenda
	//----------------------------------------------

	public void _______________AGENDA_______________() {}
	
	/**
	 * Gets the {@link Agenda} list from cache.
	 * 
	 * @return
	 */
	public List<Agenda> getCachedAgendaList() {
		return mAgendaList;
	}
	
	/**
	 * Gets the {@link Agenda} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getAgendaList(Notifiable notifiable) {
		AgendaAsyncTask task = new AgendaAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Book
	//----------------------------------------------

	public void _______________BOOK_______________() {}
	
	/**
	 * Gets the {@link Agenda} list from cache.
	 * 
	 * @return
	 */
	public List<Book> getCachedBookList() {
		return mBookList;
	}
	
	/**
	 * Gets the {@link Book} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getBookList(Notifiable notifiable) {
		BookAsyncTask task = new BookAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Event
	//----------------------------------------------

	public void _______________EVENT_______________() {}
	
	/**
	 * Gets the {@link Event} list from cache.
	 * 
	 * @return
	 */
	public List<Event> getCachedEventList() {
//		Collections.sort(mEventList, new EventComparator());
		return mEventList;
	}
	
	/**
	 * Gets the {@link Event} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getEventList(Notifiable notifiable) {
		EventAsyncTask task = new EventAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Home
	//----------------------------------------------

	public void _______________HOME_______________() {}
	
	/**
	 * Gets the {@link Home} list from cache.
	 * 
	 * @return
	 */
	public List<Home> getCachedHomeList() {
		return mHomeList;
	}
	
	/**
	 * Gets the {@link Home} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getHomeList(Notifiable notifiable) {
		HomeAsyncTask task = new HomeAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Magazine
	//----------------------------------------------

	public void _______________MAGAZINE_______________() {}
	
	/**
	 * Gets the {@link Magazine} list from cache.
	 * 
	 * @return
	 */
	public List<Magazine> getCachedMagazineList() {
		return mMagazineList;
	}
	
	/**
	 * Gets the {@link Magazine} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getMagazineList(Notifiable notifiable) {
		MagazineAsyncTask task = new MagazineAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Panelist
	//----------------------------------------------

	public void _______________PANELIST_______________() {}
	
	/**
	 * Gets the {@link Panelist} list from cache.
	 * 
	 * @return
	 */
	public List<Panelist> getCachedPanelistList() {
		return mPanelistList;
	}
	
	/**
	 * Gets the {@link Panelist} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getPanelistList(Notifiable notifiable) {
		PanelistAsyncTask task = new PanelistAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Pass
	//----------------------------------------------

	public void _______________PASSE_______________() {}
	
	/**
	 * Gets the {@link Pass} list from cache.
	 * 
	 * @return
	 */
	public List<Pass> getCachedPasseList() {
		return mPasseList;
	}
	
	/**
	 * Gets the {@link Pass} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getPasseList(Notifiable notifiable) {
		PassAsyncTask task = new PassAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	/**
	 * Purchases a {@link Pass}.
	 * 
	 * @param notifiable The notifiable object to be called.
	 * @param name
	 * @param email
	 * @param company
	 * @param role
	 * @param cpf
	 * @param passId
	 */
	public void setPassPurchase(Notifiable notifiable, String name, String email, String company, String role, String cpf, Integer passId) {
		PassPurchaseAsyncTask task = new PassPurchaseAsyncTask(name, email, company, role, cpf, passId);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Banner
	//----------------------------------------------
	
	/**
	 * Gets the {@link Banner} list from cache.
	 * 
	 * @return
	 */
	public List<Banner> getCachedBannerList() {
		return mBannerList;
	}
	
	/**
	 * Gets the {@link Pass} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getBannerList(Notifiable notifiable) {
		BannerAsyncTask task = new BannerAsyncTask(mContext, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Tasks Handling
	//----------------------------------------------
	
	public void _______________TASKS_______________() {}
	
	@SuppressWarnings("unchecked")
	public void taskFinished(Object task, OperationResult result) {
		int taskType = getTaskType(task);
		
		// Gets current task result.
		if (FETCH_TASK.UPDATER == taskType) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the database info in the cache.
				mDatabaseNeedsUpdate = (Boolean)result.getEntity();
				
				// Check if the database needs update.
				Integer numberOfExecutions = Utils.getPreference(ContentManager.getInstance().getContext(), LauncherActivity.NUMBER_OF_EXECUTIONS);
				Log.i(AppConfiguration.COMMON_LOGGING_TAG, "[ContentManager.getDatabaseInfo] Number of executions is " + numberOfExecutions + ".");
				Integer mod = numberOfExecutions % AppConfiguration.REFRESH_CACHE_INTERVAL;
				if (mod == 0) {
					mDatabaseNeedsUpdate = true;
					Log.i(AppConfiguration.COMMON_LOGGING_TAG, "[ContentManager.getDatabaseInfo] Mod acting!");
				}
			}
		} else if (FETCH_TASK.AGENDA == taskType) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Agenda list in the cache.
				mAgendaList = (List<Agenda>)result.getEntityList();
			}
		} else if (FETCH_TASK.BOOK == taskType) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Book list in the cache.
				mBookList = (List<Book>)result.getEntityList();
			}
		} else if (FETCH_TASK.EVENT == taskType) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Event list in the cache.
				mEventList = (List<Event>)result.getEntityList();
			}
		} else if (FETCH_TASK.HOME == taskType) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Home list in the cache.
				mHomeList = (List<Home>)result.getEntityList();
			}
		} else if (FETCH_TASK.MAGAZINE == taskType) {
			if (ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Magazine list in the cache.
				mMagazineList = (List<Magazine>)result.getEntityList();
			}
		} else if (FETCH_TASK.PANELIST == taskType) {
			if (result != null && ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Panelist list in the cache.
				mPanelistList = (List<Panelist>)result.getEntityList();
			}
		} else if (FETCH_TASK.PASSE == taskType) {
			if (result != null && ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Pass list in the cache.
				mPasseList = (List<Pass>)result.getEntityList();
			}
		} else if (FETCH_TASK.PASS_PURCHASE == taskType) {
			// Do nothing.
		} else if (FETCH_TASK.BANNER == taskType) {
			if (result != null && ResultType.SUCCESS.equals(result.getResultType())) {
				// Puts the Banner list in the cache.
				mBannerList = (List<Banner>)result.getEntityList();
			}
		}
		
		// Removes performed task.
		Notifiable notifiable = mTaskNotifiables.get(task);
		if (notifiable != null) {
			notifiable.taskFinished(taskType, result);
			mTaskNotifiables.remove(task);
		}
	}
	
	/**
	 * Returns the task type.
	 * 
	 * @param task
	 * @return The task type.
	 */
	private int getTaskType(Object task) {
		if (task instanceof UpdaterAsyncTask) {
			return FETCH_TASK.UPDATER;
		} if (task instanceof AgendaAsyncTask) {
			return FETCH_TASK.AGENDA;
		} else if (task instanceof BookAsyncTask) {
			return FETCH_TASK.BOOK;
		} else if (task instanceof EventAsyncTask) {
			return FETCH_TASK.EVENT;
		} else if (task instanceof HomeAsyncTask) {
			return FETCH_TASK.HOME;
		} else if (task instanceof MagazineAsyncTask) {
			return FETCH_TASK.MAGAZINE;
		} else if (task instanceof PanelistAsyncTask) {
			return FETCH_TASK.PANELIST;
		} else if (task instanceof PassAsyncTask) {
			return FETCH_TASK.PASSE;
		} else if (task instanceof PassPurchaseAsyncTask) {
			return FETCH_TASK.PASS_PURCHASE;
		} else if (task instanceof BannerAsyncTask) {
			return FETCH_TASK.BANNER;
		}
		return -1;
	}
	
	//--------------------------------------------------
	// Comparator
	//--------------------------------------------------
	
	/*
	public void _______________COMPARATOR_______________() {}
	
	public class EventComparator implements Comparator<Event> {
		@Override
		public int compare(Event event, Event event2) {
			String infoDate = event.getInfoDates();
			String dates[] = infoDate.replace("|", "-").split("-");
			String date = dates[0].trim();
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "----------\nCurrent date is " + date + ".");
			
			String infoDate2 = event2.getInfoDates();
			String dates2[] = infoDate2.replace("|", "-").split("-");
			String date2 = dates2[0].trim();
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "Current date2 is " + date2 + ".");
			
			return date.compareTo(date2);
		}
	}
	*/
}