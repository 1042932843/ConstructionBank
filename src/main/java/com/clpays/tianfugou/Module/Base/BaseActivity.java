package com.clpays.tianfugou.Module.Base;


import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Name: BaseActivity
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-06-25 21:20
 */
public abstract class BaseActivity extends RxAppCompatActivity {

  private Unbinder bind;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //设置布局内容
    setContentView(getLayoutId());
    //初始化黄油刀控件绑定框架
    bind = ButterKnife.bind(this);
    //初始化控件
    initViews(savedInstanceState);
    //初始化ToolBar
    initToolBar();
    loadData();
  }


  public abstract int getLayoutId();

  public abstract void initViews(Bundle savedInstanceState);

  public abstract void initToolBar();

  public void loadData() {}

  public void showProgressBar() {}

  public void hideProgressBar() {}

  public void initRefreshLayout() {}



  @Override
  protected void onDestroy() {
    super.onDestroy();
    bind.unbind();

  }
}
