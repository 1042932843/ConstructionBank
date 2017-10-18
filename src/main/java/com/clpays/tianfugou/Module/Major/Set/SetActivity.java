package com.clpays.tianfugou.Module.Major.Set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.ToastUtil;

public class SetActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @OnClick(R.id.clear)
    public void clear(){
        app.getInstance().delAlias();
        PreferenceUtil.resetPrivate();
        Intent it=new Intent(this, LRpageActivity.class);
        startActivity(it);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

}
