package com.clpays.tianfugou.Module.Welcome.viewPage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.Animation.AnimationUtil;

import static android.content.Context.MODE_PRIVATE;


public class Fragment1 extends Fragment {
	ImageView hand;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_1, container, false);
		hand=(ImageView)view.findViewById(R.id.hand) ;
		/*AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation aa=new AlphaAnimation(1,0);
		aa.setDuration(2000);
		animationSet.addAnimation(aa);
		TranslateAnimation translateAnimation = new TranslateAnimation(
				//X轴初始位置
				Animation.RELATIVE_TO_SELF, 0f,
				//X轴移动的结束位置
				Animation.RELATIVE_TO_SELF,-2f,
				//y轴开始位置
				Animation.RELATIVE_TO_SELF,0.0f,
				//y轴移动后的结束位置
				Animation.RELATIVE_TO_SELF,0.0f);
		//2秒完成动画
		translateAnimation.setDuration(2000);
		animationSet.addAnimation(translateAnimation);
		animationSet.setRepeatMode(Animation.RESTART);*/
		//hand.startAnimation(animationSet);
		hand.setVisibility(View.GONE);
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
