package br.com.ikomm.apps.HSM.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.services.DatabaseManager;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo Cericatto at July 7, 2014.
 */
@SuppressLint("NewApi")
public class EventRepo {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private final Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public EventRepo(Context context) {
		super();
		mContext = context;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Opens the database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public EventRepo open() throws SQLException{
		mDbManager = DatabaseManager.getInstance(mContext);
		mDb = mDbManager.getWritableDatabase();
		return this;
	}
	
	/**
	 * Closes the database.
	 */
	public void close(){
		mDbManager.close();
	}
	
	/**
	 * Insert a specific {@link Event}.
	 * 
	 * @param event
	 * @return
	 */
	public long insertEvent(Event event) {
		ContentValues value = new ContentValues();
		value.put("id", event.id);
		value.put("name", event.name);
		value.put("slug", event.slug);
		value.put("description", event.description);
		value.put("tiny_description", event.tiny_description);
		value.put("info_dates", event.info_dates);
		value.put("info_hours", event.info_hours);
		value.put("info_locale", event.info_locale);
		value.put("image_list", event.image_list);
		value.put("image_single", event.image_single);
		return mDb.insert("event", null, value);
	}
	
	/**
	 * Gets all {@link Event}.
	 * 
	 * @return
	 */
	public List<Event> getAll() {
		List<Event> eventList = new ArrayList<Event>();
		Event event;
		Cursor cursor = mDb.query("event", null, null, null, null, null, null);
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				event = new Event();
				event.id = cursor.getInt(1);
				event.name = cursor.getString(2);
				event.slug = cursor.getString(3);
				event.description = cursor.getString(4);
				event.tiny_description = cursor.getString(5);
				event.info_dates = cursor.getString(6);
				event.info_hours = cursor.getString(7);
				event.info_locale = cursor.getString(8);
				event.image_list = cursor.getString(9);
				event.image_single = cursor.getString(10);
				eventList.add(event);
			}
		}
		
		Utils.fileLog("EventRepo.getAll() -> " + eventList.size());
		return eventList;
	}
	
	/**
	 * Gets a specific {@link Event}.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Event getEvent(long id) throws SQLException {
		Event event = new Event();
		Cursor cursor = mDb.query(true, "event", null, "id = " + id, null, null, null, null, null, null);
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				event.id = cursor.getInt(1);
				event.name = cursor.getString(2);
				event.slug = cursor.getString(3);
				event.description = cursor.getString(4);
				event.tiny_description = cursor.getString(5);
				event.info_dates = cursor.getString(6);
				event.info_hours = cursor.getString(7);
				event.info_locale = cursor.getString(8);
				event.image_list = cursor.getString(9);
				event.image_single = cursor.getString(10);
			}
		}
		return event;
	}
	
	/**
	 * Delete a specific {@link Event}.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public boolean delete(long id) {
		return mDb.delete("event", "id = " + id, null) > 0;
	}
	
	/**
	 * Update a specific {@link Event}.
	 * 
	 * @param event
	 * 
	 * @return
	 */
	public boolean update(Event event) {
		ContentValues value = new ContentValues();
		value.put("id", event.id);
		value.put("name", event.name);
		value.put("slug", event.slug);
		value.put("description", event.description);
		value.put("tiny_description", event.tiny_description);
		value.put("info_dates", event.info_dates);
		value.put("info_hours", event.info_hours);
		value.put("info_locale", event.info_locale);
		value.put("image_list", event.image_list);
		value.put("image_single", event.image_single);
		return mDb.update("event", value, "id = " + event.id, null) > 0;
	}

	/**
	 * Delete all {@link Event}.
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("event", null, null) > 0;
	}
}