package com.clpays.tianfugou.Module.Major.Authentication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ScrollView;

import com.clpays.tianfugou.Adapter.PackagesAdapter.MainAdapter;
import com.clpays.tianfugou.Design.myScrollView.myScrollView;
import com.clpays.tianfugou.Entity.PackageChoice.FirstBean;
import com.clpays.tianfugou.Entity.PackageChoice.SecondBean;
import com.clpays.tianfugou.Entity.PackageChoice.ThirdBean;
import com.clpays.tianfugou.Module.Base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import java.util.ArrayList;
import java.util.List;

public class PackagesActivity extends BaseActivity implements ExpandableListView.OnGroupExpandListener,
        MainAdapter.OnExpandClickListener{

    boolean isSvToBottom;
    float mLastY;
    int THRESHOLD_Y_LIST_VIEW = 20;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.scrollView)
    myScrollView scrollView;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;

    MainAdapter mainAdapter;

    private ArrayList<FirstBean> mDatas = new ArrayList<FirstBean>();
    @Override
    public void loadData(){
        for(int i=0;i<3;i++){
            FirstBean firstBean = new FirstBean();
            ArrayList<SecondBean> secondBeanArrayList = new ArrayList<SecondBean>();
            if(i==0){
                firstBean.setTitle("套餐一:免费开立银行个人账户+优惠开立银行对公账户+优惠办理银行无抵押信用贷款");
                for(int j=0;j<3;j++){
                    SecondBean secondBean=new SecondBean();
                    ArrayList<ThirdBean> thirdBeanArrayList=new ArrayList<>();
                    if(j==0){
                        secondBean.setTitle("开立个人结算账户——银行惠福龙卡（免费）");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==1){
                        secondBean.setTitle("开立对公结算账户——享受5折价格优惠");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==2){
                        secondBean.setTitle("优惠办理银行无抵押信用贷款——无需担保抵押，最高额度达200万元");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }
                    secondBeanArrayList.add(secondBean);
                }
            }else if(i==1){
                firstBean.setTitle("套餐二:免费开立银行个人账户+优惠开立银行对公账户+免费赠送银行POS+优惠办理银行无抵押信用贷款");
                for(int j=0;j<3;j++){
                    SecondBean secondBean=new SecondBean();
                    ArrayList<ThirdBean> thirdBeanArrayList=new ArrayList<>();
                    if(j==0){
                        secondBean.setTitle("开立个人结算账户——银行惠福龙卡（免费）");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==1){
                        secondBean.setTitle("开立对公结算账户——享受5折价格优惠");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==2){
                        secondBean.setTitle("优惠办理银行无抵押信用贷款——无需担保抵押，最高额度达200万元");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }
                    secondBeanArrayList.add(secondBean);
                }
            }else if(i==2){
                firstBean.setTitle("套餐三:免费开立银行个人账户+优惠开立银行对公账户+免费赠送银行POS+免费入驻建行善融商城+优惠办理银行无抵押信用贷款");
                for(int j=0;j<3;j++){
                    SecondBean secondBean=new SecondBean();
                    ArrayList<ThirdBean> thirdBeanArrayList=new ArrayList<>();
                    if(j==0){
                        secondBean.setTitle("开立个人结算账户——银行惠福龙卡（免费）");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==1){
                        secondBean.setTitle("开立对公结算账户——享受5折价格优惠");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==2){
                        secondBean.setTitle("优惠办理银行无抵押信用贷款——无需担保抵押，最高额度达200万元");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }
                    secondBeanArrayList.add(secondBean);
                }
            }

            firstBean.setFirstData(secondBeanArrayList);
            mDatas.add(firstBean);


        }

        mainAdapter.notifyDataSetChanged();

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
        mainAdapter = new MainAdapter(this,mDatas);
        expandableListView.setAdapter(mainAdapter);
        //设置点击父控件的监听
        expandableListView.setOnGroupExpandListener(this);
        //点击最里面的菜单的点击事件
        mainAdapter.setOnChildListener(this);
        //将滑动事件交给子控件
        expandableListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    mLastY = event.getY();
                }
                if(action == MotionEvent.ACTION_MOVE) {
                    int top = expandableListView.getChildAt(0).getTop();
                    float nowY = event.getY();
                    scrollView.requestDisallowInterceptTouchEvent(true);
                   /* if(!isSvToBottom) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        scrollView.requestDisallowInterceptTouchEvent(false);
                    } else if(top == 0 && nowY - mLastY > THRESHOLD_Y_LIST_VIEW) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        scrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        // 不允许scrollview拦截点击事件， expandableListView滑动
                        scrollView.requestDisallowInterceptTouchEvent(true);
                    }*/
                }
                return false;
            }
        });


        scrollView.setScrollToBottomListener(new myScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollToBottom() {
                isSvToBottom = true;
            }

            @Override
            public void onNotScrollToBottom() {
                isSvToBottom = false;
            }
        });
    }

    @Override
    public void initToolBar() {

    }


    /**
     * 保证listview只展开一项
     * @param groupPosition
     */
    @Override
    public void onGroupExpand(int groupPosition) {
        Log.e("xxx","onGroupExpand>>"+groupPosition);
        for (int i = 0; i < mDatas.size(); i++) {
            if (i != groupPosition) {
                expandableListView.collapseGroup(i);
            }
        }
    }
    /***
     * 点击最次级菜单的点击事件
     * @param parentPosition
     * @param childPosition
     * @param childIndex
     */
    @Override
    public void onclick(int parentPosition, int childPosition, int childIndex) {
        Log.e("xxx","点了"+"parentPosition>>"+"childPosition>>"+childPosition+
                "childIndex>>"+childIndex);
    }
}
