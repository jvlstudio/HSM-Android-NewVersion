package br.com.ikomm.apps.HSM.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.AppInfo;
import br.com.ikomm.apps.HSM.model.Banner;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Pass;

import com.j256.ormlite.dao.Dao;

/**
 * QueryHelper.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
public class QueryHelper {
	
	//--------------------------------------------------
	// AppInfo
	//--------------------------------------------------
	
	public void _______________APP_INFO_______________() {}
	
	/**
	 * Gets the app info object from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static AppInfo getAppInfo(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		
		try {
			// Creating the dao object.
			Dao<AppInfo, Integer> infoDao = databaseHelper.getDao(AppInfo.class);

			// Getting the info item from the database.
			List<AppInfo> list = infoDao.queryForAll();
			AppInfo info = null;
			if (list.size() != 0 && list != null) {
				info = list.get(list.size() - 1);
			}
			
			// Returns the existent info.
			if (info != null) {
				return info;
			}
			
			// No info found, creating a new one.
			info = new AppInfo();
			info.id = AppInfo.ID;
			info.lastUpdate = 0;
			
			// Updating the info.
			databaseHelper.createOrUpdate(info);
			return info;
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return null;
	}

	/**
	 * Updates the app info last update time.
	 * 
	 * @param context
	 * @param id
	 * @param currentMillis
	 * @return
	 */
	public static boolean updateAppInfoUpdateTime(Context context, Integer id, long currentMillis) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		
		// Getting the existent app info.
		AppInfo info = new AppInfo();
		info.lastUpdate = currentMillis;
		
		// Check if the database needs update.
		Boolean needsUpdate = false;
		if (AppConfiguration.DATABASE_VERSION > id) {
			info.id = AppConfiguration.DATABASE_VERSION;
			needsUpdate = true;
		}
		
		try {
			// Updating.
			databaseHelper.createOrUpdate(info);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return needsUpdate;
	}
	
	//--------------------------------------------------
	// Agenda
	//--------------------------------------------------
	
	public void _______________AGENDA_______________() {}
	
	/**
	 * Gets the {@link Agenda} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Agenda> getAgendaList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Agenda> list = null;
		
		try {
    		// Getting the Home list.
    		Dao<Agenda, Integer> agendaDao = databaseHelper.getDao(Agenda.class);
    		list = agendaDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	/**
	 * Stores the given {@link Agenda} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistAgenda(Context context, final List<Agenda> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Agenda agenda : list) {
				// Persisting.
				databaseHelper.createOrUpdate(agenda);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Agenda.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
	
	/**
	 * Gets an {@link Agenda} by an {@link Event} and a date.  
	 * 
	 * @param eventId
	 * 
	 * @return
	 */
	public static List<Agenda> getAgendaByEvent(Integer eventId) {
		List<Agenda> list = ContentManager.getInstance().getCachedAgendaList();
		List<Agenda> filteredAgenda = new ArrayList<Agenda>();
				
		for (Agenda agenda : list) {
			// Gets conditions.
			Boolean eventIdEqual = (agenda.getEventId() == eventId);
			if (eventIdEqual) {
				filteredAgenda.add(agenda);
			}
		}
		return filteredAgenda;
	}
	
	/**
	 * Gets an {@link Agenda} list by an {@link Event} and a date.  
	 * 
	 * @param eventId
	 * @param currentDate
	 * 
	 * @return
	 */
	public static List<Agenda> getAgendaListByEventAndDate(Integer eventId, String currentDate) {
		List<Agenda> list = ContentManager.getInstance().getCachedAgendaList();
		List<Agenda> filteredList = new ArrayList<Agenda>();
		
		for (Agenda agenda : list) {
			// Gets the right date.
			String dateStart[] = currentDate.split(" ");
			String dateFromDatabase = dateStart[0];
			
			// Gets conditions.
			Boolean dateIsEqual = currentDate.equals(dateFromDatabase);
			Boolean eventIdEqual = (agenda.getEventId() == eventId);
			if (dateIsEqual && eventIdEqual) {
				filteredList.add(agenda);
			}
		}
		return filteredList;
	}
	
	/**
	 * Gets an {@link Agenda} by an {@link Event} and a date.  
	 * 
	 * @param panelistId
	 * @param eventId
	 * 
	 * @return
	 */
	public static Agenda getAgendaByEventAndPanelist(Long panelistId, Integer eventId) {
		List<Agenda> list = ContentManager.getInstance().getCachedAgendaList();
		Agenda filteredAgenda = new Agenda();
		Integer id = Integer.valueOf(panelistId.intValue());
				
		for (Agenda agenda : list) {
			// Gets conditions.
			Boolean eventIdEqual = (agenda.getEventId() == eventId);
			Boolean panelistIdEqual = (agenda.getPanelistId() == id);
			if (eventIdEqual && panelistIdEqual) {
				filteredAgenda = agenda;
			}
		}
		return filteredAgenda;
	}
	
	//--------------------------------------------------
	// Book
	//--------------------------------------------------
	
	public void _______________BOOK_______________() {}
	
	/**
	 * Gets the {@link Book} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Book> getBookList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Book> list = null;
		
		try {
    		// Getting the Home list.
    		Dao<Book, Integer> bookDao = databaseHelper.getDao(Book.class);
    		list = bookDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	
	/**
	 * Stores the given {@link Book} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistBook(Context context, final List<Book> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Book book : list) {
				// Persisting.
				databaseHelper.createOrUpdate(book);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Book.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
	
	/**
	 * Gets an {@link Book} by an id.  
	 * 
	 * @param bookId
	 * 
	 * @return
	 */
	public static Book getBook(Long bookId) {
		List<Book> list = ContentManager.getInstance().getCachedBookList();
		Integer id = Integer.valueOf(bookId.intValue());
		Book currentBook = new Book();
				
		for (Book book : list) {
			// Gets conditions.
			Boolean bookIdEqual = (book.getId() == id);
			if (bookIdEqual) {
				currentBook = book;
			}
		}
		return currentBook;
	}
	
	//--------------------------------------------------
	// Event
	//--------------------------------------------------
	
	public void _______________EVENT_______________() {}
	
	/**
	 * Gets the {@link Event} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Event> getEventList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Event> list = null;
		
		try {
    		// Getting the Event list.
    		Dao<Event, Integer> eventDao = databaseHelper.getDao(Event.class);
    		list = eventDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	
	/**
	 * Stores the given {@link Event} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistEvent(Context context, final List<Event> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Event event : list) {
				// Persisting.
				databaseHelper.createOrUpdate(event);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Event.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
	
	/**
	 * Gets an {@link Event} by an id.  
	 * 
	 * @param eventId
	 * 
	 * @return
	 */
	public static Event getEvent(Long eventId) {
		List<Event> list = ContentManager.getInstance().getCachedEventList();
		Event currentEvent = new Event();
		Integer id = Integer.valueOf(eventId.intValue());
				
		for (Event event : list) {
			// Gets conditions.
			Boolean eventIdEqual = (event.getId() == id);
			if (eventIdEqual) {
				currentEvent = event;
			}
		}
		return currentEvent;
	}
	
	/**
	 * Gets an {@link Event} by an id.  
	 * 
	 * @param eventId
	 * 
	 * @return
	 */
	public static Event getEvent(Integer eventId) {
		List<Event> list = ContentManager.getInstance().getCachedEventList();
		Event currentEvent = new Event();
				
		for (Event event : list) {
			// Gets conditions.
			Boolean eventIdEqual = (event.getId() == eventId);
			if (eventIdEqual) {
				currentEvent = event;
			}
		}
		return currentEvent;
	}
	
	//--------------------------------------------------
	// Home
	//--------------------------------------------------
	
	public void _______________HOME_______________() {}
	
	/**
	 * Gets the {@link Home} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Home> getHomeList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Home> list = null;
		
		try {
    		// Getting the Home list.
    		Dao<Home, Integer> homeDao = databaseHelper.getDao(Home.class);
    		list = homeDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	/**
	 * Stores the given {@link Home} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistHome(Context context, final List<Home> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Home home : list) {
				// Persisting.
				databaseHelper.createOrUpdate(home);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Home.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
	
	//--------------------------------------------------
	// Magazine
	//--------------------------------------------------
	
	public void _______________MAGAZINE_______________() {}
	
	/**
	 * Gets the {@link Magazine} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Magazine> getMagazineList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Magazine> list = null;
		
		try {
    		// Getting the Panelist list.
    		Dao<Magazine, Integer> magazineDao  = databaseHelper.getDao(Magazine.class);
    		list = magazineDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	/**
	 * Stores the given {@link Magazine} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistMagazine(Context context, final List<Magazine> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Magazine magazine : list) {
				// Persisting.
				databaseHelper.createOrUpdate(magazine);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Magazine.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
	
	//--------------------------------------------------
	// Panelist
	//--------------------------------------------------
	
	public void _______________PANELIST_______________() {}
	
	/**
	 * Gets the {@link Panelist} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Panelist> getPanelistList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Panelist> list = null;
		
		try {
    		// Getting the Panelist list.
    		Dao<Panelist, Integer> panelistDao = databaseHelper.getDao(Panelist.class);
    		list = panelistDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	/**
	 * Stores the given {@link Panelist} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistPanelist(Context context, final List<Panelist> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Panelist panelist : list) {
				// Persisting.
				databaseHelper.createOrUpdate(panelist);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Panelist.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
	
	/**
	 * Gets an {@link Panelist} by an id.  
	 * 
	 * @param panelistId
	 * 
	 * @return
	 */
	public static Panelist getPanelist(Long panelistId) {
		List<Panelist> list = ContentManager.getInstance().getCachedPanelistList();
		Panelist currentPanelist = new Panelist();
		Integer id = Integer.valueOf(panelistId.intValue());
				
		for (Panelist panelist : list) {
			// Gets conditions.
			Boolean panelistIdEqual = (panelist.getId() == id);
			if (panelistIdEqual) {
				currentPanelist = panelist;
			}
		}
		return currentPanelist;
	}
	
	/**
	 * Gets an {@link Panelist} by an id.  
	 * 
	 * @param panelistId
	 * 
	 * @return
	 */
	public static Panelist getPanelist(Integer panelistId) {
		List<Panelist> list = ContentManager.getInstance().getCachedPanelistList();
		Panelist currentPanelist = new Panelist();
				
		for (Panelist panelist : list) {
			// Gets conditions.
			Boolean panelistIdEqual = (panelist.getId() == panelistId);
			if (panelistIdEqual) {
				currentPanelist = panelist;
			}
		}
		return currentPanelist;
	}
	
	/**
	 * Gets an {@link Panelist} by an {@link Event}.  
	 * 
	 * @param eventId
	 * 
	 * @return
	 */
	public static List<Panelist> getPanelistListByEvent(Integer eventId) {
		List<Agenda> agendaList = ContentManager.getInstance().getCachedAgendaList();
		List<Integer> idList = new ArrayList<Integer>();
		
		List<Panelist> panelistList = ContentManager.getInstance().getCachedPanelistList();
		List<Panelist> returnList = new ArrayList<Panelist>();

		// Gets all the Panelists Ids.
		for (Agenda agenda : agendaList) {
			if (agenda.getEventId() == eventId) {
				idList.add(agenda.getPanelistId());
			}
		}
		
		// Get the filtered Panelist list.
		for (Panelist panelist : panelistList) {
			Boolean containsPanelistId = containsPanelistId(panelist.getId(), idList);
			if (containsPanelistId) {
				returnList.add(panelist);
			}
		}
		return returnList;
	}
	
	/**
	 * Checks if the {@link Panelist} Id is contained into the Id List.
	 *   
	 * @param panelistId
	 * @param idList
	 * 
	 * @return
	 */
	public static Boolean containsPanelistId(Integer panelistId, List<Integer> idList) {
		for (Integer id : idList) {
			if (id == panelistId) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the {@link Panelist} id contains into the string. 
	 * 
	 * @param panelistId
	 * @param data
	 * @return
	 */
	public static Boolean checkIfContainsPanelistId(Integer panelistId, String data) {
		String trimmed = data.trim();
		String parts[] = trimmed.split(",");
		
		for (int id = 0; id < parts.length; id++) {
			if (panelistId == id) {
				return true;
			}
		}
		return false;
	}
	
	//--------------------------------------------------
	// Pass
	//--------------------------------------------------
	
	public void _______________PASSE_______________() {}
	
	/**
	 * Gets the {@link Pass} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Pass> getPasseList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Pass> list = null;
		
		try {
    		// Getting the Panelist list.
    		Dao<Pass, Integer> passeDao = databaseHelper.getDao(Pass.class);
    		list = passeDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	/**
	 * Stores the given {@link Pass} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistPasse(Context context, final List<Pass> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Pass pass : list) {
				// Persisting.
				databaseHelper.createOrUpdate(pass);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Pass.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
	
	/**
	 * Gets an {@link Pass} by an id.  
	 * 
	 * @param passeId
	 * 
	 * @return
	 */
	public static Pass getPasse(Long passeId) {
		List<Pass> list = ContentManager.getInstance().getCachedPasseList();
		Pass currentPasse = new Pass();
		Integer id = Integer.valueOf(passeId.intValue());
				
		for (Pass pass : list) {
			// Gets conditions.
			Boolean passeIdEqual = (pass.getId() == id);
			if (passeIdEqual) {
				currentPasse = pass;
			}
		}
		return currentPasse;
	}
	
	/**
	 * Gets an {@link Pass} by an {@link Event} and a date.  
	 * 
	 * @param eventId
	 * 
	 * @return
	 */
	public static List<Pass> getPasseListByEvent(Integer eventId) {
		List<Pass> list = ContentManager.getInstance().getCachedPasseList();
		List<Pass> filteredPasse = new ArrayList<Pass>();
				
		for (Pass pass : list) {
			// Gets conditions.
			Boolean eventIdEqual = (pass.getEventId() == eventId);
			if (eventIdEqual) {
				filteredPasse.add(pass);
			}
		}
		return filteredPasse;
	}
	
	//--------------------------------------------------
	// Banner
	//--------------------------------------------------
	
	public void _______________BANNER_______________() {}
	
	/**
	 * Gets the {@link Banner} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Banner> getBannerList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Banner> list = null;
		
		try {
    		// Getting the Banner list.
    		Dao<Banner, Integer> bannerDao  = databaseHelper.getDao(Banner.class);
    		list = bannerDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	/**
	 * Stores the given {@link Banner} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistBanner(Context context, final List<Banner> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Banner banner : list) {
				// Persisting.
				databaseHelper.createOrUpdate(banner);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Banner.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
}