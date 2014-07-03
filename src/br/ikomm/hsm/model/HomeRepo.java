package br.ikomm.hsm.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.services.DatabaseManager;

public class HomeRepo {
	private final Context Ctx;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	
	public HomeRepo(Context context) {
		super();
		this.Ctx = context;
	}
	
	public HomeRepo open() throws SQLException{
		this.mDbManager = DatabaseManager.getInstance(this.Ctx);
		this.mDb = this.mDbManager.getWritableDatabase();
		return this;
	}
	
	public void close(){
		this.mDbManager.close();
	}
	
	// insert 
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
		return this.mDb.insert("home", null, value);
	}
	
	// getAll
	public Cursor getAllHome() {
		return this.mDb.query("home", null, null, null, null, null, null);
	}
	
	// getSingle
	@SuppressLint("NewApi")
	public Cursor getHome(long id) throws SQLException {
		Cursor mCursor = this.mDb.query(true, "home", null, "id = " + id, null, null, null, null, null, null);
		
		 if (mCursor != null) {
	            mCursor.moveToFirst();
	     }
		 return mCursor;
	}
	
	// getSingle
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
	
	// delete
	public boolean delete(long id){
		return this.mDb.delete("home", "id = " +id, null) > 0;
	}
	
	// update
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

	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return this.mDb.delete("home", null, null) > 0;
	}
}
