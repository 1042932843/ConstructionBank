package com.clpays.tianfugou.Module.Major.Home.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.clpays.tianfugou.Adapter.ucenterAdapter;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.WaveView.WaveView;
import com.clpays.tianfugou.Entity.UCenter.ucItem;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.Module.Major.Authentication.StartAuthenticationActivity;
import com.clpays.tianfugou.Module.Major.Home.HomePageActivity;
import com.clpays.tianfugou.Module.Major.Set.SetActivity;
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
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class userFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.waveview)
    WaveView waveView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.isfinish)
    TextView renzheng;

    @OnClick(R.id.set)
    public void set(){
        Intent it=new Intent(getActivity(), SetActivity.class);
        startActivity(it);
    }

    public static userFragment newInstance() {

        return new userFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initRecyclerView(){

        List<ucItem> ucItemList =new ArrayList<>();
        ucItem item=new ucItem();
        item.setType("认证信息");
        item.setImg(R.drawable.renzheng);
        ucItemList.add(item);

        ucItem item2=new ucItem();
        item2.setType("我的账户");
        item2.setImg(R.drawable.zhanghu);
        ucItemList.add(item2);

        ucItem item3=new ucItem();
        item3.setType("我的贷款");
        item3.setImg(R.drawable.daikuan);
        ucItemList.add(item3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ucenterAdapter adapter=new ucenterAdapter(getContext(),ucItemList);
        recyclerView.setAdapter(adapter);


    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ucenter;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        waveView.setMode(WaveView.MODE_RECT);
        waveView.setMax(50);
        waveView.setProgress(40);
        initRecyclerView();
        lazyLoad();
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
            CheckStatus();
           /* JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();
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
                            CheckStatus();
                            },throwable -> {
                        CheckStatus();
                    });*/

        }
    }

    /**
     * 如果已经登录(保存了token)，那么检查状态
     */
    public void CheckStatus(){

        String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
        LogUtil.d(s);
        switch (s){
            case "finish":
                renzheng.setText("已认证");
            case "N/A":
                break;
            case"profile":
            case"package":
            case "upload":
                Intent it=new Intent(getActivity(), StartAuthenticationActivity.class);
                startActivity(it);
                break;
            case "review_profile":
                showExitDialog("基本资料审核未通过","请前往相关页面修改！");
                break;
            case "review_upload":
                showExitDialog("证件上传审核未通过","请前往相关页面修改！");
                break;
            case "review_profile_upload":
                showExitDialog("基本资料和证件上传审核未通过","请前往相关页面修改！");
                break;
            case "waiting":
                showExitDialog("认证审核中","是否前往相关页面查看?(所有流程完成后才可使用应用功能)");
                break;
            case "checked":
                showExitDialog("审核认证成功","是否前往相关页面查看?(所有流程完成后才可使用应用功能)");
            case "prepared":
                break;
        }
    }

    private void showExitDialog(String title,String msg){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setIcon(R.mipmap.launcher);
        normalDialog.setTitle(title);
        normalDialog.setMessage(msg);
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent it=new Intent(getActivity(), StartAuthenticationActivity.class);
                        startActivity(it);
                    }
                });
        if(title.equals("证件上传审核未通过")||title.equals("基本资料审核未通过")||title.equals("基本资料和证件上传审核未通过")){

        }else{
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                        }
                    });
        }



        // 显示
        normalDialog.show();
    }
}
