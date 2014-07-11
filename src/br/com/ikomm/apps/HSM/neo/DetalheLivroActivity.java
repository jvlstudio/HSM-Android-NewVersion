package br.com.ikomm.apps.HSM.neo;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Book;
import br.ikomm.hsm.repo.BookRepo;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * DetalheLivroActivity.java class.
 * Modified by Rodrigo Cericatto at July 10, 2014.
 */
public class DetalheLivroActivity extends SherlockActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private static Long mLivroId;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_livro);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		action.setDisplayHomeAsUpEnabled(true);
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			mLivroId = extras.getLong("id");
		}
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu. This code adds items to the action bar.
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
	// Fragment
	//--------------------------------------------------
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private String mUrl;
		private Book mBook;
		
		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detalhe_livro, container, false);
			
			BookRepo bookRepo = new BookRepo(getActivity());
			bookRepo.open();
			mBook = bookRepo.getBook(mLivroId);
			bookRepo.close();
			if (mBook != null) {
				loadFields(rootView);
				addListenerButton(rootView);
			}
			return rootView;
		}

		/**
		 * Adds {@link Button} listeners.
		 * 
		 * @param rootView
		 */
		private void addListenerButton(View rootView) {
			TextView sinopseTextView = (TextView) rootView.findViewById(R.id.id_sinopse_text_view);
			TextView autorTextView = (TextView) rootView.findViewById(R.id.id_autor_text_view);
			
			final LinearLayout ll1 = (LinearLayout) rootView.findViewById(R.id.llSinopse);
			final LinearLayout ll2 = (LinearLayout) rootView.findViewById(R.id.llEspec);
			final LinearLayout ll3 = (LinearLayout) rootView.findViewById(R.id.llAutor);
			
			sinopseTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ll1.setVisibility(View.VISIBLE);
					ll2.setVisibility(View.GONE);
					ll3.setVisibility(View.GONE);
				}
			});
			autorTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ll1.setVisibility(View.GONE);
					ll2.setVisibility(View.GONE);
					ll3.setVisibility(View.VISIBLE);
				}
			});
			
			Button btnComprar = (Button) rootView.findViewById(R.id.btnComprarDet);
			btnComprar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
			        intent.setData(Uri.parse(mUrl));
			        startActivity(intent);
				}
			});
		}
		
		//--------------------------------------------------
		// Methods
		//--------------------------------------------------

		/**
		 * Loads all fields.
		 * 
		 * @param rootView
		 */
		private void loadFields(View rootView) {
			ImageView img = (ImageView) rootView.findViewById(R.id.imgLivroDet);
			TextView textTitulo = (TextView) rootView.findViewById(R.id.txtTituloDet);
			TextView textDescricao = (TextView) rootView.findViewById(R.id.txtDescricaoDet);
			TextView textSinopse = (TextView) rootView.findViewById(R.id.textDescricaoLivro);
			TextView textTamanho = (TextView) rootView.findViewById(R.id.textTamanho);
			TextView textPagina = (TextView) rootView.findViewById(R.id.textPag);
			TextView textCodigo = (TextView) rootView.findViewById(R.id.textCodigo);
			TextView textISBM = (TextView) rootView.findViewById(R.id.textISBM);
			TextView textAutor = (TextView) rootView.findViewById(R.id.textNomeAutor);
			TextView textHistoria = (TextView) rootView.findViewById(R.id.textDescricaoAutor);
			
			mUrl = mBook.link;
			textTitulo.setText(mBook.name);
			textDescricao.setText("");
			textSinopse.setText(mBook.description);
			textTamanho.setText("-");
			textPagina.setText("-");
			textCodigo.setText("-");
			textISBM.setText("-");
			textAutor.setText(mBook.author_name);
			textHistoria.setText(mBook.author_description);
			
			String url = "http://apps.ikomm.com.br/hsm5/uploads/books/" + mBook.picture;
			setUniversalImage(url, img);
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
			imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
			imageLoader.displayImage(url, imageView, cache);
		}
	}
}