package br.ikomm.hsm.fragment;

import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.model.Banner;
import br.ikomm.hsm.model.BannerRepository;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BannerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		BannerRepository repo = new BannerRepository(getActivity());
		List<Banner> list = repo.getAll();

		Random myRandomizer = new Random();
		final Banner bannerAtual = list.get(myRandomizer.nextInt(list.size()));

		View view = inflater
				.inflate(R.layout.fragment_banner, container, false);

		ImageView imgView = (ImageView) view.findViewById(R.id.ibtnFooter);
		String imageUri = bannerAtual.banner;
		// Aqui você adiciona a imagem do Banner

		// imgView.setImageResource(R.drawable.hsm_bg_bt_large);

		DisplayImageOptions cache = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true).build();

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

		imageLoader.displayImage(imageUri, imgView, cache);

		imgView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					try {
						if (!bannerAtual.url.isEmpty()) {
							Uri uri = Uri.parse(bannerAtual.url);
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
						} else {
							Toast.makeText(
									getActivity(),
									"Erro ao abrir o link, por favor tentar mais tarde.",
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						Toast.makeText(
								getActivity(),
								"Erro ao abrir o link, por favor tentar mais tarde.",
								Toast.LENGTH_SHORT).show();
					}
				}
				}
				return true;
			}
		});
		return view;
	}
}
