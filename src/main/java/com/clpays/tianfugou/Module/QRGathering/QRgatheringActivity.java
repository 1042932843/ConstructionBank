package com.clpays.tianfugou.Module.QRGathering;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Module.QRGathering.Fragement.GeneralJournalFragment;
import com.clpays.tianfugou.Module.QRGathering.Fragement.QrFragment;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import nbsix.clpays.tianfugou.R;

public class QRgatheringActivity extends BaseActivity {

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_qrgathering;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        GeneralJournalFragment generalJournalFragment=GeneralJournalFragment.newInstance();
        QrFragment qrFragment=QrFragment.newInstance();
        fragments = new Fragment[] {
                qrFragment,
                generalJournalFragment
        };

        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.qrcontainer, qrFragment)
                .show(qrFragment).commit();

    }

    //protected复写，属于eventBus的bug? -->https://github.com/greenrobot/EventBus/issues/156  倒数第三行
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void initToolBar() {

    }

    /**
     * 切换Fragment的下标
     */
     public void changeFragmentIndex(int currentIndex) {
        index = currentIndex;
        switchFragment();
    }

    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.replace(R.id.qrcontainer, fragments[index]);
        }
        if(index!=0){
            trx.addToBackStack(null);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String Type = event.getType();
        switch (Type){
            case "流水明细":
                changeFragmentIndex(1);
                break;
        }
    }
}
