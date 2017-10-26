package com.clpays.tianfugou.Module.LoginRegister;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.keyEditText.KeyEditText;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordActivity extends BaseActivity implements KeyEditText.KeyPreImeListener{
    DialogLoading dialogLoading;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }


    @BindView(R.id.tip)
    TextView tip;

    @BindView(R.id.old_password)
    KeyEditText old_password;
    @BindView(R.id.new_password)
    KeyEditText new_password;

    @BindView(R.id.submit)
    Button submit;
    @OnClick(R.id.submit)
    public void Submit(){
        dialogLoading.setMessage("请求中");
        dialogLoading.show(getSupportFragmentManager(), DialogLoading.TAG);
        JsonObject obj= RequestProperty.CreateJsonObjectBody();
        String Opassword=old_password.getText().toString();
        String Npassword=new_password.getText().toString();
        obj.addProperty("password",Opassword);
        obj.addProperty("newpassword",Npassword);
        RetrofitHelper.getLoginRegisterAPI()
                .changepw(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                        showDialog("重新登录","身份验证失效，请重新登录");
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    dialogLoading.dismiss();
                    //ToastUtil.ShortToast("数据错误");
                });
    }


    Dialog dialog;
    private void showDialog(String title,String message){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */

        if(dialog!=null){
            dialog.dismiss();
        }

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        //normalDialog.setIcon(R.mipmap.launcher);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if("重新登录".equals(title)){
                            Intent it=new Intent(ChangePasswordActivity.this,LRpageActivity.class);
                            startActivity(it);
                            finish();
                        }

                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        if("强制升级".equals(title)||"重新登录".equals(title)){
                            System.exit(0);
                        }
                    }
                });
        // 显示
        dialog= normalDialog.show();

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            submit.setEnabled(old_password.getText().length() != 0&& new_password.getText().length() != 0);
            if(old_password.getText().toString().equals(new_password.getText().toString())){
                tip.setText("新密码和旧密码一致，无需修改");
                tip.setVisibility(View.VISIBLE);
            }else{
                tip.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        dialogLoading=new DialogLoading();
        old_password.addTextChangedListener(textWatcher);
        new_password.addTextChangedListener(textWatcher);
        old_password.setKeyPreImeListener(this);
        new_password.setKeyPreImeListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_change;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        old_password.clearFocus();
        new_password.clearFocus();
    }
}
