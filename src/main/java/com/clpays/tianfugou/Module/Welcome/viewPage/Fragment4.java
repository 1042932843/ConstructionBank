package com.clpays.tianfugou.Module.Welcome.viewPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Module.Major.Home.HomePageActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.Animation.AnimationUtil;

import static android.content.Context.MODE_PRIVATE;


public class Fragment4 extends Fragment {
	ImageView pic;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_4, container, false);
		pic=(ImageView)view.findViewById(R.id.pic) ;
		Glide.with(this).load(R.drawable.welcome3).into(pic);
		view.findViewById(R.id.tvInNew).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
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
