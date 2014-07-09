package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.services.DatabaseManager;

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
		this.mContext = context;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Open database.
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
	 * Close database.
	 */
	public void close(){
		mDbManager.close();
	}
	
	/**
	 * Insert.
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
	 * Get all.
	 * 
	 * @return
	 */
	public List<Event> getAllEvent() {
		List<Event> events = new ArrayList<Event>();
		Event _event;
		Cursor mCursor = mDb.query("event", null, null, null, null, null, null);
		
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_event = new Event();
				_event.id = mCursor.getInt(1);
				_event.name = mCursor.getString(2);
				_event.slug = mCursor.getString(3);
				_event.description = mCursor.getString(4);
				_event.tiny_description = mCursor.getString(5);
				_event.info_dates = mCursor.getString(6);
				_event.info_hours = mCursor.getString(7);
				_event.info_locale = mCursor.getString(8);
				_event.image_list = mCursor.getString(9);
				_event.image_single = mCursor.getString(10);
				events.add(_event);
			}
		}
		return events;
	}
	
	/**
	 * Get single.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Event getEvent(long id) throws SQLException {
		Event _event = new Event();
		Cursor mCursor = mDb.query(true, "event", null, "id = " + id, null, null, null, null, null, null);
		
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_event.id = mCursor.getInt(1);
				_event.name = mCursor.getString(2);
				_event.slug = mCursor.getString(3);
				_event.description = mCursor.getString(4);
				_event.tiny_description = mCursor.getString(5);
				_event.info_dates = mCursor.getString(6);
				_event.info_hours = mCursor.getString(7);
				_event.info_locale = mCursor.getString(8);
				_event.image_list = mCursor.getString(9);
				_event.image_single = mCursor.getString(10);
			}
		}
		return _event;
	}
	
	/**
	 * Delete.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public boolean delete(long id) {
		return mDb.delete("event", "id = " + id, null) > 0;
	}
	
	/**
	 * Update.
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
	 * Delete all.
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("event", null, null) > 0;
	}
}