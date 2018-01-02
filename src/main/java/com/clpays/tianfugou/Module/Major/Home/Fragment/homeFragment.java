package com.clpays.tianfugou.Module.Major.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.clpays.tianfugou.Adapter.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.clpays.tianfugou.Design.FullyLinearLayoutManager.FullyLinearLayoutManager;
import com.clpays.tianfugou.Entity.HomePage.homeItem;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.Module.Major.Home.FunctionTipActivity;
import com.clpays.tianfugou.Module.Major.QRGathering.QRgatheringActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.LogUtil;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.clpays.tianfugou.zxing.android.CaptureActivity;
import com.clpays.tianfugou.zxing.common.Constant;
import com.google.gson.JsonObject;


import static android.app.Activity.RESULT_OK;


public class homeFragment extends BaseFragment {
    private int REQUEST_CODE_SCAN = 111;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.shoukuan)
    RelativeLayout shoukuan;
    @OnClick(R.id.shoukuan)
    public void setShoukuan(){
        String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
        Intent it=new Intent();
        if("finish".equals(s)){
            it.setClass(getActivity(), QRgatheringActivity.class);
            startActivity(it);
        }else{
           it.setClass(getActivity(), FunctionTipActivity.class);
           it.putExtra("Title","收款");
            startActivity(it);
        }
    }

    @BindView(R.id.sao)
    RelativeLayout sao;
    @OnClick(R.id.sao)
    public void setSao(){
        String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
        Intent it=new Intent();
        if("finish".equals(s)){
           /* Intent intent = new Intent(getActivity(), CaptureActivity.class);
            //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE_SCAN);*/
            it.setClass(getActivity(), FunctionTipActivity.class);
            it.putExtra("Title","扫一扫");
            startActivity(it);
        }else{
            it.setClass(getActivity(), FunctionTipActivity.class);
            it.putExtra("Title","扫一扫");
            startActivity(it);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                LogUtil.d("扫描结果为：" + content);

            }
        }
    }

    public static homeFragment newInstance() {

        return new homeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initRecyclerView(){
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getContext()));
        List<homeItem> homeItems =new ArrayList<>();
        homeItem item2=new homeItem();
        item2.setName("银行贷款");
        item2.setImg(R.drawable.bg2_index);
        homeItems.add(item2);
        homeItem item3=new homeItem();
        item3.setName("商城缴费");
        item3.setImg(R.drawable.bg3_index);
        homeItems.add(item3);

        HomePageAdapter adapter=new HomePageAdapter(getContext(), homeItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusableInTouchMode(false); //设置不需要焦点
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        initRecyclerView();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){

        }else{
            lazyLoad();
        }
    }

    protected void lazyLoad() {
        if(UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username", UserState.NA))){

        }else{
            CheckLogin();
        }
    }

    /**
     * 检查登录,没登录去登录页面,登录了检查状态
     */
    public void CheckLogin(){
        String s= PreferenceUtil.getStringPRIVATE("token", UserState.NA);
        //LogUtil.d(s);
        if(s.isEmpty()||UserState.NA.equals(s)){
            ToastUtil.ShortToast("请登录！");
            Intent it=new Intent(getActivity(), LRpageActivity.class);
            startActivity(it);
        }else{
            JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();
            RetrofitHelper.getAppAPI()
                    .checkStatus(obj)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bean -> {
                        String a = bean.string();//{"success":true,"message":"","data":{"token":"1haL06uZXgHQIT6-0HuZ24Q1eQWjVSN0","status":"\u5b9e\u540d\u8ba4\u8bc1"}}
                        if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                            String status = isGetStringFromJson.handleData("status", isJsonObj.handleData("data", a));
                            PreferenceUtil.putStringPRIVATE("status", status);

                        }
                    },throwable -> {

                    });

        }
    }

}
