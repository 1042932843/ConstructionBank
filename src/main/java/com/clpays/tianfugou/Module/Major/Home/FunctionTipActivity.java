package com.clpays.tianfugou.Module.Major.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class FunctionTipActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    @OnClick(R.id.back)
    public void back(){
        finish();

    }

    Intent it;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        it=getIntent();
        String T= it.getStringExtra("Title");
        title.setText(T);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tip_function;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

}
