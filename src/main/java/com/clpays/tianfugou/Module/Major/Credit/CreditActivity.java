package com.clpays.tianfugou.Module.Major.Credit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.clpays.tianfugou.Adapter.CreditAdapter;
import com.clpays.tianfugou.Entity.Credit.CreditType;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Module.Major.Credit.Fragment.CreditInfoFragment;
import com.clpays.tianfugou.Module.Major.Credit.Fragment.CreditStatusFragment;
import com.clpays.tianfugou.Module.Major.Credit.Fragment.CreditTypeFragment;
import com.clpays.tianfugou.Module.Major.Home.HomePageActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.google.gson.JsonObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);

    }
    public void initFragments(){
        CreditTypeFragment creditTypeFragment=CreditTypeFragment.newInstance();
        CreditInfoFragment creditInfoFragment=CreditInfoFragment.newInstance();
        CreditStatusFragment creditStatusFragment=CreditStatusFragment.newInstance();
        fragments = new Fragment[] {
                creditTypeFragment,
                creditInfoFragment
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
    /**
     * Fragment切换
     */
    private void switchFragment() {

        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

}
