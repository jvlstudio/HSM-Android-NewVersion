package br.com.ikomm.apps.HSM.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Passe;
import br.com.ikomm.apps.HSM.repo.AgendaRepo;
import br.com.ikomm.apps.HSM.repo.BookRepo;
import br.com.ikomm.apps.HSM.repo.EventRepo;
import br.com.ikomm.apps.HSM.repo.HomeRepo;
import br.com.ikomm.apps.HSM.repo.MagazineRepo;
import br.com.ikomm.apps.HSM.repo.PanelistRepo;
import br.com.ikomm.apps.HSM.repo.PasseRepo;
import br.com.ikomm.apps.HSM.task.AgendaAsyncTask;
import br.com.ikomm.apps.HSM.task.BookAsyncTask;
import br.com.ikomm.apps.HSM.task.EventAsyncTask;
import br.com.ikomm.apps.HSM.task.HomeAsyncTask;
import br.com.ikomm.apps.HSM.task.MagazineAsyncTask;
import br.com.ikomm.apps.HSM.task.Notifiable;
import br.com.ikomm.apps.HSM.task.PanelistAsyncTask;
import br.com.ikomm.apps.HSM.task.PasseAsyncTask;
import br.com.ikomm.apps.HSM.task.UpdaterAsyncTask;

/**
 * ContentManager.java class.
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
	private List<Passe> mPasseList = new ArrayList<Passe>();
	
	// Bitmap values.
	private List<Bitmap> mLinearLayoutBitmapList = new ArrayList<Bitmap>();
	
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
		mLinearLayoutBitmapList = null;
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
	// Bitmap
	//----------------------------------------------
	
	public void _______________BITMAP_______________() {}
	
	/**
	 * Adds a {@link Bitmap} to the {@link Bitmap} list.
	 * 
	 * @param bitmap
	 */
	public void addBitmap(Bitmap bitmap) {
		mLinearLayoutBitmapList.add(bitmap);
	}
	
	/**
	 * Gets the {@link Bitmap} list.
	 * 
	 * @return
	 */
	public List<Bitmap> getBitmapList() {
		return mLinearLayoutBitmapList;
	}
	
	//----------------------------------------------
	// Agenda
	//----------------------------------------------

	public void _______________AGENDA_______________() {}
	
	/**
	 * Gets all {@link Agenda}.
	 * 
	 * @return
	 */
	public List<Agenda> getMickAgendaList() {
		List<Agenda> list = null;
		
		// Checks if is null.
		if (mAgendaList == null) {
			AgendaRepo repo = new AgendaRepo(mContext);
			repo.open();
			list = repo.getAll();
			repo.close();
			mAgendaList = list;
		} else {
			list = mAgendaList;
		}
		return list;
	}

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
	 * Gets all {@link Book}.
	 * 
	 * @return
	 */
	public List<Book> getMickBookList() {
		List<Book> list = null;
		
		// Checks if is null.
		if (mBookList == null) {
			BookRepo repo = new BookRepo(mContext);
			repo.open();
			list = repo.getAll();
			repo.close();
			mBookList = list;
		} else {
			list = mBookList;
		}
		return list;
	}
	
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
	 * Gets all {@link Event}.
	 * 
	 * @return
	 */
	public List<Event> getMickEventList() {
		List<Event> list = null;
		
		// Checks if is null.
		if (mEventList == null) {
			EventRepo repo = new EventRepo(mContext);
			repo.open();
			list = repo.getAll();
			repo.close();
			mEventList = list;
		} else {
			list = mEventList;
		}
		return list;
	}
	
	/**
	 * Gets the {@link Event} list from cache.
	 * 
	 * @return
	 */
	public List<Event> getCachedEventList() {
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
	 * Gets all {@link Home}.
	 * 
	 * @return
	 */
	public List<Home> getMickHomeList() {
		List<Home> list = null;
		
		// Checks if is null.
		if (mHomeList.size() <= 0 ||  mHomeList == null) {
			HomeRepo repo = new HomeRepo(mContext);
			repo.open();
			list = repo.getAll();
			repo.close();
			mHomeList = list;
		} else {
			list = mHomeList;
		}
		return list;
	}
	
	/**
	 * Gets the {@link Home} list from cache.
	 * 
	 * @return
	 */
	public List<Event> getCachedHomeList() {
		return mEventList;
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
	 * Gets all {@link Magazine}.
	 * 
	 * @return
	 */
	public List<Magazine> getMickMagazineList() {
		List<Magazine> list = null;
		
		// Checks if is null.
		if (mMagazineList == null) {
			MagazineRepo repo = new MagazineRepo(mContext);
			repo.open();
			list = repo.getAll();
			repo.close();
			mMagazineList = list;
		} else {
			list = mMagazineList;
		}
		return list;
	}
	
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
	 * Gets all {@link Panelist}.
	 * 
	 * @return
	 */
	public List<Panelist> getMickPanelistList() {
		List<Panelist> list = null;
		
		// Checks if is null.
		if (mPanelistList == null) {
			PanelistRepo repo = new PanelistRepo(mContext);
			repo.open();
			list = repo.getAll();
			repo.close();
			mPanelistList = list;
		} else {
			list = mPanelistList;
		}
		return list;
	}
	
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
	// Passe
	//----------------------------------------------

	public void _______________PASSE_______________() {}
	
	/**
	 * Gets all {@link Passe}.
	 * 
	 * @return
	 */
	public List<Passe> getMickPasseList() {
		List<Passe> list = null;
		
		// Checks if is null.
		if (mPasseList == null) {
			PasseRepo repo = new PasseRepo(mContext);
			repo.open();
			list = repo.getAll();
			repo.close();
			mPasseList = list;
		} else {
			list = mPasseList;
		}
		return list;
	}
	
	/**
	 * Gets the {@link Passe} list from cache.
	 * 
	 * @return
	 */
	public List<Passe> getCachedPasseList() {
		return mPasseList;
	}
	
	/**
	 * Gets the {@link Passe} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getPasseList(Notifiable notifiable) {
		PasseAsyncTask task = new PasseAsyncTask(mContext, mDatabaseNeedsUpdate);
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
				// Puts the Passe list in the cache.
				mPasseList = (List<Passe>)result.getEntityList();
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
		if (task instanceof AgendaAsyncTask) {
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
		} else if (task instanceof PasseAsyncTask) {
			return FETCH_TASK.PASSE;
		}
		return -1;
	}
}