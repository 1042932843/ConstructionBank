package com.clpays.tianfugou.Module.Welcome.viewPage;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.clpays.tianfugou.Adapter.ViewPagerAdapter;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
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
	public static final String TAG = ViewPagerActivity.class.getSimpleName();
	protected Fragment1 mFragment1;
	protected Fragment2 mFragment2;
	protected Fragment3 mFragment3;
	protected Fragment4 mFragment4;
	protected Fragment5 mFragment5;
	protected Fragment6 mFragment6;
	protected List<Fragment> mListFragment = new ArrayList<Fragment>();
	protected PagerAdapter mPgAdapter;

	UltraViewPager ultraViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//SystemBarHelper.immersiveStatusBar(this);
		initPermission();

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
		mFragment3 = new Fragment3();
		mFragment5 = new Fragment5();
		mFragment6 = new Fragment6();
 		mFragment4 = new Fragment4();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mListFragment.add(mFragment3);
		mListFragment.add(mFragment5);
		mListFragment.add(mFragment6);
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
		ultraViewPager.getIndicator().setMargin(0,0,0,30);
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

	/**
	 * RxPermission权限动态申请
	 */
	private void initPermission() {
		RxPermissions rxPermissions = new RxPermissions(this);
		rxPermissions.setLogging(true);
		rxPermissions.requestEach(
				Manifest.permission.INTERNET,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.READ_PHONE_STATE,
				Manifest.permission.ACCESS_NETWORK_STATE,
				Manifest.permission.ACCESS_WIFI_STATE,
				Manifest.permission.CAMERA,
				Manifest.permission.WRITE_SETTINGS,
				Manifest.permission.WAKE_LOCK,
				Manifest.permission.VIBRATE,
				Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
				Manifest.permission.MODIFY_AUDIO_SETTINGS

		)
				.subscribe(permission -> {
					if (permission.granted) {
						// 用户已经同意该权限
						Log.d(TAG, permission.name + " is granted.");

					} else if (permission.shouldShowRequestPermissionRationale) {
						// 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
						Log.d(TAG, permission.name + " is denied. More info should be provided.");
						//app.getInstance().Exit();
					} else {
						// 用户拒绝了该权限，并且选中『不再询问』
						Log.d(TAG, permission.name + " is denied.");
						//app.getInstance().Exit();

					}
				});
	}

	private long exitTime=0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				app.getInstance().Exit();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
