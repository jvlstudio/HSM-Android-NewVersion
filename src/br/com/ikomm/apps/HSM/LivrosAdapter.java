package br.com.ikomm.apps.HSM;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import br.ikomm.hsm.model.Book;
import br.ikomm.hsm.repo.BookRepo;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

/**
 * LivrosAdapter.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class LivrosAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private LayoutInflater mInflater;
	private List<Book> mBookList;
	private BookRepo mBookRepo;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public LivrosAdapter(Activity activity) {
		super();
		mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		
		mBookRepo = new BookRepo(activity);
		mBookRepo.open();
		mBookList = mBookRepo.getAllBook();
		mBookRepo.close();
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mBookList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		Book item = mBookList.get(position); 
		return item.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.fragment_livros_adapter, parent, false);
		
		Book book = mBookList.get(position);
		TextView nameBook = (TextView) view.findViewById(R.id.txtNameBook);
		TextView descBook = (TextView) view.findViewById(R.id.txtDescBook);
		ImageView imgBook = (ImageView) view.findViewById(R.id.imgBook);
		
		nameBook.setText(book.name);
		descBook.setText(book.author_name);
		
		String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/mBookList/"+book.picture;
		DisplayImageOptions _cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mActivity));
		imageLoader.displayImage(imageUri, imgBook, _cache);
		
		return view;
	}	
}