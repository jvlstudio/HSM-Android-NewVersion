package br.ikomm.hsm.fragment;

import com.actionbarsherlock.app.SherlockFragment;

import br.com.ikomm.apps.HSM.R;
import br.ikomm.hsm.adapter.BDWData2Adapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BDWData2Fragment extends SherlockFragment {

	ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_bdwdata2, container, false);
		
		listView = (ListView) view.findViewById(R.id.listViewBDWData2);
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		updateView();
	}

	private void updateView() {
		// TODO Auto-generated method stub
		listView.setAdapter(new BDWData2Adapter(getActivity(), null));
	}
}