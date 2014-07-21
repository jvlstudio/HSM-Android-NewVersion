package br.ikomm.hsm.repo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.model.Book;
import br.ikomm.hsm.services.DatabaseManager;

@SuppressLint("NewApi")
/**
 * BookRepo.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class BookRepo {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private final Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public BookRepo(Context context) {
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
	public BookRepo open() throws SQLException{
		mDbManager = DatabaseManager.getInstance(mContext);
		mDb = mDbManager.getWritableDatabase();
		return this;
	}
	
	/**
	 * Closes the database
	 */
	public void close(){
		mDbManager.close();
	}
	
	/**
	 * Inserts a specific {@link Book}.
	 * 
	 * @param book
	 * 
	 * @return
	 */
	public long insertBook(Book book) {
		ContentValues value = new ContentValues();
		value.put("id", book.id);
		value.put("name", book.name);
		value.put("picture", book.picture);
		value.put("description", book.description);
		value.put("author_name", book.author_name);
		value.put("author_description", book.author_description);
		value.put("link", book.link);
		return mDb.insert("book", null, value);
	}
	
	/**
	 * Gets all {@link Book}.
	 * 
	 * @return
	 */
	public List<Book> getAllBook() {
		List<Book> bookList = new ArrayList<Book>();
		Book book;
		
		Cursor cursor = mDb.query("book", null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				book = new Book();
				book.id = cursor.getInt(1);
				book.name = cursor.getString(2);
				book.picture = cursor.getString(3);
				book.description = cursor.getString(4);
				book.author_name = cursor.getString(5);
				book.author_description = cursor.getString(6);
				book.link = cursor.getString(7);
				bookList.add(book);
			}
		}
		return bookList;
	}
	
	/**
	 * Gets a specific {@link Book}.
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 */
	public Book getBook(long id) throws SQLException {
		Book book = new Book();
		
		Cursor cursor = mDb.query(true, "book", null, "id = " + id, null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				book = new Book();
				book.id = cursor.getInt(1);
				book.name = cursor.getString(2);
				book.picture = cursor.getString(3);
				book.description = cursor.getString(4);
				book.author_name = cursor.getString(5);
				book.author_description = cursor.getString(6);
				book.link = cursor.getString(7);
			}
		}
		return book;
	}
	
	/**
	 * Deletes a specific {@link Book}.
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public boolean delete(long id){
		return mDb.delete("book", "id = " +id, null) > 0;
	}
	
	/**
	 * Updates a specific {@link Book}.
	 * 
	 * @param book
	 * 
	 * @return
	 */
	public boolean update(Book book) {
		ContentValues value = new ContentValues();
		value.put("id", book.id);
		value.put("name", book.name);
		value.put("picture", book.picture);
		value.put("description", book.description);
		value.put("author_name", book.author_name);
		value.put("author_description", book.author_description);
		value.put("link", book.link);
		return mDb.update("book", value, "id = " + book.id, null) > 0;
	}

	/**
	 * Deletes all {@link Book}.
	 * @return
	 */
	public boolean deleteAll() {
		return mDb.delete("book", null, null) > 0;
	}
}
