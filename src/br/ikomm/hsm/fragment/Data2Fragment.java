package br.ikomm.hsm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ikomm.hsm.DetalhePalestraActivity;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.Data2Adapter;

import com.actionbarsherlock.app.SherlockFragment;

public class Data2Fragment extends SherlockFragment {

	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_data2, container, false);

		listView = (ListView) view.findViewById(R.id.listViewData2);
		listView.setOnItemClickListener(onItemClickData1());
		setHasOptionsMenu(false);
		return view;
	}

	private OnItemClickListener onItemClickData1() {
		// TODO Auto-generated method stub
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adpterView, View view,
					int pos, long id) {

				if (id == 0) {
					return;
				}

				Intent intent = new Intent(getActivity(),
						DetalhePalestraActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}

		};
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		updateView();
	}

	private void updateView() {
		listView.setAdapter(new Data2Adapter(getActivity(), null));
	}
}
