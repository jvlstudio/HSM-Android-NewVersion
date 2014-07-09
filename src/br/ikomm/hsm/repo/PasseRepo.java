package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.services.DatabaseManager;

public class PasseRepo {
	private final Context Ctx;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	
	public PasseRepo(Context context) {
		super();
		this.Ctx = context;
	}
	
	public PasseRepo open() throws SQLException{
		this.mDbManager = DatabaseManager.getInstance(this.Ctx);
		this.mDb = this.mDbManager.getWritableDatabase();
		return this;
	}
	
	public void close(){
		this.mDbManager.close();
	}
	
	// insert 
	public long insertPasse(Passe passe) {
		ContentValues value = new ContentValues();
		value.put("id", passe.id);
		value.put("event_id", passe.event_id);
		value.put("event_name", passe.event_name);
		value.put("event_slug", passe.event_slug);
		value.put("color", passe.color);
		value.put("name", passe.name);
		value.put("slug", passe.slug);
		value.put("price_from", passe.price_from);
		value.put("price_to", passe.price_to);
		value.put("valid_to", passe.valid_to);
		value.put("email", passe.email);
		value.put("description", passe.description);
		value.put("days", passe.days);
		value.put("mDates", passe.dates);
		value.put("show_dates", passe.show_dates);
		value.put("is_multiple", passe.is_multiple);
		return this.mDb.insert("passe", null, value);
	}
	
	// getAll
	public List<Passe> getAllPasse() {
		List<Passe> passes = new ArrayList<Passe>();
		Passe _passe;
		
		Cursor mCursor = this.mDb.query("passe", null, null, null, null, null, null);
		
		if(mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_passe = new Passe();
				_passe.id = mCursor.getInt(1);
				_passe.event_id = mCursor.getInt(2);
				_passe.event_name = mCursor.getString(3);
				_passe.event_slug = mCursor.getString(4);
				_passe.color = mCursor.getString(5);
				_passe.name = mCursor.getString(6);
				_passe.slug = mCursor.getString(7);
				_passe.price_from = mCursor.getString(8);
				_passe.price_to = mCursor.getString(9);
				_passe.valid_to = mCursor.getString(10);
				_passe.email = mCursor.getString(11);
				_passe.description = mCursor.getString(12);
				_passe.days = mCursor.getString(13);
				_passe.dates = mCursor.getString(14);
				_passe.show_dates = mCursor.getString(15);
				_passe.is_multiple = mCursor.getString(16);
				passes.add(_passe);
			}
		}
		
		return passes;
	}
	
	// byEvent
	public List<Passe> byEvent(long event_id) {
		List<Passe> passes = new ArrayList<Passe>();
		Passe _passe;
			
		Cursor mCursor = this.mDb.query("passe", null, "event_id = "+event_id, null, null, null, null);
			
		if(mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_passe = new Passe();
				_passe.id = mCursor.getInt(1);
				_passe.event_id = mCursor.getInt(2);
				_passe.event_name = mCursor.getString(3);
				_passe.event_slug = mCursor.getString(4);
				_passe.color = mCursor.getString(5);
				_passe.name = mCursor.getString(6);
				_passe.slug = mCursor.getString(7);
				_passe.price_from = mCursor.getString(8);
				_passe.price_to = mCursor.getString(9);
				_passe.valid_to = mCursor.getString(10);
				_passe.email = mCursor.getString(11);
				_passe.description = mCursor.getString(12);
				_passe.days = mCursor.getString(13);
				_passe.dates = mCursor.getString(14);
				_passe.show_dates = mCursor.getString(15);
				_passe.is_multiple = mCursor.getString(16);
				passes.add(_passe);
			}
		}
			
		return passes;
	}
	
	// getSingle
	public Passe getPasse(long id) throws SQLException {
		Passe _passe = new Passe();
		
		Cursor mCursor = this.mDb.query("passe", null, "id = " +id, null, null, null, null);
		
		if(mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_passe.id = mCursor.getInt(1);
				_passe.event_id = mCursor.getInt(2);
				_passe.event_name = mCursor.getString(3);
				_passe.event_slug = mCursor.getString(4);
				_passe.color = mCursor.getString(5);
				_passe.name = mCursor.getString(6);
				_passe.slug = mCursor.getString(7);
				_passe.price_from = mCursor.getString(8);
				_passe.price_to = mCursor.getString(9);
				_passe.valid_to = mCursor.getString(10);
				_passe.email = mCursor.getString(11);
				_passe.description = mCursor.getString(12);
				_passe.days = mCursor.getString(13);
				_passe.dates = mCursor.getString(14);
				_passe.show_dates = mCursor.getString(15);
				_passe.is_multiple = mCursor.getString(16);
			}
		}
		return _passe;
	}
	
	// delete
	public boolean delete(long id){
		return this.mDb.delete("passe", "id = " +id, null) > 0;
	}
	
	// update
	public boolean update(Passe passe) {
		ContentValues value = new ContentValues();
		value.put("id", passe.id);
		value.put("event_id", passe.event_id);
		value.put("event_name", passe.event_name);
		value.put("event_slug", passe.event_slug);
		value.put("color", passe.color);
		value.put("name", passe.name);
		value.put("slug", passe.slug);
		value.put("price_from", passe.price_from);
		value.put("price_to", passe.price_to);
		value.put("valid_to", passe.valid_to);
		value.put("email", passe.email);
		value.put("description", passe.description);
		value.put("days", passe.days);
		value.put("mDates", passe.dates);
		value.put("show_dates", passe.show_dates);
		value.put("is_multiple", passe.is_multiple);
		return this.mDb.update("passe", value, "id = " + passe.id, null) > 0;
	}

	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return this.mDb.delete("passe", null, null) > 0;
	}
}
