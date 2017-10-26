package com.clpays.tianfugou.Module.LoginRegister;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Module.LoginRegister.Fragment.RetrieveFragmentStep1;
import com.clpays.tianfugou.Module.LoginRegister.Fragment.RetrieveFragmentStep2;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
            finish();
    }

    @BindView(R.id.step1)
    TextView step1;
    @BindView(R.id.success)
    TextView success;

    private Fragment[] fragments;
    private int currentTabIndex;
    FragmentTransaction trx;
    private int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        EventBus.getDefault().register(this);

        RetrieveFragmentStep1 fragmentStep1=RetrieveFragmentStep1.newInstance();
        RetrieveFragmentStep2 fragmentStep2= RetrieveFragmentStep2.newInstance();
        fragments = new Fragment[] {
                fragmentStep1,
                fragmentStep2
        };
        step1.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        success.setTextColor(ContextCompat.getColor(this,R.color.gray_20));
        changeFragmentIndex(0);
    }

    //protected覆写，属于eventBus的bug? -->https://github.com/greenrobot/EventBus/issues/156  倒数第三行
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event) {
        String Type = event.getType();
        switch (Type) {
            case "修改成功":
                step1.setTextColor(ContextCompat.getColor(this,R.color.gray_20));
                success.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
                changeFragmentIndex(1);
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(int currentIndex) {
        index = currentIndex;
        switchFragment();
    }

    /**
     * Fragment切换
     */
    private void switchFragment() {
        trx = getSupportFragmentManager().beginTransaction();
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.step_view, fragments[index]);
        }
        trx.show(fragments[index]).commitAllowingStateLoss();//重要
        currentTabIndex = index;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_retrieve;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

}
