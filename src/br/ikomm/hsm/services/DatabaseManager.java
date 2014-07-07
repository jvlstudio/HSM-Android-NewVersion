package br.ikomm.hsm.services;

import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper{
	 
	private static DatabaseManager sInstance;
	private static final String DATABASE_NAME = "hsm.db";
	private static final int DATABASE_VERSION = 1;
	
	/*
	 * CAMPOS TABELA AGENDA
	 * [0] - _id
	 * [1] - id
	 * [2] - type
	 * [3] - event_id
	 * [4] - panelist_id
	 * [5] - date
	 * [6] - date_start
	 * [7] - date_end
	 * [8] - theme_title 
	 * [9] - theme_description
	 * [10] - label
	 * [11] - sublabel
	 * [12] - image
	 */
	 private static final String DATABASE_AGENDA = "create table agenda(_id integer primary key autoincrement, id integer, "
	 		+ "type text, event_id integer, panelist_id integer, date integer, date_start text, date_end text, theme_title text, theme_description text, "
	 		+ "label text, sublabel text, image text);";
	 
	 /*
	  * CAMPOS TABELA BOOK
	  * [0] - _id
	  * [1] - id
	  * [2] - name
	  * [3] - picture
	  * [4] - description
	  * [5] - author_name
	  * [6] - author_description
	  * [7] - link
	  */
	 private static final String DATABASE_BOOK = "create table book(_id integer primary key autoincrement, id integer, name text, picture text, "
	 		+ "description text, author_name text, author_description text, link text);";
	 	
	 /*
	  * CAMPOS TABELA EVENT
	  * [0] - _id
	  * [1] - id
	  * [2] - name
	  * [3] - slug
	  * [4] - description
	  * [5] - tiny_description
	  * [6] - info_dates
	  * [7] - info_hours
	  * [8] - info_locale
	  * [9] - image_list
	  * [10] - image_single
	  */
	 private static final String DATABASE_EVENT = "create table event(_id integer primary key autoincrement, id integer, name text, slug text, "
	 		+ "description text, tiny_description text, info_dates text, info_hours text, info_locale text, image_list text, image_single text);";

	 /*
	  * CAMPOS TABELA HOME
	  * [0] - _id
	  * [1] - id
	  * [2] - events_title
	  * [3] - events_image_android
	  * [4] - education_title
	  * [5] - education_image_android
	  * [6] - videos_title
	  * [7] - videos_image_android
	  * [8] - magazines_title
	  * [9] - magazines_image_android
	  * [10] - books_title
	  * [11] - books_image_android
	  */
	 private static final String DATABASE_HOME = "create table home(_id integer primary key autoincrement, id integer, events_title text, events_image_android text, "
	 		+ "education_title text, education_image_android text, videos_title text, videos_image_android text, magazines_title text, "
	 		+ "magazines_image_android text, books_title text, books_image_android text);";
		
	 /*
	  * CAMPOS TABELA MAGAZINE
	  * [0] - _id
	  * [1] - id
	  * [2] - name
	  * [3] - picture
	  * [4] - description
	  * [5] - link
	  */
	 private static final String DATABASE_MAGAZINE = "create table magazine(_id integer primary key autoincrement, id integer, name text, "
	 		+ "picture text, description text, link text);";
	 
	 /*
	  * CAMPOS TABELA PANELIST
	  * [0] - _id
	  * [1] - id
	  * [2] - name
	  * [3] - slug
	  * [4] - description
	  * [5] - picture
	  */
	 private static final String DATABASE_PANELIST = "create table panelist(_id integer primary key autoincrement, id integer, name text, "
	 		+ "slug text, description text, picture text);";
	 
	 /*
	  * CAMPOS TABELA PASSE
	  * [0] - _id
	  * [1] - id
	  * [2] - event_id
	  * [3] - event_name
	  * [4] - event_slug
	  * [5] - color
	  * [6] - name
	  * [7] - slug
	  * [8] - price_from
	  * [9] - price_to
	  * [10] - valid_to
	  * [11] - email
	  * [12] - description
	  * [13] - days
	  * [14] - mDates
	  * [15] - show_dates
	  * [16] - is_multiple
	  */
	 private static final String DATABASE_PASSE = "create table passe(_id integer primary key autoincrement, id integer, event_id integer, "
	 		+ "event_name text, event_slug text, color text, name text, slug text, price_from text, price_to text, "
	 		+ "valid_to text, email text, description text, days text, mDates text, show_dates text, is_multiple text);";
	  
	public static DatabaseManager getInstance(Context context){
		if (sInstance == null){
			sInstance = new DatabaseManager(context.getApplicationContext());
		}
		return sInstance;
	}
	 
	 
	private DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_AGENDA);
		db.execSQL(DATABASE_BOOK);
		db.execSQL(DATABASE_EVENT);
		db.execSQL(DATABASE_HOME);
		db.execSQL(DATABASE_MAGAZINE);
		db.execSQL(DATABASE_PANELIST);
		db.execSQL(DATABASE_PASSE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*
		 * CASO SEJA NECESSÁRIO UMA ATUALIZAÇÃO DO DB.
		 * 
		 * db.execSQL("DROP TABLE IF EXISTS agenda");
		 * db.execSQL("DROP TABLE IF EXISTS book");
		 * db.execSQL("DROP TABLE IF EXISTS event");
		 * db.execSQL("DROP TABLE IF EXISTS home");
		 * db.execSQL("DROP TABLE IF EXISTS magazine");
		 * db.execSQL("DROP TABLE IF EXISTS panelist");
		 * db.execSQL("DROP TABLE IF EXISTS passe");
		 */
		onCreate(db);
	}
}
