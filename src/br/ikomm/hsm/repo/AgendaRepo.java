package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.services.DatabaseManager;

/**
 * AgendaRepo.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class AgendaRepo {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public AgendaRepo(Context context) {
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
	public AgendaRepo open() throws SQLException{
		mDbManager = DatabaseManager.getInstance(mContext);
		mDb = mDbManager.getWritableDatabase();
		return this;
	}
	
	/**
	 * Closes the database
	 */
	public void close(){
		mDbManager.close();
	}
	
	/**
	 * Inserts a specific {@link Agenda}.
	 * 
	 * @param agenda
	 * 
	 * @return
	 */
	public long insertAgenda(Agenda agenda) {
		ContentValues value = new ContentValues();
		value.put("id", agenda.id);
		value.put("type", agenda.type);
		value.put("event_id", agenda.event_id);
		value.put("panelist_id", agenda.panelist_id);
		value.put("date", agenda.date);
		value.put("date_start", agenda.date_start);
		value.put("date_end", agenda.date_end);
		value.put("theme_title", agenda.theme_title);
		value.put("theme_description", agenda.theme_description);
		value.put("label", agenda.label);
		value.put("sublabel", agenda.sublabel);
		value.put("image", agenda.image);
		return mDb.insert("agenda", null, value);
	}
	
	/**
	 * Gets all {@link Agenda}.
	 * 
	 * @return
	 */
	public List<Agenda> getAllAgenda() {
		List<Agenda> agendaList = new ArrayList<Agenda>();
		Agenda agenda;
		
		Cursor cursor = mDb.query("agenda", null, null, null, null, null, null); 
		if (cursor.getCount() > 0){
			while (cursor.moveToNext()) {
				agenda = new Agenda();
				agenda.id = cursor.getInt(1);
				agenda.type = cursor.getString(2);
				agenda.event_id = cursor.getInt(3);
				agenda.panelist_id = cursor.getInt(4);
				agenda.date = cursor.getInt(5);
				agenda.date_start = cursor.getString(6);
				agenda.date_end = cursor.getString(7);
				agenda.theme_title = cursor.getString(8);
				agenda.theme_description = cursor.getString(9);
				agenda.label = cursor.getString(10);
				agenda.sublabel = cursor.getString(11);
				agenda.image = cursor.getString(12);
				agendaList.add(agenda);
			}
		}
		return agendaList;
	}
	
	/**
	 * Gets a specific {@link Agenda}.
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public Agenda getAgenda(long id) throws SQLException {
		Agenda agenda = new Agenda();
		
		Cursor cursor = mDb.query(true, "agenda", null, "id = " + id, null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				agenda = new Agenda();
				agenda.id = cursor.getInt(1);
				agenda.type = cursor.getString(2);
				agenda.event_id = cursor.getInt(3);
				agenda.panelist_id = cursor.getInt(4);
				agenda.date = cursor.getInt(5);
				agenda.date_start = cursor.getString(6);
				agenda.date_end = cursor.getString(7);
				agenda.theme_title = cursor.getString(8);
				agenda.theme_description = cursor.getString(9);
				agenda.label = cursor.getString(10);
				agenda.sublabel = cursor.getString(11);
				agenda.image = cursor.getString(12);
			}
		}
		return agenda;
	}
	
	/**
	 * Deletes a specific {@link Agenda}.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public boolean delete(long id){
		return mDb.delete("agenda", "id = " +id, null) > 0;
	}
	
	/**
	 * Updates a specific {@link Agenda}.
	 * 
	 * @param agenda
	 * 
	 * @return
	 */
	public boolean update(Agenda agenda) {
		ContentValues value = new ContentValues();
		value.put("id", agenda.id);
		value.put("type", agenda.type);
		value.put("event_id", agenda.event_id);
		value.put("panelist_id", agenda.panelist_id);
		value.put("date", agenda.date);
		value.put("date_start", agenda.date_start);
		value.put("date_end", agenda.date_end);
		value.put("theme_title", agenda.theme_title);
		value.put("theme_description", agenda.theme_description);
		value.put("label", agenda.label);
		value.put("sublabel", agenda.sublabel);
		value.put("image", agenda.image);
		return mDb.update("agenda", value, "id = " + agenda.id, null) > 0;
	}

	/**
	 * Gets a specific {@link Agenda} from an {@link Event}.
	 * 
	 * @param event_id
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public List<Agenda> byEvent(long event_id) throws SQLException {
		List<Agenda> agendaList = new ArrayList<Agenda>();
		Agenda agenda;
		
		Cursor cursor = mDb.query(true, "agenda", null, "event_id = " + event_id, null, null, null, null, null, null); 
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				agenda = new Agenda();
				agenda.id = cursor.getInt(1);
				agenda.type = cursor.getString(2);
				agenda.event_id = cursor.getInt(3);
				agenda.panelist_id = cursor.getInt(4);
				agenda.date = cursor.getInt(5);
				agenda.date_start = cursor.getString(6);
				agenda.date_end = cursor.getString(7);
				agenda.theme_title = cursor.getString(8);
				agenda.theme_description = cursor.getString(9);
				agenda.label = cursor.getString(10);
				agenda.sublabel = cursor.getString(11);
				agenda.image = cursor.getString(12);
				agendaList.add(agenda);
			}
		}
		return  agendaList;
	}
	
	/**
	 * Gets a specific {@link Agenda} from an {@link Event} and a date.
	 *  
	 * @param event_id
	 * @param currentDate
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public List<Agenda> byEventAndDate(long event_id, String currentDate) throws SQLException {
		List<Agenda> agendaList = new ArrayList<Agenda>();
		Agenda agenda;
		
		Cursor cursor = mDb.query(true, "agenda", null, "event_id = " + event_id, null, null, null, null, null, null); 
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				// Adds to the correct date tab.
				String dateStart[] = cursor.getString(6).split(" ");
				String dateFromDatabase = dateStart[0];
				if (currentDate.equals(dateFromDatabase)) {
					agenda = new Agenda();
					agenda.id = cursor.getInt(1);
					agenda.type = cursor.getString(2);
					agenda.event_id = cursor.getInt(3);
					agenda.panelist_id = cursor.getInt(4);
					agenda.date = cursor.getInt(5);
					agenda.date_start = cursor.getString(6);
					agenda.date_end = cursor.getString(7);
					agenda.theme_title = cursor.getString(8);
					agenda.theme_description = cursor.getString(9);
					agenda.label = cursor.getString(10);
					agenda.sublabel = cursor.getString(11);
					agenda.image = cursor.getString(12);
					agendaList.add(agenda);
				}
			}
		}
		return  agendaList;
	}
	
	/**
	 * Gets a specific {@link Agenda} from an {@link Event} and a {@link Panelist}.
	 * 
	 * @param panelist_id
	 * @param event_id
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public Agenda byEventAndPanelist(long panelist_id, long event_id) throws SQLException {
		Agenda agenda = new Agenda();
		
		Cursor cursor = mDb.query(true, "agenda", null, "panelist_id = " + panelist_id + " AND  event_id = " + event_id, null, null, null, null, null, null); 
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				agenda.id = cursor.getInt(1);
				agenda.type = cursor.getString(2);
				agenda.event_id = cursor.getInt(3);
				agenda.panelist_id = cursor.getInt(4);
				agenda.date = cursor.getInt(5);
				agenda.date_start = cursor.getString(6);
				agenda.date_end = cursor.getString(7);
				agenda.theme_title = cursor.getString(8);
				agenda.theme_description = cursor.getString(9);
				agenda.label = cursor.getString(10);
				agenda.sublabel = cursor.getString(11);
				agenda.image = cursor.getString(12);
			}
		}
		return  agenda;
	}

	/**
	 * Deletes all {@link Agenda}.
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("agenda", null, null) > 0;
	}
}