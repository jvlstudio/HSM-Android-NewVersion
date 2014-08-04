package br.com.ikomm.apps.HSM.fragment;

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
import br.com.ikomm.apps.HSM.model.Banner;
import br.com.ikomm.apps.HSM.repo.BannerRepository;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * BannerFragment.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Aug 4, 2014
 */
public class BannerFragment extends Fragment {

	//--------------------------------------------------
	// Fragment Life Cycle
	//--------------------------------------------------
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		BannerRepository repo = new BannerRepository(getActivity());
		List<Banner> list = repo.getAll();

		Random randomizer = new Random();
		final Banner currentBanner = list.get(randomizer.nextInt(list.size()));

		View view = inflater.inflate(R.layout.fragment_banner, container, false);

		ImageView imageView = (ImageView) view.findViewById(R.id.id_banner_image_view);
		String url = currentBanner.banner;
		
		// Here we add the Banner image.
		setUniversalImage(url, imageView);
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						try {
							if (!currentBanner.url.isEmpty()) {
								Uri uri = Uri.parse(currentBanner.url);
								Intent intent = new Intent(Intent.ACTION_VIEW, uri);
								startActivity(intent);
							} else {
								Toast.makeText(getActivity(), getActivity().getString(R.string.banner_fragment_empty), Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							Toast.makeText(getActivity(), getActivity().getString(R.string.banner_fragment_empty), Toast.LENGTH_SHORT).show();
						}
					}
				}
				return true;
			}
		});
		return view;
	}
	
	//--------------------------------------------------
	// Fragment Life Cycle
	//--------------------------------------------------
	
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