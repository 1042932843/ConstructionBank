package com.clpays.tianfugou.Module.Major.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.clpays.tianfugou.Adapter.PackagesAdapter.MainAdapter;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.myExpandableListview.CustomExpandableListView;
import com.clpays.tianfugou.Design.myScrollView.myScrollView;
import com.clpays.tianfugou.Entity.PackageChoice.FirstBean;
import com.clpays.tianfugou.Entity.PackageChoice.SecondBean;
import com.clpays.tianfugou.Entity.PackageChoice.ThirdBean;
import com.clpays.tianfugou.Module.Base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class PackagesActivity extends BaseActivity implements ExpandableListView.OnGroupExpandListener,
        MainAdapter.OnExpandClickListener{
    DialogLoading dialogLoading;
    int Package=0;
    boolean isSvToBottom;
    float mLastY;
    int THRESHOLD_Y_LIST_VIEW = 20;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.scrollView)
    myScrollView scrollView;

    @BindView(R.id.posLayout)
    RelativeLayout posLayout;
    @BindView(R.id.comLayout)
    RelativeLayout comLayout;
    @BindView(R.id.personLayout)
    RelativeLayout personLayout;
    @BindView(R.id.shopLayout)
    RelativeLayout shopLayout;

    @BindView(R.id.person)
    CheckBox person;
    @BindView(R.id.com)
    CheckBox com;
    @BindView(R.id.pos)
    CheckBox pos;
    @BindView(R.id.shop)
    CheckBox shop;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @OnClick(R.id.next_step)
    public void next(){

       if(Package==0){
           ToastUtil.ShortToast("请选择对应套餐");
           return;
       }
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        obj.addProperty("package",Package);
        JsonArray jsonArray = new JsonArray();
        if(person.isChecked()){
            jsonArray.add(1);
        }
        if(com.isChecked()){
            jsonArray.add(2);
        }
        if(pos.isChecked()){
            jsonArray.add(3);
        }
        if(shop.isChecked()){
            jsonArray.add(4);
        }
        jsonArray.add(5);
        if(jsonArray.size()<=1){
            ToastUtil.ShortToast("请选择套餐需求内容");
            return;
        }
        obj.add("selected",jsonArray);
        dialogLoading.setMessage("资料提交中");
        dialogLoading.show(getSupportFragmentManager(),DialogLoading.TAG);
        RetrofitHelper.getPackageAPI()
                .pushpackage(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("证照上传"));
                        finish();
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    dialogLoading.dismiss();
                    //ToastUtil.ShortToast("数据错误");
                });

    }

    @BindView(R.id.expandableListView)
    CustomExpandableListView expandableListView;

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
                        secondBean.setTitle("免费开立银行个人账户");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==1){
                        secondBean.setTitle("优惠开立银行对公账户");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==2){
                        secondBean.setTitle("优惠办理银行无抵押信用贷款");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }
                    secondBeanArrayList.add(secondBean);
                }
            }else if(i==1){
                firstBean.setTitle("套餐二:免费开立银行个人账户+优惠开立银行对公账户+免费赠送银行POS+优惠办理银行无抵押信用贷款");
                for(int j=0;j<4;j++){
                    SecondBean secondBean=new SecondBean();
                    ArrayList<ThirdBean> thirdBeanArrayList=new ArrayList<>();
                    if(j==0){
                        secondBean.setTitle("免费开立银行个人账户");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==1){
                        secondBean.setTitle("优惠开立银行对公账户");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==2){
                        secondBean.setTitle("优惠办理银行无抵押信用贷款");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==3){
                        secondBean.setTitle("免费赠送银行POS");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("weqwe");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }
                    secondBeanArrayList.add(secondBean);
                }
            }else if(i==2){
                firstBean.setTitle("套餐三:免费开立银行个人账户+优惠开立银行对公账户+免费赠送银行POS+免费入驻建行善融商城+优惠办理银行无抵押信用贷款");
                for(int j=0;j<5;j++){
                    SecondBean secondBean=new SecondBean();
                    ArrayList<ThirdBean> thirdBeanArrayList=new ArrayList<>();
                    ThirdBean thirdBean=new ThirdBean();
                    switch (j){
                        case 0:
                            secondBean.setTitle("免费开立银行个人账户");
                            thirdBean.setTitle("经济观察报 记者 李意安 如果时间回到2012年，你可能不曾想过，身上不带一分钱一张卡就能畅通无阻地在一个城市生活一周、一个月甚至更长的时间。是的，仅仅五年后的今天，这已经成为许多人的生活方式。无论是餐厅吃饭、超市购物、搭乘公交，还是市场买菜，只需要拿上手机就能轻松支付。\n" +
                                    "变化可能不仅于此，当你跨出国门，诸如银联、支付宝、微信支付这样的字眼不仅在日本、东南亚等地随处可见，即使远及欧洲、美国，这些熟悉的品牌也已广泛覆盖。\n" +
                                    "就在刚刚过去的十一黄金周期间，支付宝方面发布统计数据显示，在境外用支付宝付款的人次同比激增七倍多，人均消费金额达1480元。同时，约有370万用户在境外使用支付宝查找当地的吃喝玩乐信息和商家优惠。而此前银联2016年的年报显示，截至2016年末，银联受理网络延伸至160多个国家地区，境外商户累计达到1986万户，累计发卡6800万张，欧洲受理网络覆盖率已经达到50%。");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 1:
                            secondBean.setTitle("优惠开立银行对公账户");
                            thirdBean.setTitle("weqwe");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 2:
                            secondBean.setTitle("免费赠送银行POS");
                            thirdBean.setTitle("weqwe");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 3:
                            secondBean.setTitle("免费入住建行善融商城");
                            thirdBean.setTitle("weqwe");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 4:
                            secondBean.setTitle("优惠办理银行无抵押信用贷款");
                            thirdBean.setTitle("weqwe");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
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
        dialogLoading=new DialogLoading();
        mainAdapter = new MainAdapter(this,mDatas);
        expandableListView.setAdapter(mainAdapter);
        //设置点击父控件的监听
        expandableListView.setOnGroupExpandListener(this);
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if(Package==groupPosition+1){
                    Package=0;
                    posLayout.setVisibility(View.GONE);
                    comLayout.setVisibility(View.GONE);
                    personLayout.setVisibility(View.GONE);
                    shopLayout.setVisibility(View.GONE);
                }

            }
        });
        //点击最里面的菜单的点击事件
        mainAdapter.setOnChildListener(this);
        //将滑动事件交给子控件
        /*expandableListView.setOnTouchListener(new View.OnTouchListener() {
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
                    }
                }
                return false;
            }
        });*/


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
        Package=groupPosition+1;
        if(Package==1){
            comLayout.setVisibility(View.VISIBLE);
            personLayout.setVisibility(View.VISIBLE);
        }
        else if(Package==2){
            posLayout.setVisibility(View.VISIBLE);
            comLayout.setVisibility(View.VISIBLE);
            personLayout.setVisibility(View.VISIBLE);
        }
        else if(Package==3){
            posLayout.setVisibility(View.VISIBLE);
            comLayout.setVisibility(View.VISIBLE);
            personLayout.setVisibility(View.VISIBLE);
            shopLayout.setVisibility(View.VISIBLE);
        }else{
            posLayout.setVisibility(View.GONE);
            comLayout.setVisibility(View.GONE);
            personLayout.setVisibility(View.GONE);
            shopLayout.setVisibility(View.GONE);
        }

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
