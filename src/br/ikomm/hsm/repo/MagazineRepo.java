package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Magazine;
import br.ikomm.hsm.services.DatabaseManager;

public class MagazineRepo {
	private final Context Ctx;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	
	public MagazineRepo(Context context) {
		super();
		this.Ctx = context;
	}
	
	public MagazineRepo open() throws SQLException{
		this.mDbManager = DatabaseManager.getInstance(this.Ctx);
		this.mDb = this.mDbManager.getWritableDatabase();
		return this;
	}
	
	public void close(){
		this.mDbManager.close();
	}
	
	// insert 
	public long insertMagazine(Magazine magazine) {
		ContentValues value = new ContentValues();
		value.put("id", magazine.id);
		value.put("name", magazine.name);
		value.put("picture", magazine.picture);
		value.put("description", magazine.description);
		value.put("link", magazine.link);
		return this.mDb.insert("magazine", null, value);
	}
	
	// getAll
	public List<Magazine> getAllMagazine() {
		List<Magazine> _magazines = new ArrayList<Magazine>();
		Magazine _magazine;
		Cursor mCursor = this.mDb.query("magazine", null, null, null, null, null, null);
		
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_magazine = new Magazine();
				_magazine.id = mCursor.getInt(1);
				_magazine.name = mCursor.getString(2);
				_magazine.picture = mCursor.getString(3);
				_magazine.description = mCursor.getString(4);
				_magazine.link = mCursor.getString(5);
				_magazines.add(_magazine);
			}
		}
		
		return _magazines;
	}
	
	// getSingle
	@SuppressLint("NewApi")
	public Magazine getMagazine(long id) throws SQLException {
		Cursor mCursor = this.mDb.query("magazine", null, "id = "+id, null, null, null, null);
		Magazine _magazine = new Magazine();
	
		if(mCursor.getCount() > 0){
			while (mCursor.moveToNext()) {
				_magazine.id = mCursor.getInt(1);
				_magazine.name = mCursor.getString(2);
				_magazine.picture = mCursor.getString(3);
				_magazine.description = mCursor.getString(4);
				_magazine.link = mCursor.getString(5);
			}
		}
		
		return _magazine;
	}
	
	// delete
	public boolean delete(long id){
		return this.mDb.delete("magazine", "id = " +id, null) > 0;
	}
	
	// update
	public boolean update(Magazine magazine) {
		ContentValues value = new ContentValues();
		value.put("id", magazine.id);
		value.put("name", magazine.name);
		value.put("picture", magazine.picture);
		value.put("description", magazine.description);
		value.put("link", magazine.link);
		return this.mDb.update("magazine", value, "id = " + magazine.id, null) > 0;
	}

	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return this.mDb.delete("magazine", null, null) > 0;
	}	
}
