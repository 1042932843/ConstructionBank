package com.clpays.tianfugou.Module.LoginRegister.Fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.PayCustomView.PayCustomView;
import com.clpays.tianfugou.Design.TimeButton.TimeButton;
import com.clpays.tianfugou.Design.keyEditText.KeyEditText;
import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.LogUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RetrieveFragmentStep1 extends BaseFragment implements KeyEditText.KeyPreImeListener{
    DialogLoading dialogLoading;

    @BindView(R.id.submit)
    Button submit;
    @OnClick(R.id.submit)
    public void Submit(){
        dialogLoading.setMessage("请求中");
        dialogLoading.show(getFragmentManager(), DialogLoading.TAG);
        JsonObject obj=RequestProperty.CreateJsonObjectBody();
        String phoneNum=phone.getText().toString();
        String password=new_password.getText().toString();
        String captcha=identifying_code.getText().toString();
        obj.addProperty("phone",phoneNum);
        obj.addProperty("password",password);
        obj.addProperty("captcha",captcha);
        RetrofitHelper.getLoginRegisterAPI()
                .findpw(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                        EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("修改成功"));
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    dialogLoading.dismiss();
                    //ToastUtil.ShortToast("数据错误");
                });
    }

    @BindView(R.id.identifying_code_but)
    TimeButton identifying_code_but;
    @OnClick(R.id.identifying_code_but)
    public void getCode(){
        String phoneNum=phone.getText().toString();
        if(phoneNum.isEmpty()){
            ToastUtil.ShortToast("请填写手机号");
            return;
        }
        identifying_code_but.setEnabled(false);
        JsonObject obj= RequestProperty.CreateJsonObjectBody();
        obj.addProperty("phone",phoneNum);
        obj.addProperty("findpw",true);
        LogUtil.d("获取验证码");
        RetrofitHelper.getLoginRegisterAPI()
                .getCaptcha(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        //ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                        String seconds=isGetStringFromJson.handleData("seconds", isJsonObj.handleData("data",a));
                        int time=Integer.parseInt(seconds);
                        identifying_code_but.setLength(time * 1000).initTimer();
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                        String seconds=isGetStringFromJson.handleData("seconds", isJsonObj.handleData("data",a));
                        if(seconds.isEmpty()){
                            identifying_code_but.setEnabled(true);
                        }else{
                            int time=Integer.parseInt(seconds);
                            identifying_code_but.setLength(time * 1000).initTimer();
                        }

                    }
                }, throwable -> {
                    identifying_code_but.setEnabled(true);
                    // ToastUtil.ShortToast("数据错误");
                });
    }

    @BindView(R.id.phone)
    KeyEditText phone;
    @BindView(R.id.identifying_code)
    KeyEditText identifying_code;
    @BindView(R.id.new_password)
    KeyEditText new_password;


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            submit.setEnabled(identifying_code.getText().length() != 0&& new_password.getText().length() != 0);
        }
    };


    public static RetrieveFragmentStep1 newInstance() {

        return new RetrieveFragmentStep1();
    }

    @Override
    public void initRecyclerView(){

    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_retrieve_step1;
    }

    @Override
    public void finishCreateView(Bundle state) {
        dialogLoading=new DialogLoading();
        identifying_code.addTextChangedListener(textWatcher);
        new_password.addTextChangedListener(textWatcher);
        identifying_code.setKeyPreImeListener(this);
        new_password.setKeyPreImeListener(this);
    }


    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        phone.clearFocus();
        identifying_code.clearFocus();
        new_password.clearFocus();

    }
}
