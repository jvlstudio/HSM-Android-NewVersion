package br.com.ikomm.apps.HSM.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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
	
	// The singleton instance.
	private static ContentManager sInstance = null;
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------

	// The application context.
	private Context mContext;
	
	// Cached list values.
	private List<Agenda> mAgendaList = new ArrayList<Agenda>();
	private List<Book> mBookList = new ArrayList<Book>();
	private List<Event> mEventList = new ArrayList<Event>();
	private List<Home> mHomeList = new ArrayList<Home>();
	private List<Magazine> mMagazineList = new ArrayList<Magazine>();
	private List<Panelist> mPanelistList = new ArrayList<Panelist>();
	private List<Passe> mPasseList = new ArrayList<Passe>();
	
	// Bitmap values.
	private List<Bitmap> mLinearLayoutBitmapList = new ArrayList<Bitmap>();
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
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
	// Methods
	//----------------------------------------------
	
	/**
	 * Sets the application context.
	 * 
	 * @param context The context to be used.
	 */
	public void setContext(Context context) {
		mContext = context;
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
	// Bitmap
	//----------------------------------------------
	
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

	/**
	 * Gets all {@link Agenda}.
	 * 
	 * @return
	 */
	public List<Agenda> getAllAgenda() {
		List<Agenda> list = null;
		
		// Checks if is null.
		if (mAgendaList == null) {
			AgendaRepo repo = new AgendaRepo(mContext);
			repo.open();
			list = repo.getAllAgenda();
			repo.close();
			mAgendaList = list;
		} else {
			list = mAgendaList;
		}
		return list;
	}
	
	//----------------------------------------------
	// Book
	//----------------------------------------------

	/**
	 * Gets all {@link Book}.
	 * 
	 * @return
	 */
	public List<Book> getAllBook() {
		List<Book> list = null;
		
		// Checks if is null.
		if (mBookList == null) {
			BookRepo repo = new BookRepo(mContext);
			repo.open();
			list = repo.getAllBook();
			repo.close();
			mBookList = list;
		} else {
			list = mBookList;
		}
		return list;
	}
	
	//----------------------------------------------
	// Event
	//----------------------------------------------

	/**
	 * Gets all {@link Event}.
	 * 
	 * @return
	 */
	public List<Event> getAllEvent() {
		List<Event> list = null;
		
		// Checks if is null.
		if (mEventList == null) {
			EventRepo repo = new EventRepo(mContext);
			repo.open();
			list = repo.getAllEvent();
			repo.close();
			mEventList = list;
		} else {
			list = mEventList;
		}
		return list;
	}
	
	//----------------------------------------------
	// Home
	//----------------------------------------------

	/**
	 * Gets all {@link Home}.
	 * 
	 * @return
	 */
	public List<Home> getAllHome() {
		List<Home> list = null;
		
		// Checks if is null.
		if (mHomeList.size() <= 0 ||  mHomeList == null) {
			HomeRepo repo = new HomeRepo(mContext);
			repo.open();
			list = repo.getAllHome();
			repo.close();
			mHomeList = list;
		} else {
			list = mHomeList;
		}
		return list;
	}
	
	//----------------------------------------------
	// Magazine
	//----------------------------------------------

	/**
	 * Gets all {@link Magazine}.
	 * 
	 * @return
	 */
	public List<Magazine> getAllMagazine() {
		List<Magazine> list = null;
		
		// Checks if is null.
		if (mMagazineList == null) {
			MagazineRepo repo = new MagazineRepo(mContext);
			repo.open();
			list = repo.getAllMagazine();
			repo.close();
			mMagazineList = list;
		} else {
			list = mMagazineList;
		}
		return list;
	}
	
	//----------------------------------------------
	// Panelist
	//----------------------------------------------

	/**
	 * Gets all {@link Panelist}.
	 * 
	 * @return
	 */
	public List<Panelist> getAllPanelist() {
		List<Panelist> list = null;
		
		// Checks if is null.
		if (mPanelistList == null) {
			PanelistRepo repo = new PanelistRepo(mContext);
			repo.open();
			list = repo.getAllPanelist();
			repo.close();
			mPanelistList = list;
		} else {
			list = mPanelistList;
		}
		return list;
	}
	
	//----------------------------------------------
	// Passe
	//----------------------------------------------

	/**
	 * Gets all {@link Passe}.
	 * 
	 * @return
	 */
	public List<Passe> getAllPasse() {
		List<Passe> list = null;
		
		// Checks if is null.
		if (mPasseList == null) {
			PasseRepo repo = new PasseRepo(mContext);
			repo.open();
			list = repo.getAllPasse();
			repo.close();
			mPasseList = list;
		} else {
			list = mPasseList;
		}
		return list;
	}
}