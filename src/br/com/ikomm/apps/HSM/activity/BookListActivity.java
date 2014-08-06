package br.com.ikomm.apps.HSM.activity;

import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.BookAdapter;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Book;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * BookListActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class BookListActivity extends SherlockFragmentActivity implements OnItemClickListener {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String EXTRA_BOOK_ID = "book_id";
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_list);

		setActionBar();
		setAdapter();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_application, menu);
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
	 * Sets the {@link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Sets the {@link ListView}.
	 */
	public void setAdapter() {
		List<Book> bookList = ContentManager.getInstance().getCachedBookList();
		ListView bookListView = (ListView)findViewById(R.id.id_book_list_view);
		bookListView.setAdapter(new BookAdapter(this, bookList));
		bookListView.setOnItemClickListener(this);
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, BookDetailsActivity.class);
		intent.putExtra(EXTRA_BOOK_ID, id);
		startActivity(intent);
	}
}