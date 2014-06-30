package br.com.ikomm.apps.HSM;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.ikomm.hsm.DetalheEventoActivity;
import br.ikomm.hsm.HomeActivity;

public class EventosActivity extends FragmentActivity {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventos);

		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		addButtonListner();

		mPlanetTitles = getResources().getStringArray(R.array.menuList);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			if (id == 0) {
				startActivity(new Intent(EventosActivity.this, HomeActivity.class));
				finish();
			} else if (id == 1) {
				startActivity(new Intent(EventosActivity.this, EventosActivity.class));
				finish();
			} else if (id == 3){
				startActivity(new Intent(EventosActivity.this,
						ListaNetworkingActivity.class));
				finish();
			} else if (id == 5) {
				Uri uri = Uri.parse("http://www.youtube.com/watch?v=ZHolmn4LBzg");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
			else {
				Toast.makeText(EventosActivity.this, "Disponível em breve", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	public void showDialogClick(View v) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Conteúdo Indisponível");
		dialog.setMessage("Este conteúdo estará disponível em breve.");
		dialog.setPositiveButton("OK", null);

		final AlertDialog alert = dialog.create();
		alert.show();
	}

	private void addButtonListner() {
		findViewById(R.id.ibtnImagem1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(EventosActivity.this,
								DetalheEventoActivity.class));
					}
				});

		findViewById(R.id.ibtnImagem2).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(EventosActivity.this,
								MostraConteudoActivity.class));
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eventos, menu);
		return false;
	}

}
