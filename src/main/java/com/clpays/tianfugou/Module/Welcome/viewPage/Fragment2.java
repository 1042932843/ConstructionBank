package com.clpays.tianfugou.Module.Welcome.viewPage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clpays.tianfugou.R;

import static android.content.Context.MODE_PRIVATE;


public class Fragment2 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_2, container, false);
		view.findViewById(R.id.tvInNew).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						SharedPreferences sp = getActivity().getSharedPreferences("data", MODE_PRIVATE);
						SharedPreferences.Editor edit = sp.edit();
						edit.putBoolean("isFirst", false);
						edit.apply();
						getActivity().finish();
						//动画
						//AnimationUtil.finishActivityAnimation(getActivity());
					}
				});
		return view;
	}

}
