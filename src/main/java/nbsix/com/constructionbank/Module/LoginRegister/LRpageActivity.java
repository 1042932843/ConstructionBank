package nbsix.com.constructionbank.Module.LoginRegister;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.VersionUpdate.entity.VersionUpdateConfig;
import nbsix.com.constructionbank.Design.TimeButton.TimeButton;
import nbsix.com.constructionbank.Design.keyEditText.KeyEditText;
import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.Module.Major.Authentication.StartAuthenticationActivity;
import nbsix.com.constructionbank.Module.Major.Home.HomePageActivity;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;
import nbsix.com.constructionbank.Utils.TextToSpeechUtil;
import nbsix.com.constructionbank.Utils.ToastUtil;
import nbsix.com.constructionbank.Utils.UserState;

public class LRpageActivity extends BaseActivity implements KeyEditText.KeyPreImeListener {
    boolean isNext=false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.username)
    KeyEditText username;
    @BindView(R.id.password)
    KeyEditText password;

    @BindView(R.id.nickname)
    KeyEditText nickname;
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
        ToastUtil.ShortToast("获取验证码");
    }
    @BindView(R.id.next_step)
    Button next_step;
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
        register_next_layout.setVisibility(View.GONE);
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
        if(isNext){
            next();
        }else{
            previous();
        }
    }

    @OnClick(R.id.next_step)
    public void next(){
        register_layout.setVisibility(View.GONE);
        register_next_layout.setVisibility(View.VISIBLE);
        isNext=true;
    }


    @OnClick(R.id.previous)
    public void previous(){
        register_layout.setVisibility(View.VISIBLE);
        register_next_layout.setVisibility(View.GONE);
        isNext=false;
    }

    @OnClick(R.id.register)
    public void do_register(){

    }

    @OnClick(R.id.login_btn)
    public void do_login(){
        afterlogin(1);
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

    @BindView(R.id.register_next_layout)
    LinearLayout register_next_layout;

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

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lrpage;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        identifying_code_but.setTextAfter("秒重新获取").setTextBefore("获取验证码").setLength(60 * 1000);
        back.setVisibility(View.GONE);
        username.setKeyPreImeListener(this);
        password.setKeyPreImeListener(this);
        nickname.setKeyPreImeListener(this);
        my_password.setKeyPreImeListener(this);
        phone.setKeyPreImeListener(this);
        identifying_code.setKeyPreImeListener(this);
        phone.addTextChangedListener(textWatcher);
        identifying_code.addTextChangedListener(textWatcher);
        phone.setOnFocusChangeListener(onFocusChangeListener);
        identifying_code.setOnFocusChangeListener(onFocusChangeListener);
        nickname.setOnFocusChangeListener(onFocusChangeListener);
        my_password.setOnFocusChangeListener(onFocusChangeListener);
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        nickname.addTextChangedListener(textWatcher);
        my_password.addTextChangedListener(textWatcher);
        username.setOnFocusChangeListener(onFocusChangeListener);
        password.setOnFocusChangeListener(onFocusChangeListener);
        SpannableStringBuilder builder = new SpannableStringBuilder(user_agreement.getText().toString());
        ForegroundColorSpan Span = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorPrimary));
        builder.setSpan(Span, 16,20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        user_agreement.setText(builder);


        if(UserState.isLogin()&&!UserState.isAuthentication()){
            afterlogin(1);
        }
        if(UserState.isLogin()&&UserState.isAuthentication()){
            afterlogin(2);
        }

        showNormalDialog();

    }


    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        //normalDialog.setIcon(R.mipmap.launcher);
        normalDialog.setTitle("版本升级");
        normalDialog.setMessage("检查到更新,是否进行升级？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    private String url = "http://link.moobplayer.com/download2/m001.apk";
    public void update(){
        VersionUpdateConfig.getInstance()//获取配置实例
                .setContext(this)//设置上下文
                .setDownLoadURL(url)//设置文件下载链接
                .setNotificationIconRes(R.mipmap.launcher)//设置通知大图标
                .setNotificationSmallIconRes(R.mipmap.launcher)//设置通知小图标
                .setNotificationTitle("版本升级")//设置通知标题
                .startDownLoad();//开始下载
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
            next_step.setEnabled(phone.getText().length() != 0 && identifying_code.getText().length() != 0);
            register.setEnabled(nickname.getText().length() != 0 && my_password.getText().length() != 0);
        }
    };



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
            //登录了未认证
            case 1:
                it.setClass(this,StartAuthenticationActivity.class);
                startActivity(it);
                break;
            //登录了已认证
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
        nickname.clearFocus();
        my_password.clearFocus();
    }
}
