package com.clpays.tianfugou.Module.Major.Authentication.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintButton;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Module.Major.Authentication.StartAuthenticationActivity;
import com.clpays.tianfugou.Module.Major.Home.HomePageActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.LogUtil;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetJsonArrayFromJson;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ResultFragment extends BaseFragment {

    @BindView(R.id.stu_img)
    ImageView stu_img;
    DialogLoading dialogLoading;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.status_shenhetongguo)
    RelativeLayout status_shenhetongguo;
    @BindView(R.id.status_shenhe)
    RelativeLayout status_shenhe;

    @BindView(R.id.status_prepared)
    RelativeLayout status_prepared;
    @BindView(R.id.prepared)
    TextView prepared;


    @BindView(R.id.yuanyin)
    TextView yuanyin;
    @BindView(R.id.tongguo)
    TextView tongguo;

    @BindView(R.id.next_step)
    TintButton next_step;
    @OnClick(R.id.next_step)
    public void ok(){
        showExitDialog("是的，我已经准备好了相关材料","确认之后服务器会向您分配专员");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){

        }else{
            CheckStatus();
        }
    }
    @Override
    protected void lazyLoad() {

        if(UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username", UserState.NA))){

        }else{
            JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();
            RetrofitHelper.getAppAPI()
                    .checkStatus(obj)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bean -> {
                        swipeRefreshLayout.setRefreshing(false);
                        String a = bean.string();//{"success":true,"message":"","data":{"token":"1haL06uZXgHQIT6-0HuZ24Q1eQWjVSN0","status":"\u5b9e\u540d\u8ba4\u8bc1"}}
                        if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                            String status = isGetStringFromJson.handleData("status", isJsonObj.handleData("data", a));
                            PreferenceUtil.putStringPRIVATE("status", status);
                            EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("检查状态"));
                        }
                    },throwable -> {
                        swipeRefreshLayout.setRefreshing(false);
                    });
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
                        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
                        RetrofitHelper.getAuthenticationAPI()
                                .pushchecked(obj)
                                //.compose(this.bindToLifecycle())这里因为在不可见情况下更新页面，所以不能绑定生命周期
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(bean -> {
                                    String a=bean.string();
                                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                                        String name=isGetStringFromJson.handleData("name", isJsonObj.handleData("data",a));
                                        String phone=isGetStringFromJson.handleData("phone",isJsonObj.handleData("data",a));
                                        String time =isGetStringFromJson.handleData("time ",isJsonObj.handleData("data",a));
                                        prepared.setText("专员名称:"+name+"\n"+
                                                "手机号码:"+phone+"\n"+
                                                "预约时间:"+time);
                                        PreferenceUtil.putStringPRIVATE("status","prepared");
                                        CheckStatus();
                                    }else{
                                        //ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                                    }

                                }, throwable -> {
                                    ToastUtil.ShortToast("数据错误,请尝试重新确认");
                                });
                    }
                });

            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                        }
                    });
        }

    public static ResultFragment newInstance() {

        return new ResultFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initRecyclerView(){

    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_result;
    }

    @Override
    public void finishCreateView(Bundle state) {

        initRecyclerView();
        dialogLoading=new DialogLoading();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lazyLoad();
            }
        });

    }


    /**
     * 检查状态
     */
    public void CheckStatus(){
        String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
        LogUtil.d(s);
        switch (s){
            case "N/A":
                break;
            case "checked":
                stu_img.setImageResource(R.drawable.shenhetongguo);
                status_prepared.setVisibility(View.GONE);
                status_shenhe.setVisibility(View.GONE);
                status_shenhetongguo.setVisibility(View.VISIBLE);

                dialogLoading.setMessage("状态检查中...");
                dialogLoading.show(getFragmentManager(),DialogLoading.TAG);
                JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
                RetrofitHelper.getAuthenticationAPI()
                        .fetchchecked(obj)
                        //.compose(this.bindToLifecycle())这里因为在不可见情况下更新页面，所以不能绑定生命周期
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bean -> {
                            String a=bean.string();
                            dialogLoading.dismiss();
                            if("true".equals(isGetStringFromJson.handleData("success",a))){
                                JsonArray array= isGetJsonArrayFromJson.handleData("attachlist", isJsonObj.handleData("data",a));
                                for(int i=0;i<array.size();i++){
                                    tongguo.append(array.get(i).toString()+"\n");
                                }
                                next_step.setVisibility(View.VISIBLE);
                            }else{
                                //ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                            }

                        }, throwable -> {
                            ToastUtil.ShortToast("数据错误,请尝试重新加载本页面");
                            dialogLoading.dismiss();
                        });

                break;
            case "prepared":
                status_shenhetongguo.setVisibility(View.GONE);
                status_shenhe.setVisibility(View.GONE);
                status_prepared.setVisibility(View.VISIBLE);
                stu_img.setImageResource(R.drawable.renzheng_tijiao);
                break;
            case "waiting":
                status_shenhetongguo.setVisibility(View.GONE);
                status_prepared.setVisibility(View.GONE);
                status_shenhe.setVisibility(View.VISIBLE);
                stu_img.setImageResource(R.drawable.renzheng_shenhe);
                break;
        }
    }

}
