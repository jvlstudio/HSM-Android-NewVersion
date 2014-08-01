package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.model.Book;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * BookDetailsActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class BookDetailsActivity extends SherlockActivity implements OnClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String URL = "http://apps.ikomm.com.br/hsm5/uploads/books/"; 
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Long mBookId;
	private Book mBook;
	
	private LinearLayout mSinopseLinearLayout;
	private LinearLayout mAuthorLinearLayout;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		
		setActionBar();
		getExtras();
		getBook();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.application_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	/**
	 * Sets the {#link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			mBookId = extras.getLong("id");
		}
	}
	
	/**
	 * Gets the current {@link Book}.
	 */
	public void getBook() {
		mBook = QueryHelper.getBook(mBookId);
		if (mBook != null) {
			setLayout();
		}
	}
	
	/**
	 * Loads all fields.
	 */
	private void setLayout() {
		setLinearLayout();
		setTextViewSwitches();
		setTextView();
		
		// Button.
		Button purchaseButton = (Button)findViewById(R.id.id_purchase_button);
		purchaseButton.setOnClickListener(this);
		
		// Image.
		ImageView bookImageView = (ImageView)findViewById(R.id.id_book_image_view);
		String url = URL + mBook.picture;
		setUniversalImage(url, bookImageView);
	}
	
	/**
	 * Sets the {@link LinearLayout}.
	 */
	public void setLinearLayout() {
		mSinopseLinearLayout = (LinearLayout)findViewById(R.id.id_sinopse_linear_layout);
		mAuthorLinearLayout = (LinearLayout)findViewById(R.id.id_author_linear_layout);
	}

	/**
	 * Sets {@link TextView} switches.
	 */
	public void setTextViewSwitches() {
		TextView sinopseTextView = (TextView)findViewById(R.id.id_sinopse_text_view);
		sinopseTextView.setOnClickListener(this);
		TextView authorTextView = (TextView)findViewById(R.id.id_author_text_view);
		authorTextView.setOnClickListener(this);
	}
	
	/**
	 * Sets the {@link TextView}.
	 */
	public void setTextView() {
		// Initializes TextView's.
		TextView titleTextView = (TextView)findViewById(R.id.id_title_text_view);
		TextView bookDescriptionTextView = (TextView)findViewById(R.id.id_book_description_text_view);
		TextView authorNameTextView = (TextView)findViewById(R.id.id_author_name_text_view);
		TextView authorDescriptionTextView = (TextView)findViewById(R.id.id_author_description_text_view);
		
		// Populates TextView's.
		titleTextView.setText(mBook.name);
		bookDescriptionTextView.setText(mBook.description);
		authorNameTextView.setText(mBook.author_name);
		authorDescriptionTextView.setText(mBook.author_description);
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
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(url, imageView, cache);
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.id_sinopse_text_view:
				mSinopseLinearLayout.setVisibility(View.VISIBLE);
				mAuthorLinearLayout.setVisibility(View.GONE);
				break;
			case R.id.id_author_text_view:
				mSinopseLinearLayout.setVisibility(View.GONE);
				mAuthorLinearLayout.setVisibility(View.VISIBLE);
				break;
			case R.id.id_purchase_button:
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setData(Uri.parse(mBook.link));
				startActivity(intent);
				break;
		}
	}
}