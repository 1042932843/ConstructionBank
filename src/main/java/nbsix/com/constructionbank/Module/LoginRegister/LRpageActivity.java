package nbsix.com.constructionbank.Module.LoginRegister;

import android.content.Intent;

import android.support.design.widget.AppBarLayout;

import android.support.v4.content.ContextCompat;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nbsix.com.constructionbank.Design.Dialog.DialogLoading;
import nbsix.com.constructionbank.Design.TimeButton.TimeButton;
import nbsix.com.constructionbank.Design.keyEditText.KeyEditText;
import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.Module.Major.Authentication.StartAuthenticationActivity;
import nbsix.com.constructionbank.Module.Major.Home.HomePageActivity;
import nbsix.com.constructionbank.Network.RequestProperty;
import nbsix.com.constructionbank.Network.RetrofitHelper;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.LogUtil;
import nbsix.com.constructionbank.Utils.PreferenceUtil;
import nbsix.com.constructionbank.Utils.SystemBarHelper;
import nbsix.com.constructionbank.Utils.ToastUtil;
import nbsix.com.constructionbank.Utils.UserState;
import nbsix.com.constructionbank.Utils.tools.isGetStringFromJson;
import nbsix.com.constructionbank.Utils.tools.isJsonObj;

public class LRpageActivity extends BaseActivity implements KeyEditText.KeyPreImeListener {


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
        JsonObject obj=RequestProperty.CreateJsonObjectBody();
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
                        String seconds=isGetStringFromJson.handleData("seconds",isJsonObj.handleData("data",a));
                        int time=Integer.parseInt(seconds);
                        identifying_code_but.setLength(time * 1000).initTimer();
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                }, throwable -> {
                    ToastUtil.ShortToast("数据错误");
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
                    ToastUtil.ShortToast("数据错误");
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
        finish();
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
        if(!UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username",UserState.NA))){
            username.setText(PreferenceUtil.getStringPRIVATE("username",UserState.NA));
        }
        if (!UserState.NA.equals(PreferenceUtil.getStringPRIVATE("password",UserState.NA))&&!UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username",UserState.NA))){
            password.setText(PreferenceUtil.getStringPRIVATE("password",UserState.NA));
            login(PreferenceUtil.getStringPRIVATE("username",UserState.NA),PreferenceUtil.getStringPRIVATE("password",UserState.NA));
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
            identifying_code_but.setEnabled(phone.getText().length() != 0);
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

                        PreferenceUtil.putStringPRIVATE("username",username);
                        PreferenceUtil.putStringPRIVATE("password",pwd);
                        PreferenceUtil.putStringPRIVATE("token",token);
                        PreferenceUtil.putStringPRIVATE("status",status);
                        CheakStatus();
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    dialogLoading.dismiss();
                    ToastUtil.ShortToast("数据错误");
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
            case"profile":
                afterlogin(1);
                break;
            case"2":
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
                break;
            //已经认证
            case 2:
                it.setClass(this,HomePageActivity.class);
                startActivity(it);
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
}
