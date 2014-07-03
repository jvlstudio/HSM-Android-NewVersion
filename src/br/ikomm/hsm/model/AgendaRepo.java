package br.ikomm.hsm.model;

import java.util.ArrayList;
import java.util.List;

import br.ikomm.hsm.services.DatabaseManager;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

@SuppressLint("NewApi")
public class AgendaRepo {

	private final Context Ctx;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	
	public AgendaRepo(Context context) {
		super();
		this.Ctx = context;
	}
	
	public AgendaRepo open() throws SQLException{
		this.mDbManager = DatabaseManager.getInstance(this.Ctx);
		this.mDb = this.mDbManager.getWritableDatabase();
		return this;
	}
	
	public void close(){
		this.mDbManager.close();
	}
	
	// insert 
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
		return this.mDb.insert("agenda", null, value);
	}
	
	// getAll
	public List<Agenda> getAllAgenda() {
		List<Agenda> agendas = new ArrayList<Agenda>();
		Agenda _agenda;
		Cursor mCursor = this.mDb.query("agenda", null, null, null, null, null, null); 
		
		if (mCursor.getCount() > 0){
			while (mCursor.moveToNext()) {
				_agenda = new Agenda();
				_agenda.id = mCursor.getInt(1);
				_agenda.type = mCursor.getString(2);
				_agenda.event_id = mCursor.getInt(3);
				_agenda.panelist_id = mCursor.getInt(4);
				_agenda.date = mCursor.getInt(5);
				_agenda.date_start = mCursor.getString(6);
				_agenda.date_end = mCursor.getString(7);
				_agenda.theme_title = mCursor.getString(8);
				_agenda.theme_description = mCursor.getString(9);
				_agenda.label = mCursor.getString(10);
				_agenda.sublabel = mCursor.getString(11);
				_agenda.image = mCursor.getString(12);
				agendas.add(_agenda);
			}
		}
		
		return agendas;
	}
	
	// getSingle
	public Agenda getAgenda(long id) throws SQLException {
		Agenda _agenda = new Agenda();
		
		Cursor mCursor = this.mDb.query(true, "agenda", null, "id = " + id, null, null, null, null, null, null);
		if(mCursor.getCount() > 0){
			while (mCursor.moveToNext()) {
				_agenda = new Agenda();
				_agenda.id = mCursor.getInt(1);
				_agenda.type = mCursor.getString(2);
				_agenda.event_id = mCursor.getInt(3);
				_agenda.panelist_id = mCursor.getInt(4);
				_agenda.date = mCursor.getInt(5);
				_agenda.date_start = mCursor.getString(6);
				_agenda.date_end = mCursor.getString(7);
				_agenda.theme_title = mCursor.getString(8);
				_agenda.theme_description = mCursor.getString(9);
				_agenda.label = mCursor.getString(10);
				_agenda.sublabel = mCursor.getString(11);
				_agenda.image = mCursor.getString(12);
			}
		}
		
		return _agenda;
	}
	
	// delete
	public boolean delete(long id){
		return this.mDb.delete("agenda", "id = " +id, null) > 0;
	}
	
	// update
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
		return this.mDb.update("agenda", value, "id = " + agenda.id, null) > 0;
	}

	// byEvent
	public List<Agenda> byEvent(long event_id) throws SQLException {
		List<Agenda> agendas = new ArrayList<Agenda>();
		Agenda _agenda;
		
		Cursor mCursor = this.mDb.query(true, "agenda", null, "event_id = " + event_id, null, null, null, null, null, null); 
		if(mCursor.getCount() > 0){
			while (mCursor.moveToNext()) {
				_agenda = new Agenda();
				_agenda.id = mCursor.getInt(1);
				_agenda.type = mCursor.getString(2);
				_agenda.event_id = mCursor.getInt(3);
				_agenda.panelist_id = mCursor.getInt(4);
				_agenda.date = mCursor.getInt(5);
				_agenda.date_start = mCursor.getString(6);
				_agenda.date_end = mCursor.getString(7);
				_agenda.theme_title = mCursor.getString(8);
				_agenda.theme_description = mCursor.getString(9);
				_agenda.label = mCursor.getString(10);
				_agenda.sublabel = mCursor.getString(11);
				_agenda.image = mCursor.getString(12);
				agendas.add(_agenda);
			}
		}

		return  agendas;
	}
	
	// byEvent
	public List<Agenda> byEventAndDate(long event_id, String currentDate) throws SQLException {
		List<Agenda> agendas = new ArrayList<Agenda>();
		Agenda _agenda;
		
		Cursor mCursor = this.mDb.query(true, "agenda", null, "event_id = " + event_id, null, null, null, null, null, null); 
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				// Adds to the correct date tab.
				String dateStart[] = mCursor.getString(6).split(" ");
				String dateFromDatabase = dateStart[0];
				if (currentDate.equals(dateFromDatabase)) {
					_agenda = new Agenda();
					_agenda.id = mCursor.getInt(1);
					_agenda.type = mCursor.getString(2);
					_agenda.event_id = mCursor.getInt(3);
					_agenda.panelist_id = mCursor.getInt(4);
					_agenda.date = mCursor.getInt(5);
					_agenda.date_start = mCursor.getString(6);
					_agenda.date_end = mCursor.getString(7);
					_agenda.theme_title = mCursor.getString(8);
					_agenda.theme_description = mCursor.getString(9);
					_agenda.label = mCursor.getString(10);
					_agenda.sublabel = mCursor.getString(11);
					_agenda.image = mCursor.getString(12);
					agendas.add(_agenda);
				}
			}
		}

		return  agendas;
	}
	
	// detailAgenda
	public Agenda detailAgenda(long panelist_id, long event_id) throws SQLException {
		Agenda _agenda = new Agenda();
		
		Cursor mCursor = this.mDb.query(true, "agenda", null, "panelist_id = " + panelist_id + " AND  event_id = " + event_id, null, null, null, null, null, null); 
		if(mCursor.getCount() > 0){
			while (mCursor.moveToNext()) {
				_agenda.id = mCursor.getInt(1);
				_agenda.type = mCursor.getString(2);
				_agenda.event_id = mCursor.getInt(3);
				_agenda.panelist_id = mCursor.getInt(4);
				_agenda.date = mCursor.getInt(5);
				_agenda.date_start = mCursor.getString(6);
				_agenda.date_end = mCursor.getString(7);
				_agenda.theme_title = mCursor.getString(8);
				_agenda.theme_description = mCursor.getString(9);
				_agenda.label = mCursor.getString(10);
				_agenda.sublabel = mCursor.getString(11);
				_agenda.image = mCursor.getString(12);
			}
		}

		return  _agenda;
	}

	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return this.mDb.delete("agenda", null, null) > 0;
	}
	
	public void specialUpdate() {
		List<Agenda> list = getAllAgenda();
		
		// Updates the Agenda list.
		Agenda agenda = list.get(3);
		agenda.date_start = "2014-08-23 10:00:00";
		agenda.date_end = "2014-08-23 12:00:00";
		agenda.id = 27;
        update(agenda);
		
        agenda = list.get(4);
		agenda.date_start = "2014-08-23 12:00:00";
		agenda.date_end = "2014-08-23 14:00:00";
		agenda.id = 28;
        update(agenda);
        
        agenda = list.get(5);
		agenda.date_start = "2014-08-23 14:00:00";
		agenda.date_end = "2014-08-23 16:00:00";
		agenda.id = 28;
        update(agenda);
	}
}
