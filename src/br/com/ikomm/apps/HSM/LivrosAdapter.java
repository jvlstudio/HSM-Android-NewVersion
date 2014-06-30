package br.com.ikomm.apps.HSM;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import br.ikomm.hsm.model.Book;
import br.ikomm.hsm.model.BookRepo;
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

public class LivrosAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private List<Book> books;
	private BookRepo _br;
	
	public LivrosAdapter(Activity activity) {
		super();
		this.activity = activity;
		inflater = LayoutInflater.from(activity);
		
		_br = new BookRepo(activity);
		_br.open();
		books = _br.getAllBook();
		_br.close();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return books.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		Book _item = books.get(position); 
		return _item.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_livros_adapter, parent, false);
		
		Book _book = books.get(position);
		TextView nameBook = (TextView) view.findViewById(R.id.txtNameBook);
		TextView descBook = (TextView) view.findViewById(R.id.txtDescBook);
		ImageView imgBook = (ImageView) view.findViewById(R.id.imgBook);
		
		nameBook.setText(_book.name);
		descBook.setText(_book.author_name);
		
		String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/books/"+_book.picture;
		DisplayImageOptions _cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
	
		imageLoader.displayImage(imageUri, imgBook, _cache);
		
		return view;
	}
	
}