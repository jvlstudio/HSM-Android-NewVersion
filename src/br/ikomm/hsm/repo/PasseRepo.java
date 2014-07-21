package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.services.DatabaseManager;

/**
 * PasseRepo.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class PasseRepo {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private final Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public PasseRepo(Context context) {
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
	 * 
	 * @throws SQLException
	 */
	public PasseRepo open() throws SQLException{
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
	 * Inserts a specific {@link Passe}.
	 * 
	 * @param passe
	 * 
	 * @return
	 */
	public long insertPasse(Passe passe) {
		ContentValues value = new ContentValues();
		value.put("id", passe.id);
		value.put("event_id", passe.event_id);
		value.put("color", passe.color);
		value.put("name", passe.name);
		value.put("slug", passe.slug);
		value.put("price_from", passe.price_from);
		value.put("price_to", passe.price_to);
		value.put("valid_to", passe.valid_to);
		value.put("email", passe.email);
		value.put("description", passe.description);
		value.put("days", passe.days);
		value.put("show_dates", passe.show_dates);
		value.put("is_multiple", passe.is_multiple);
		return mDb.insert("passe", null, value);
	}
	
	/**
	 * Get all {@link Passe}.
	 * 
	 * @return
	 */
	public List<Passe> getAllPasse() {
		List<Passe> passeList = new ArrayList<Passe>();
		Passe passe;
		
		Cursor cursor = mDb.query("passe", null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				passe = new Passe();
				passe.id = cursor.getInt(1);
				passe.event_id = cursor.getInt(2);
				passe.color = cursor.getString(3);
				passe.name = cursor.getString(4);
				passe.slug = cursor.getString(5);
				passe.price_from = cursor.getString(6);
				passe.price_to = cursor.getString(7);
				passe.valid_to = cursor.getString(8);
				passe.email = cursor.getString(9);
				passe.description = cursor.getString(10);
				passe.days = cursor.getString(11);
				passe.show_dates = cursor.getString(12);
				passe.is_multiple = cursor.getString(13);
				passeList.add(passe);
			}
		}
		return passeList;
	}
	
	/**
	 * Gets a specific {@link Passe} from an {@link Event}.
	 * 
	 * @param eventId
	 * 
	 * @return
	 */
	public List<Passe> byEvent(long eventId) {
		List<Passe> passeList = new ArrayList<Passe>();
		Passe passe;
			
		Cursor cursor = mDb.query("passe", null, "event_id = " + eventId, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				passe = new Passe();
				passe.id = cursor.getInt(1);
				passe.event_id = cursor.getInt(2);
				passe.color = cursor.getString(3);
				passe.name = cursor.getString(4);
				passe.slug = cursor.getString(5);
				passe.price_from = cursor.getString(6);
				passe.price_to = cursor.getString(7);
				passe.valid_to = cursor.getString(8);
				passe.email = cursor.getString(9);
				passe.description = cursor.getString(10);
				passe.days = cursor.getString(11);
				passe.show_dates = cursor.getString(12);
				passe.is_multiple = cursor.getString(13);
				passeList.add(passe);
			}
		}
		return passeList;
	}
	
	/**
	 * Gets a specific {@link Passe}.
	 *  
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Passe getPasse(long id) throws SQLException {
		Passe passe = new Passe();
		
		Cursor cursor = mDb.query("passe", null, "id = " + id, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				passe.id = cursor.getInt(1);
				passe.event_id = cursor.getInt(2);
				passe.color = cursor.getString(3);
				passe.name = cursor.getString(4);
				passe.slug = cursor.getString(5);
				passe.price_from = cursor.getString(6);
				passe.price_to = cursor.getString(7);
				passe.valid_to = cursor.getString(8);
				passe.email = cursor.getString(9);
				passe.description = cursor.getString(10);
				passe.days = cursor.getString(11);
				passe.show_dates = cursor.getString(12);
				passe.is_multiple = cursor.getString(13);
			}
		}
		return passe;
	}
	
	/**
	 * Deletes a specific {@link Passe}.
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(long id){
		return mDb.delete("passe", "id = " + id, null) > 0;
	}
	
	/**
	 * Updates a specific {@link Passe}.
	 * 
	 * @param passe
	 * 
	 * @return
	 */
	public boolean update(Passe passe) {
		ContentValues value = new ContentValues();
		value.put("id", passe.id);
		value.put("event_id", passe.event_id);
		value.put("color", passe.color);
		value.put("name", passe.name);
		value.put("slug", passe.slug);
		value.put("price_from", passe.price_from);
		value.put("price_to", passe.price_to);
		value.put("valid_to", passe.valid_to);
		value.put("email", passe.email);
		value.put("description", passe.description);
		value.put("days", passe.days);
		value.put("show_dates", passe.show_dates);
		value.put("is_multiple", passe.is_multiple);
		return mDb.update("passe", value, "id = " + passe.id, null) > 0;
	}

	/**
	 * Deletes all {@link Passe}.
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("passe", null, null) > 0;
	}
}