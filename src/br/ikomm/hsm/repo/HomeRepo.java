package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Home;
import br.ikomm.hsm.services.DatabaseManager;

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
		List<Home> homes = new ArrayList<Home>();
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
				homes.add(home);
			}
		}
		return homes;
	}
	
	/**
	 * Gets a specific {@link Home}.
	 * 
	 * @param id
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressLint("NewApi")
	public Cursor getHome(long id) throws SQLException {
		Cursor cursor = mDb.query(true, "home", null, "id = " + id, null, null, null, null, null, null);
		
		 if (cursor != null) {
			 cursor.moveToFirst();
	     }
		 return cursor;
	}
	
	/**
	 * Gets a {@link Home} from a {@link Cursor}.
	 * 
	 * @param cursor
	 * @return
	 */
	public Home getHomeFromCursor(Cursor cursor) {
		Home home = new Home();
		home.id = cursor.getInt(0);
		home.events_title = cursor.getString(1);
		home.events_image_android = cursor.getString(2);
		home.education_title = cursor.getString(3);
		home.education_image_android = cursor.getString(4);
		home.videos_title = cursor.getString(5);
		home.videos_image_android = cursor.getString(6);
		home.magazines_title = cursor.getString(7);
		home.magazines_image_android = cursor.getString(8);
		home.books_title = cursor.getString(9);
		home.books_image_android = cursor.getString(10);
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