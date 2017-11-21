package com.clpays.tianfugou.Module.Major.Credit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.clpays.tianfugou.Adapter.CreditAdapter;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Entity.Credit.CreditType;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.Module.Major.Authentication.StartAuthenticationActivity;
import com.clpays.tianfugou.Module.Major.Credit.Fragment.CreditInfoFragment;
import com.clpays.tianfugou.Module.Major.Credit.Fragment.CreditStatusFragment;
import com.clpays.tianfugou.Module.Major.Credit.Fragment.CreditTypeFragment;
import com.clpays.tianfugou.Module.Major.Home.HomePageActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreditActivity extends BaseActivity {
    public static final String TAG = CreditActivity.class.getSimpleName();
    @BindView(R.id.container)
    RelativeLayout container;
    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CreditType event){
        String cmd = event.getCmd();

        switch (cmd){
            case "0":
                changeFragmentIndex(0);
                break;
            case "1":
                bundle.putSerializable("CreditType", event);
                changeFragmentIndex(1,bundle);
                break;
            case "2":
                if(!event.getContent().isEmpty()){
                    bundle.putSerializable("CreditType", event);
                    changeFragmentIndex(2,bundle);
                }else{
                    showExitDialog(event);
                }

                break;
            case "back":
                back();
                break;
            case "":
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void initFragments(){
        CreditTypeFragment creditTypeFragment=CreditTypeFragment.newInstance();
        CreditInfoFragment creditInfoFragment=CreditInfoFragment.newInstance();
        CreditStatusFragment creditStatusFragment=CreditStatusFragment.newInstance();
        fragments = new Fragment[] {
                creditTypeFragment,
                creditInfoFragment,
                creditStatusFragment
        };
        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, creditTypeFragment)
                .show(creditTypeFragment).commit();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_credit;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initFragments();
    }

    @Override
    public void initToolBar() {

    }

    public void out(){
        this.finish();
    }

    public void back(){
        currentTabIndex=currentTabIndex-1;
        changeFragmentIndex(currentTabIndex);
    }

    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(int currentIndex) {
        index = currentIndex;
        switchFragment();
    }
    private void changeFragmentIndex(int currentIndex,Bundle bundle) {
        index = currentIndex;
        switchFragment(bundle);
    }

    /**
     * Fragment切换
     */
    private void switchFragment() {

        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fragments[index].isAdded()) {
            trx.replace(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    private void switchFragment(Bundle bundle) {

        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fragments[index].isAdded()) {
            trx.replace(R.id.container, fragments[index]);
        }
        fragments[index].setArguments(bundle);
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //登录了并且是在审核状态UserState.isLogin()&&UserState.isAuditing()
            String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
            if(index==0){
                finish();
            }else{
                back();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void showExitDialog(CreditType event){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(R.mipmap.launcher);
        normalDialog.setTitle("提示");
        normalDialog.setMessage(event.getTitle()+"?");
        normalDialog.setPositiveButton("立即申请",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        bundle.putSerializable("CreditType", event);
                        changeFragmentIndex(2,bundle);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}
