package br.ikomm.hsm.model;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.services.DatabaseManager;

@SuppressLint("NewApi")
public class EventRepo {
	private final Context Ctx;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	
	public EventRepo(Context context) {
		super();
		this.Ctx = context;
	}
	
	public EventRepo open() throws SQLException{
		this.mDbManager = DatabaseManager.getInstance(this.Ctx);
		this.mDb = this.mDbManager.getWritableDatabase();
		return this;
	}
	
	public void close(){
		this.mDbManager.close();
	}
	
	// insert 
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
		return this.mDb.insert("event", null, value);
	}
	
	// getAll
	public List<Event> getAllEvent() {
		List<Event> events = new ArrayList<Event>();
		Event _event;
		Cursor mCursor = this.mDb.query("event", null, null, null, null, null, null);
		
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
				events.add(_event);
			}
		}
		return events;
	}
	
	// getSingle
	public Event getEvent(long id) throws SQLException {
		Event _event = new Event();
		Cursor mCursor = this.mDb.query(true, "event", null, "id = " + id, null, null, null, null, null, null);
		
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
			}
		}
		 return _event;
	}
	
	// delete
	public boolean delete(long id){
		return this.mDb.delete("event", "id = " +id, null) > 0;
	}
	
	// update
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
		return this.mDb.update("event", value, "id = " + event.id, null) > 0;
	}

	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return this.mDb.delete("event", null, null) > 0;
	}
}
