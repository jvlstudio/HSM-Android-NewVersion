package br.com.ikomm.apps.HSM.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.adapter.PassAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * PassActivity.java class.
 * Modified by Rodrigo Cericatto at July 3, 2014.
 */
public class PassActivity extends SherlockFragmentActivity implements OnItemClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String EXTRA_EVENT_ID = "event_id";
	public static final String EXTRA_PASSE_ID = "passe_id";
	
	public static final int PARENT_IS_PASSES = 0;
	public static final int PARENT_IS_PARTICIPANT = 1;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Integer mEventId;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pass);
		
		getExtras();
		setActionBar();
		setListView();
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
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mEventId = extras.getInt(EventDetailsActivity.EXTRA_EVENT_ID);
		}
	}
	
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
	public void setListView() {
		ListView listview = (ListView) findViewById(R.id.id_pass_list_view);
		listview.setAdapter(new PassAdapter(this, mEventId));
		listview.setOnItemClickListener(this);
	}
	
	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------
	
	@Override
	public void onItemClick(AdapterView<?> s, View view, int position, long id) {
		Intent intent = new Intent(this, PaymentActivity.class);
		intent.putExtra(EXTRA_PASSE_ID, id);
		intent.putExtra(EXTRA_EVENT_ID, mEventId);
		intent.putExtra(PaymentActivity.EXTRA_PARENT, PARENT_IS_PASSES); 
		startActivity(intent);
	}
}