package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.services.DatabaseManager;

public class PanelistRepo {
	private final Context Ctx;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	
	public PanelistRepo(Context context) {
		super();
		this.Ctx = context;
	}
	
	public PanelistRepo open() throws SQLException{
		this.mDbManager = DatabaseManager.getInstance(this.Ctx);
		this.mDb = this.mDbManager.getWritableDatabase();
		return this;
	}
	
	public void close(){
		this.mDbManager.close();
	}
	
	// insert 
	public long insertPanelist(Panelist panelist) {
		ContentValues value = new ContentValues();
		value.put("id", panelist.id);
		value.put("name", panelist.name);
		value.put("slug", panelist.slug);
		value.put("description", panelist.description);
		value.put("picture", panelist.picture);
		return this.mDb.insert("panelist", null, value);
	}
	
	// getAll
	public List<Panelist> getAllPanelist() {
		List<Panelist> panelists = new ArrayList<Panelist>();
		Panelist _panelist;
		
		Cursor mCursor = this.mDb.query("panelist", null, null, null, null, null, null);
		
		if(mCursor.getCount() > 0){
			while (mCursor.moveToNext()) {
				_panelist = new Panelist();
				_panelist.id = mCursor.getInt(1);
				_panelist.name = mCursor.getString(2);
				_panelist.slug = mCursor.getString(3);
				_panelist.description = mCursor.getString(4);
				_panelist.picture = mCursor.getString(5);
				panelists.add(_panelist);
			}
		}
		return panelists;
	}
	
	// getAllbyEvent
	public List<Panelist> getAllbyEvent(String listAgenda) {
		List<Panelist> _panelists = new ArrayList<Panelist>();
		Panelist _panelist;
		
		Cursor mCursor = this.mDb.query("panelist", null, "id in ("+listAgenda+")", null, null, null, null);
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_panelist = new Panelist();
				_panelist.id = mCursor.getInt(1);
				_panelist.name = mCursor.getString(2);
				_panelist.slug = mCursor.getString(3);
				_panelist.description = mCursor.getString(4);
				_panelist.picture = mCursor.getString(5);
				_panelists.add(_panelist);
			}
		}
		return _panelists;
	}
	
	// getSingle
	@SuppressLint("NewApi")
	public Panelist getPanelist(long id) throws SQLException {
		Panelist _panelist = new Panelist();
		Cursor mCursor = this.mDb.query("panelist", null, "id = "+id, null, null, null, null);
		
		if(mCursor.getCount() > 0){
			while (mCursor.moveToNext()) {
				_panelist.id = mCursor.getInt(1);
				_panelist.name = mCursor.getString(2);
				_panelist.slug = mCursor.getString(3);
				_panelist.description = mCursor.getString(4);
				_panelist.picture = mCursor.getString(5);
			}
		}
		return _panelist;
	}
	
	// delete
	public boolean delete(long id){
		return this.mDb.delete("panelist", "id = " +id, null) > 0;
	}
	
	// update
	public boolean update(Panelist panelist) {
		ContentValues value = new ContentValues();
		value.put("id", panelist.id);
		value.put("name", panelist.name);
		value.put("slug", panelist.slug);
		value.put("description", panelist.description);
		value.put("picture", panelist.picture);
		return this.mDb.update("panelist", value, "id = " + panelist.id, null) > 0;
	}

	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return this.mDb.delete("panelist", null, null) > 0;
	}
}
