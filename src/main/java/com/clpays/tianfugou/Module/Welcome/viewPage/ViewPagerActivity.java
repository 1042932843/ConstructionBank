package com.clpays.tianfugou.Module.Welcome.viewPage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;

import com.clpays.tianfugou.Adapter.ViewPagerAdapter;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: ViewPagerActivity
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //欢迎界面，分开的Fragment创建只是为了保持高度的定制化。
 * Date: 2017-10-31 15:20
 */
public class ViewPagerActivity extends BaseActivity {
	private ViewPager mVPActivity;
	private Fragment1 mFragment1;
	private Fragment2 mFragment2;

	private Fragment4 mFragment4;
	private List<Fragment> mListFragment = new ArrayList<Fragment>();
	private PagerAdapter mPgAdapter;

	UltraViewPager ultraViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_viewpager;
	}

	@Override
	public void initViews(Bundle savedInstanceState) {
		ultraViewPager = (UltraViewPager)findViewById(R.id.ultra_viewpager);
		ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //initialize UltraPagerAdapter，and add child view to UltraViewPager
		mFragment1 = new Fragment1();
		mFragment2 = new Fragment2();

		mFragment4 = new Fragment4();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mListFragment.add(mFragment4);
		mPgAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mListFragment);
		ultraViewPager.setAdapter(mPgAdapter);

        //initialize built-in indicator
		ultraViewPager.initIndicator();
       //set style of indicators
		ultraViewPager.getIndicator()
				.setOrientation(UltraViewPager.Orientation.HORIZONTAL)
				.setFocusColor(Color.GREEN)
				.setNormalColor(Color.WHITE)
				.setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        //set the alignment
		ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
		ultraViewPager.getIndicator().build();

        //set an infinite loop
		ultraViewPager.setInfiniteLoop(false);
        //enable auto-scroll mode
		//ultraViewPager.setAutoScroll(2000);
	}

	@Override
	public void initToolBar() {

	}

}
