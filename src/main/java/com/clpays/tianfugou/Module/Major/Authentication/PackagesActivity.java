package com.clpays.tianfugou.Module.Major.Authentication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.clpays.tianfugou.Module.Base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

public class PackagesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;


    @Override
    public void loadData(){


        /*for(int i = 0; i < adapter.getGroupCount(); i++){
            expandableListView.expandGroup(i);
        }*/
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_packages;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void initToolBar() {

    }

}
