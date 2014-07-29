package br.com.ikomm.apps.HSM.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.model.Book;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * BookAdapter.java class.
 * Modified by Rodrigo Cericatto at July 9, 2014.
 */
public class BookAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private ViewHolder mViewHolder;
	private Activity mActivity;
	
	private List<Book> mBookList;
	
	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	static class ViewHolder {
		private TextView bookName;
		private TextView bookDescription;
		private ImageView bookImage;
	}
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public BookAdapter(Activity activity, List<Book> bookList) {
		super();
		mActivity = activity;
		mBookList = bookList;
	}

	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mBookList.size();
	}

	@Override
	public Book getItem(int position) {
		return mBookList.get(position);
	}

	@Override
	public long getItemId(int position) {
		Book item = mBookList.get(position); 
		return item.id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Book book = getItem(position);
		mViewHolder = new ViewHolder();

		if (convertView == null) {
			// Sets layout.
			LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_book, parent, false);
			
			// Set views.
			mViewHolder.bookName = (TextView) convertView.findViewById(R.id.txtNameBook);
			mViewHolder.bookDescription = (TextView) convertView.findViewById(R.id.txtDescBook);
			mViewHolder.bookImage = (ImageView) convertView.findViewById(R.id.imgBook);
			
			// Saves ViewHolder into the tag.
			convertView.setTag(mViewHolder);
		} else {
			// Gets ViewHolder from the tag.
			mViewHolder = (ViewHolder)convertView.getTag();
		}
		
		// Sets the data.
		setData(book);
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Sets the {@link Adapter} data.
	 * 
	 * @param book
	 */
	public void setData(Book book) {
		mViewHolder.bookName.setText(book.name);
		mViewHolder.bookDescription.setText(book.author_name);
		
		String url = "http://apps.ikomm.com.br/hsm5/uploads/books/" + book.picture;
		setUniversalImage(url, mViewHolder.bookImage);
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage(String url, ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mActivity));
		imageLoader.displayImage(url, imageView, cache);
	}
}