package com.clpays.tianfugou.Module.LoginRegister;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class RetrievepasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
        if(step==1){
            finish();
        }else{
            step=step-1;
            changeStep();
        }


    }

    private void changeStep() {
        switch (step){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public int step=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_retrieve;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

}
