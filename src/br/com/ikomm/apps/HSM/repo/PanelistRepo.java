package br.com.ikomm.apps.HSM.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.services.DatabaseManager;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * PanelistRepo.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class PanelistRepo {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public PanelistRepo(Context context) {
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
	public PanelistRepo open() throws SQLException{
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
	 * Inserts a specific {@link Panelist}.
	 * 
	 * @param panelist
	 * 
	 * @return
	 */
	public long insertPanelist(Panelist panelist) {
		ContentValues value = new ContentValues();
		value.put("id", panelist.id);
		value.put("name", panelist.name);
		value.put("slug", panelist.slug);
		value.put("description", panelist.description);
		value.put("picture", panelist.picture);
		return mDb.insert("panelist", null, value);
	}
	
	/**
	 * Gets all {@link Panelist}.
	 * 
	 * @return
	 */
	public List<Panelist> getAll() {
		List<Panelist> panelistList = new ArrayList<Panelist>();
		Panelist panelist;
		
		Cursor cursor = mDb.query("panelist", null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				panelist = new Panelist();
				panelist.id = cursor.getInt(1);
				panelist.name = cursor.getString(2);
				panelist.slug = cursor.getString(3);
				panelist.description = cursor.getString(4);
				panelist.picture = cursor.getString(5);
				panelistList.add(panelist);
			}
		}
		
		Utils.fileLog("PanelistRepo.getAll() -> " + panelistList.size());
		return panelistList;
	}
	
	/**
	 * Gets all {@link Panelist} from an {@link Event}.
	 * 
	 * @param listAgenda
	 * 
	 * @return
	 */
	public List<Panelist> getAllbyEvent(String listAgenda) {
		List<Panelist> panelistList = new ArrayList<Panelist>();
		Panelist panelist;
		
		Cursor cursor = mDb.query("panelist", null, "id in (" + listAgenda + ")", null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				panelist = new Panelist();
				panelist.id = cursor.getInt(1);
				panelist.name = cursor.getString(2);
				panelist.slug = cursor.getString(3);
				panelist.description = cursor.getString(4);
				panelist.picture = cursor.getString(5);
				panelistList.add(panelist);
			}
		}
		return panelistList;
	}
	
	/**
	 * Gets a specific {@link Panelist}.
	 *  
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public Panelist getPanelist(long id) throws SQLException {
		Panelist panelist = new Panelist();
		
		Cursor cursor = mDb.query("panelist", null, "id = "+id, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				panelist.id = cursor.getInt(1);
				panelist.name = cursor.getString(2);
				panelist.slug = cursor.getString(3);
				panelist.description = cursor.getString(4);
				panelist.picture = cursor.getString(5);
			}
		}
		return panelist;
	}
	
	/**
	 * Deletes a specific {@link Panelist}.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public boolean delete(long id){
		return mDb.delete("panelist", "id = " + id, null) > 0;
	}
	
	/**
	 * Updates a specific {@link Panelist}.
	 * 
	 * @param panelist
	 * 
	 * @return
	 */
	public boolean update(Panelist panelist) {
		ContentValues value = new ContentValues();
		value.put("id", panelist.id);
		value.put("name", panelist.name);
		value.put("slug", panelist.slug);
		value.put("description", panelist.description);
		value.put("picture", panelist.picture);
		return mDb.update("panelist", value, "id = " + panelist.id, null) > 0;
	}

	/**
	 * Deletes all {@link Panelist}.
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("panelist", null, null) > 0;
	}
}