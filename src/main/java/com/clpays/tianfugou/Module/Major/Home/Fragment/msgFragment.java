package com.clpays.tianfugou.Module.Major.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.Module.Message.MessageActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonObject;


public class msgFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.yidu)
    TextView yidu;
    @OnClick(R.id.yidu)
    public void yidu(){
        yidu.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        yidu.setBackgroundResource(R.drawable.shape_corner_right_blue);
        weidu.setTextColor(ContextCompat.getColor(getContext(),R.color.black_alpha_45));
        weidu.setBackgroundResource(R.drawable.shape_corner_left_gray);
    }

    @OnClick(R.id.go_mymessage)
    public void gomymessage(){
        Intent it=new Intent(getActivity(), MessageActivity.class);
        startActivity(it);
    }

    @BindView(R.id.weidu)
    TextView weidu;
    @OnClick(R.id.weidu)
    public void weidu(){
        weidu.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        weidu.setBackgroundResource(R.drawable.shape_corner_left_blue);
        yidu.setTextColor(ContextCompat.getColor(getContext(),R.color.black_alpha_45));
        yidu.setBackgroundResource(R.drawable.shape_corner_right_gray);
    }

    public static msgFragment newInstance() {

        return new msgFragment();
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
        return R.layout.fragment_msg;
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
