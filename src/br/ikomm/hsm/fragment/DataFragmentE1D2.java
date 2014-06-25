package br.ikomm.hsm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.ikomm.apps.HSM.DetalhePalestraNeoActivity;
import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.DetalhePalestraActivity;
import br.ikomm.hsm.adapter.Data1Adapter;
import br.ikomm.hsm.adapter.DataAdapterE1;

import com.actionbarsherlock.app.SherlockFragment;

public class DataFragmentE1D2 extends SherlockFragment {

	private ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_data1, container, false);

		listView = (ListView) view.findViewById(R.id.listViewData1);
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
				Intent intent = new Intent(getActivity(),
						DetalhePalestraNeoActivity.class);
				intent.putExtra("id", 2);
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
		listView.setAdapter(new DataAdapterE1(getActivity(), null,2));
	}
}

