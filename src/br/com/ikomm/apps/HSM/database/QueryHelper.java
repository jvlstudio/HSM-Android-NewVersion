package br.com.ikomm.apps.HSM.database;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Passe;

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
	
	//--------------------------------------------------
	// Book
	//--------------------------------------------------
	
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
	
	//--------------------------------------------------
	// Event
	//--------------------------------------------------
	
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
	
	//--------------------------------------------------
	// Home
	//--------------------------------------------------
	
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
	
	//--------------------------------------------------
	// Passe
	//--------------------------------------------------
	
	/**
	 * Gets the {@link Passe} list from the database.
	 * 
	 * @param context
	 * @return
	 */
	public static List<Passe> getPasseList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Passe> list = null;
		
		try {
    		// Getting the Panelist list.
    		Dao<Passe, Integer> passeDao = databaseHelper.getDao(Passe.class);
    		list = passeDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return list;
	}
	
	/**
	 * Stores the given {@link Passe} list into the database.
	 * 
	 * @param context
	 * @param list
	 */
	public static void persistPasse(Context context, final List<Passe> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Passe passe : list) {
				// Persisting.
				databaseHelper.createOrUpdate(passe);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Passe.", e);
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