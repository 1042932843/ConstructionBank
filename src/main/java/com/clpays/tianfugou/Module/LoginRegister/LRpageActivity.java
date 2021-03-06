package com.clpays.tianfugou.Module.LoginRegister;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.design.widget.AppBarLayout;

import android.support.v4.content.ContextCompat;

import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.TimeButton.TimeButton;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Module.Major.Authentication.StartAuthenticationActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.clpays.tianfugou.Design.keyEditText.KeyEditText;

import com.clpays.tianfugou.Utils.LogUtil;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.net.SocketTimeoutException;

public class LRpageActivity extends BaseActivity implements KeyEditText.KeyPreImeListener {
    public static final String TAG = LRpageActivity.class.getSimpleName();
    DialogLoading dialogLoading;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.username)
    KeyEditText username;
    @BindView(R.id.password)
    KeyEditText password;
    @BindView(R.id.my_password)
    KeyEditText my_password;

    @BindView(R.id.phone)
    KeyEditText phone;
    @BindView(R.id.identifying_code)
    KeyEditText identifying_code;
    @BindView(R.id.identifying_code_but)
    TimeButton identifying_code_but;
    @OnClick (R.id.identifying_code_but)
    public void getIdentifying_code(){
        String phoneNum=phone.getText().toString();
        if(phoneNum.isEmpty()){
            ToastUtil.ShortToast("请填写手机号");
            return;
        }
        identifying_code_but.setEnabled(false);
        JsonObject obj= RequestProperty.CreateJsonObjectBody();
        obj.addProperty("phone",phoneNum);
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

    @BindView(R.id.register)
    Button register;

    @BindView(R.id.login_btn)
    Button login_btn;

    @BindView(R.id.app_bar)
    AppBarLayout app_bar;

    @BindView(R.id.type_login)
    TextView type_login;
    @BindView(R.id.type_login_line)
    RelativeLayout login_line;
    @OnClick(R.id.type_login)
    public void setType_login(){
        login_layout.setVisibility(View.VISIBLE);
        register_layout.setVisibility(View.GONE);
        type_login.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        login_line.setVisibility(View.VISIBLE);
        type_register.setTextColor(ContextCompat.getColor(this,R.color.gray_80_alpha_60));
        register_line.setVisibility(View.GONE);
    }

    @BindView(R.id.type_register)
    TextView type_register;
    @BindView(R.id.type_register_line)
    RelativeLayout register_line;
    @OnClick(R.id.type_register)
    public void setType_register(){
        login_layout.setVisibility(View.GONE);
        type_login.setTextColor(ContextCompat.getColor(this,R.color.gray_80_alpha_60));
        login_line.setVisibility(View.GONE);
        type_register.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        register_line.setVisibility(View.VISIBLE);
        register_layout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.register)
    public void do_register(){
        dialogLoading.setMessage("注册中");
        dialogLoading.show(getSupportFragmentManager(),DialogLoading.TAG);
        JsonObject obj=RequestProperty.CreateJsonObjectBody();
        String phoneNum=phone.getText().toString();
        String password=my_password.getText().toString();
        String captcha=identifying_code.getText().toString();
        obj.addProperty("phone",phoneNum);
        obj.addProperty("password",password);
        obj.addProperty("captcha",captcha);
        RetrofitHelper.getLoginRegisterAPI()
                .register(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                        username.setText(phoneNum);
                        setType_login();
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    dialogLoading.dismiss();
                    //ToastUtil.ShortToast("数据错误");
                });
    }

    @OnClick(R.id.login_btn)
    public void do_login(){
        String userName=username.getText().toString();
        String pwd=password.getText().toString();
        login(userName,pwd);


    }
    @BindView(R.id.back)
    ImageView back;
    @OnClick(R.id.back)
    public void back(){
        String s= PreferenceUtil.getStringPRIVATE("token", UserState.NA);
        if(s.isEmpty()||UserState.NA.equals(s)){
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                app.getInstance().Exit();

            }
        }else{
            finish();
        }
    }


    /**
     * RxPermission权限动态申请
     */
    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.requestEach(
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.VIBRATE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS

        )
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        Log.d(TAG, permission.name + " is granted.");

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Log.d(TAG, permission.name + " is denied. More info should be provided.");

                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Log.d(TAG, permission.name + " is denied.");

                    }
                });
    }

    @OnClick(R.id.forget)
    public void forget(){
        String[] items = new String[] {"短信验证找回", "取消" };
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("忘记密码？").setIcon(R.mipmap.launcher)
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        Intent it=new Intent();
                        switch (items[which]){
                            case "通过旧密码修改":
                                it.setClass(LRpageActivity.this,ChangePasswordActivity.class);
                                startActivity(it);
                                break;
                            case "短信验证找回":
                                it.setClass(LRpageActivity.this,FindPasswordActivity.class);
                                startActivity(it);
                                break;
                            case "取消":
                                break;
                        }
                    }
                }).create();
        dialog.show();
    }

    @BindView(R.id.login_layout)
    LinearLayout login_layout;
    @BindView(R.id.register_layout)
    LinearLayout register_layout;

    @BindView(R.id.user_agreement)
    TextView user_agreement;
    @OnClick(R.id.user_agreement)
    public void user_agreement(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        initPermission();
        if(!UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username",UserState.NA))){
            username.setText(PreferenceUtil.getStringPRIVATE("username",UserState.NA));
        }
        if (!UserState.NA.equals(PreferenceUtil.getStringPRIVATE("token",UserState.NA))&&!UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username",UserState.NA))){
            CheakStatus();
        }


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(identifying_code_but!=null){
            identifying_code_but.onDestroy();
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lrpage;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        dialogLoading=new DialogLoading();
        dialogLoading.setCancelable(false);
        back.setVisibility(View.GONE);
        username.setKeyPreImeListener(this);
        password.setKeyPreImeListener(this);
        my_password.setKeyPreImeListener(this);
        phone.setKeyPreImeListener(this);
        identifying_code.setKeyPreImeListener(this);
        phone.addTextChangedListener(textWatcher);
        identifying_code.addTextChangedListener(textWatcher);
        phone.setOnFocusChangeListener(onFocusChangeListener);
        identifying_code.setOnFocusChangeListener(onFocusChangeListener);
        my_password.setOnFocusChangeListener(onFocusChangeListener);
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        my_password.addTextChangedListener(textWatcher);
        username.setOnFocusChangeListener(onFocusChangeListener);
        password.setOnFocusChangeListener(onFocusChangeListener);
        SpannableStringBuilder builder = new SpannableStringBuilder(user_agreement.getText().toString());
        ForegroundColorSpan Span = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorPrimary));
        builder.setSpan(Span, 16,20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        user_agreement.setText(builder);

    }




    private View.OnFocusChangeListener onFocusChangeListener=new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                app_bar.setExpanded(false);
            }

        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            login_btn.setEnabled(username.getText().length() != 0 && password.getText().length() != 0);
            register.setEnabled(phone.getText().length() != 0 && my_password.getText().length() != 0&& identifying_code.getText().length() != 0);
        }
    };


    /**
     * 执行登录
     */
    public void login(String username,String pwd){

        dialogLoading.setMessage("登录中");
        dialogLoading.show(getSupportFragmentManager(),DialogLoading.TAG);
        JsonObject obj=RequestProperty.CreateJsonObjectBody();
        obj.addProperty("phone",username);
        obj.addProperty("password",pwd);
        RetrofitHelper.getLoginRegisterAPI()
                .login(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();//{"success":true,"message":"","data":{"token":"1haL06uZXgHQIT6-0HuZ24Q1eQWjVSN0","status":"\u5b9e\u540d\u8ba4\u8bc1"}}
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        String token=isGetStringFromJson.handleData("token",isJsonObj.handleData("data",a));
                        String status=isGetStringFromJson.handleData("status",isJsonObj.handleData("data",a));
                        //String rid=isGetStringFromJson.handleData("rid",isJsonObj.handleData("data",a));
                        app.getInstance().setAlias(token);//推送ID
                        PreferenceUtil.putStringPRIVATE("username",username);
                        //PreferenceUtil.putStringPRIVATE("password",pwd);
                        PreferenceUtil.putStringPRIVATE("token",token);
                        PreferenceUtil.putStringPRIVATE("status",status);
                        CheakStatus();
                    }else{
                        String e=isGetStringFromJson.handleData("message",a);
                        if(!e.isEmpty()){
                            ToastUtil.ShortToast(e);
                        }

                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    if(throwable instanceof SocketTimeoutException){
                        ToastUtil.ShortToast("服务器连接超时");
                    }
                    dialogLoading.dismiss();

                });
    }

    /**
     * 检查状态
     */
    public void CheakStatus(){
        String s=PreferenceUtil.getStringPRIVATE("status",UserState.NA);
        LogUtil.d(s);
        switch (s){
            case "N/A":
                break;
            case"package":
            case"profile":
            case "upload":
            case "review_profile":
            case "review_upload":
            case "review_profile_upload":
            case "checked":
            case "prepared":
            case "waiting":
            case "makecard":
                afterlogin(1);
                break;
            case"finish":
                afterlogin(2);
                break;
        }
    }

    /**
     * 根据用户状态处理页面跳转
     * @param state
     */
    public void afterlogin(int state){
        Intent it=new Intent();
        switch (state){
            //未登录
            case 0:
                break;
            //需要实名认证
            case 1:
                it.setClass(this,StartAuthenticationActivity.class);
                startActivity(it);
                finish();
                break;
            //已经认证
            case 2:
                finish();
                break;
        }

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        app_bar.setExpanded(true);
        username.clearFocus();
        password.clearFocus();
        phone.clearFocus();
        identifying_code.clearFocus();
        my_password.clearFocus();
    }

    private long exitTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            String s= PreferenceUtil.getStringPRIVATE("token", UserState.NA);
            //LogUtil.d(s);
            if(s.isEmpty()||UserState.NA.equals(s)){
                if (System.currentTimeMillis() - exitTime > 2000) {
                    Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    app.getInstance().Exit();
                }
                return true;
            }else{
                finish();
            }

        }

        return super.onKeyDown(keyCode, event);
    }
}
