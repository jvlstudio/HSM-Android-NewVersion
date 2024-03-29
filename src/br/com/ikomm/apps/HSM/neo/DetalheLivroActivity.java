package br.com.ikomm.apps.HSM.neo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.R.id;
import br.com.ikomm.apps.HSM.R.layout;
import br.com.ikomm.apps.HSM.R.menu;
import br.ikomm.hsm.model.Book;
import br.ikomm.hsm.model.BookRepo;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DetalheLivroActivity extends Activity {

	private static Long id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_livro);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);

		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			id = extras.getLong("id");
		}
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_livro, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private String URL;
		private Book _book;
		
		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detalhe_livro,
					container, false);
			
			BookRepo _br = new BookRepo(getActivity());
			_br.open();
			_book = _br.getBook(id);
			_br.close();
			
			if (_book != null) {
				carregarCampos(rootView);
				addListenerButton(rootView);
			}
			
			return rootView;
		}

		private void addListenerButton(View rootView) {
			// TODO Auto-generated method stub
			TextView tS = (TextView) rootView.findViewById(R.id.tS);
			TextView tE = (TextView) rootView.findViewById(R.id.tE);
			TextView tA = (TextView) rootView.findViewById(R.id.tA);
			
			final LinearLayout ll1 = (LinearLayout) rootView.findViewById(R.id.llSinopse);
			final LinearLayout ll2 = (LinearLayout) rootView.findViewById(R.id.llEspec);
			final LinearLayout ll3 = (LinearLayout) rootView.findViewById(R.id.llAutor);
			
			tS.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ll1.setVisibility(View.VISIBLE);
					ll2.setVisibility(View.GONE);
					ll3.setVisibility(View.GONE);
				}
			});
			
			tE.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ll1.setVisibility(View.GONE);
					ll2.setVisibility(View.VISIBLE);
					ll3.setVisibility(View.GONE);
				}
			});

			tA.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ll1.setVisibility(View.GONE);
					ll2.setVisibility(View.GONE);
					ll3.setVisibility(View.VISIBLE);
				}
			});
			
			Button btnComprar = (Button) rootView.findViewById(R.id.btnComprarDet);
			btnComprar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
			        intent.setData(Uri.parse(URL));
			        startActivity(intent);
				}
			});
		}

		private void carregarCampos(View rootView) {
			// TODO Auto-generated method stub
			
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
			
			URL = _book.link;
			textTitulo.setText(_book.name);
			textDescricao.setText("");
			textSinopse.setText(_book.description);
			textTamanho.setText("-");
			textPagina.setText("-");
			textCodigo.setText("-");
			textISBM.setText("-");
			textAutor.setText(_book.author_name);
			textHistoria.setText(_book.author_description);
			
			String imageUri = "http://apps.ikomm.com.br/hsm5/uploads/books/"+_book.picture;
			DisplayImageOptions _cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		
			imageLoader.displayImage(imageUri, img, _cache);
		}
	}

}
