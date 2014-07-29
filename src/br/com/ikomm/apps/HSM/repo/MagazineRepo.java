package br.com.ikomm.apps.HSM.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.services.DatabaseManager;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * MagazineRepo.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class MagazineRepo {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private final Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public MagazineRepo(Context context) {
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
	public MagazineRepo open() throws SQLException{
		mDbManager = DatabaseManager.getInstance(mContext);
		mDb = mDbManager.getWritableDatabase();
		return this;
	}
	
	/**
	 * Closes the database.
	 */
	public void close() {
		mDbManager.close();
	}
	
	/**
	 * Inserts a specific {@link Magazine}.
	 * 
	 * @param magazine
	 * 
	 * @return
	 */
	public long insertMagazine(Magazine magazine) {
		ContentValues value = new ContentValues();
		value.put("id", magazine.id);
		value.put("name", magazine.name);
		value.put("picture", magazine.picture);
		value.put("description", magazine.description);
		value.put("link", magazine.link);
		return mDb.insert("magazine", null, value);
	}
	
	/**
	 * Gets all {@link Magazine}.
	 * 
	 * @return
	 */
	public List<Magazine> getAll() {
		List<Magazine> magazineList = new ArrayList<Magazine>();
		Magazine magazine;
		
		Cursor cursor = mDb.query("magazine", null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				magazine = new Magazine();
				magazine.id = cursor.getInt(1);
				magazine.name = cursor.getString(2);
				magazine.picture = cursor.getString(3);
				magazine.description = cursor.getString(4);
				magazine.link = cursor.getString(5);
				magazineList.add(magazine);
			}
		}
		
		Utils.fileLog("MagazineRepo.getAll() -> " + magazineList.size());
		return magazineList;
	}
	
	/**
	 * Gets a specific {@link Magazine}.
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public Magazine getMagazine(long id) throws SQLException {
		Magazine magazine = new Magazine();
	
		Cursor cursor = mDb.query("magazine", null, "id = "+id, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				magazine.id = cursor.getInt(1);
				magazine.name = cursor.getString(2);
				magazine.picture = cursor.getString(3);
				magazine.description = cursor.getString(4);
				magazine.link = cursor.getString(5);
			}
		}
		return magazine;
	}
	
	/**
	 * Deletes a specific {@link Magazine}.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public boolean delete(long id){
		return mDb.delete("magazine", "id = " +id, null) > 0;
	}
	
	/**
	 * Updates a specific {@link Magazine}.
	 * 
	 * @param magazine
	 * 
	 * @return
	 */
	public boolean update(Magazine magazine) {
		ContentValues value = new ContentValues();
		value.put("id", magazine.id);
		value.put("name", magazine.name);
		value.put("picture", magazine.picture);
		value.put("description", magazine.description);
		value.put("link", magazine.link);
		return mDb.update("magazine", value, "id = " + magazine.id, null) > 0;
	}

	/**
	 * Deletes all {@link Magazine}.
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("magazine", null, null) > 0;
	}	
}
