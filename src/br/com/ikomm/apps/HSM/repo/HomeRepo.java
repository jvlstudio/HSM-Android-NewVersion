package br.com.ikomm.apps.HSM.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.services.DatabaseManager;

/**
 * HomeActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class HomeRepo {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private final Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public HomeRepo(Context context) {
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
	public HomeRepo open() throws SQLException{
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
	 * Inserts a specific {@link Home}.
	 *  
	 * @param home
	 * 
	 * @return
	 */
	public long insertHome(Home home) {
		ContentValues value = new ContentValues();
		value.put("id", home.id);
		value.put("events_title", home.events_title);
		value.put("events_image_android", home.events_image_android);
		value.put("education_title", home.education_title);
		value.put("education_image_android", home.education_image_android);
		value.put("videos_title", home.videos_title);
		value.put("videos_image_android", home.videos_image_android);
		value.put("magazines_title", home.magazines_title);
		value.put("magazines_image_android", home.magazines_image_android);
		value.put("books_title", home.books_title);
		value.put("books_image_android", home.books_image_android);
		return mDb.insert("home", null, value);
	}
	
	/**
	 * Gets all {@link Home}.
	 * 
	 * @return
	 */
	public List<Home> getAllHome() {
		List<Home> homeList = new ArrayList<Home>();
		Home home;
		Cursor cursor = mDb.query("home", null, null, null, null, null, null);
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				home = new Home();
				home.id = cursor.getInt(1);
				home.events_title = cursor.getString(2);
				home.events_image_android = cursor.getString(3);
				home.education_title = cursor.getString(4);
				home.education_image_android = cursor.getString(5);
				home.videos_title = cursor.getString(6);
				home.videos_image_android = cursor.getString(7);
				home.magazines_title = cursor.getString(8);
				home.magazines_image_android = cursor.getString(9);
				home.books_title = cursor.getString(10);
				home.books_image_android = cursor.getString(11);
				homeList.add(home);
			}
		}
		return homeList;
	}
	
	/**
	 * Gets a specific {@link Home}.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public Home getHome(long id) throws SQLException {
		Home home = new Home();
		
		Cursor cursor = mDb.query(true, "home", null, "id = " + id, null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				home.id = cursor.getInt(1);
				home.events_title = cursor.getString(2);
				home.events_image_android = cursor.getString(3);
				home.education_title = cursor.getString(4);
				home.education_image_android = cursor.getString(5);
				home.videos_title = cursor.getString(6);
				home.videos_image_android = cursor.getString(7);
				home.magazines_title = cursor.getString(8);
				home.magazines_image_android = cursor.getString(9);
				home.books_title = cursor.getString(10);
				home.books_image_android = cursor.getString(11);
			}
		}
		return home;
	}
	
	/**
	 * Deletes a specific {@link Home}.
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(long id){
		return mDb.delete("home", "id = " +id, null) > 0;
	}
	
	/**
	 * Updates a specific {@link Home}.
	 * 
	 * @param home
	 * @return
	 */
	public boolean update(Home home) {
		ContentValues value = new ContentValues();
		value.put("id", home.id);
		value.put("events_title", home.events_title);
		value.put("events_image_android", home.events_image_android);
		value.put("education_title", home.education_title);
		value.put("education_image_android", home.education_image_android);
		value.put("videos_title", home.videos_title);
		value.put("videos_image_android", home.videos_image_android);
		value.put("magazines_title", home.magazines_title);
		value.put("magazines_image_android", home.magazines_image_android);
		value.put("books_title", home.books_title);
		value.put("books_image_android", home.books_image_android);
		return this.mDb.update("home", value, "id = " + home.id, null) > 0;
	}

	/**
	 * Deletes all {@link Home}.
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("home", null, null) > 0;
	}
}