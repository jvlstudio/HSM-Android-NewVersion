package br.ikomm.hsm.model;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.ikomm.hsm.services.DatabaseManager;

@SuppressLint("NewApi")
public class BookRepo {
	private final Context Ctx;
	private SQLiteDatabase mDb;
	private DatabaseManager mDbManager;
	
	
	public BookRepo(Context context) {
		super();
		this.Ctx = context;
	}
	
	public BookRepo open() throws SQLException{
		this.mDbManager = DatabaseManager.getInstance(this.Ctx);
		this.mDb = this.mDbManager.getWritableDatabase();
		return this;
	}
	
	public void close(){
		this.mDbManager.close();
	}
	
	// insert 
	public long insertBook(Book book) {
		ContentValues value = new ContentValues();
		value.put("id", book.id);
		value.put("name", book.name);
		value.put("picture", book.picture);
		value.put("description", book.description);
		value.put("author_name", book.author_name);
		value.put("author_description", book.author_description);
		value.put("link", book.link);
		return this.mDb.insert("book", null, value);
	}
	
	// getAll
	public List<Book> getAllBook() {
		List<Book> books = new ArrayList<Book>();
		Book _book;
		
		Cursor mCursor = this.mDb.query("book", null, null, null, null, null, null);
		
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_book = new Book();
				_book.id = mCursor.getInt(1);
				_book.name = mCursor.getString(2);
				_book.picture = mCursor.getString(3);
				_book.description = mCursor.getString(4);
				_book.author_name = mCursor.getString(5);
				_book.author_description = mCursor.getString(6);
				_book.link = mCursor.getString(7);
				books.add(_book);
			}
		}
		return books;
	}
	
	// getSingle
	public Book getBook(long id) throws SQLException {
		Book _book = new Book();
		Cursor mCursor = this.mDb.query(true, "book", null, "id = " + id, null, null, null, null, null, null);
		
		if (mCursor.getCount() > 0) {
			while (mCursor.moveToNext()) {
				_book = new Book();
				_book.id = mCursor.getInt(1);
				_book.name = mCursor.getString(2);
				_book.picture = mCursor.getString(3);
				_book.description = mCursor.getString(4);
				_book.author_name = mCursor.getString(5);
				_book.author_description = mCursor.getString(6);
				_book.link = mCursor.getString(7);
			}
		}
		
		return _book;
	}
	
	// delete
	public boolean delete(long id){
		return this.mDb.delete("book", "id = " +id, null) > 0;
	}
	
	// update
	public boolean update(Book book) {
		ContentValues value = new ContentValues();
		value.put("id", book.id);
		value.put("name", book.name);
		value.put("picture", book.picture);
		value.put("description", book.description);
		value.put("author_name", book.author_name);
		value.put("author_description", book.author_description);
		value.put("link", book.link);
		return this.mDb.update("book", value, "id = " + book.id, null) > 0;
	}

	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return this.mDb.delete("book", null, null) > 0;
	}
}
