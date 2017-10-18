package com.clpays.tianfugou.Module.Major.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.clpays.tianfugou.Utils.tools.isGetBooleanFromJson;
import com.clpays.tianfugou.Utils.tools.isGetJsonArrayFromJson;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
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
    @BindView(R.id.accountLayout)
    LinearLayout accountLayout;
    @BindView(R.id.title_c)
    TextView title_c;

    @BindView(R.id.person)
    CheckBox person;
    @BindView(R.id.com)
    CheckBox com;
    @BindView(R.id.pos)
    CheckBox pos;
    @BindView(R.id.account)
    CheckBox account;


    @OnClick(R.id.back)
    public void back(){
        EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("基本资料"));
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
        if(!person.isChecked()&&!com.isChecked()){
            ToastUtil.ShortToast("个人开户和单位开户必须至少选择一个！");
            return;
        }
        if(person.isChecked()){
            jsonArray.add(1);
        }
        if(com.isChecked()){
            jsonArray.add(2);
        }
        if(pos.isChecked()&&Package!=1){
            jsonArray.add(3);
        }

        if(account.isChecked()&&com.isChecked()){
            obj.addProperty("isnewbank",true);
        }else{
            obj.addProperty("isnewbank",false);
        }
        if(Package==3){
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
                        thirdBean.setTitle("建行专门为成都国际商贸城的入驻商家打造一款银行储蓄卡——惠福龙卡，它不仅具备普通储蓄卡的所有基本功能，还增加了结算优惠、存款增值等特色功能：\n" +
                                "免开立工本费和每年的账户管理费；\n" +
                                "享受活期便利和定期收益；\n" +
                                "全国境内银行系统内存取现和转账免手续费；\n" +
                                "截至2017年12月31日全国跨行取现免手续费；\n" +
                                "银行手机银行跨行转账免手续费；使用手机银行以外的渠道跨行转账，每年免12笔转账手续费。");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==1){
                        secondBean.setTitle("优惠开立银行对公账户");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("建行对公结算账户既是各商家日常交易结算所需，账户上的交易流水、资金沉淀和缴税金额等更是申请建行无抵押信用贷款的必要条件，建行本次仅针对商贸城入驻商家提供666元对公开户大礼包（原价1360元），优惠幅度达5折：\n" +
                                "支付密码器免费赠送；\n" +
                                "网银优惠办理（原价480元/年）；\n" +
                                "结算卡优惠办理（原价300元/年）；\n" +
                                "回单卡优惠办理（原价360元/年）；\n" +
                                "短信通知优惠办理（原价96元/年）；\n" +
                                "账户管理费优惠办理（原价360元/年）；");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==2){
                        secondBean.setTitle("优惠办理银行无抵押信用贷款");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("贷款特点：依据商贸城商家在建行账户上的交易流水、存款余额或商家的缴税金额核定贷款金额，不定期限、随借随还的信用贷款，支持网上申请和归还，最高额度200万元，日利率低于万分之2。");
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
                        thirdBean.setTitle("建行专门为成都国际商贸城的入驻商家打造一款银行储蓄卡——惠福龙卡，它不仅具备普通储蓄卡的所有基本功能，还增加了结算优惠、存款增值等特色功能：\n" +
                                "免开立工本费和每年的账户管理费；\n" +
                                "享受活期便利和定期收益；\n" +
                                "全国境内银行系统内存取现和转账免手续费；\n" +
                                "截至2017年12月31日全国跨行取现免手续费；\n" +
                                "银行手机银行跨行转账免手续费；使用手机银行以外的渠道跨行转账，每年免12笔转账手续费。");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==1){
                        secondBean.setTitle("优惠开立银行对公账户");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("建行对公结算账户既是各商家日常交易结算所需，账户上的交易流水、资金沉淀和缴税金额等更是申请建行无抵押信用贷款的必要条件，建行本次仅针对商贸城入驻商家提供666元对公开户大礼包（原价1360元），优惠幅度达5折：\n" +
                                "支付密码器免费赠送；\n" +
                                "网银优惠办理（原价480元/年）；\n" +
                                "结算卡优惠办理（原价300元/年）；\n" +
                                "回单卡优惠办理（原价360元/年）；\n" +
                                "短信通知优惠办理（原价96元/年）；\n" +
                                "账户管理费优惠办理（原价360元/年）；");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==2){
                        secondBean.setTitle("优惠办理银行无抵押信用贷款");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("贷款特点：依据商贸城商家在建行账户上的交易流水、存款余额或商家的缴税金额核定贷款金额，不定期限、随借随还的信用贷款，支持网上申请和归还，最高额度200万元，日利率低于万分之2。");
                        thirdBeanArrayList.add(thirdBean);
                        secondBean.setSecondBean(thirdBeanArrayList);
                    }else if(j==3){
                        secondBean.setTitle("免费赠送银行POS");
                        ThirdBean thirdBean=new ThirdBean();
                        thirdBean.setTitle("建行将为商家免费赠送带扫码功能固定POS。支持微信、支付宝和各家银行卡支付。固定POS提供公网通讯模式，速度更快，更可节约通讯费用。免费提供POS用纸。若商家营业执照经营范围有商品批发，可给予特殊费率，收费标准为：\n" +
                                "借记卡：0.5%，单笔20元封顶\n" +
                                "贷记卡：0.5%，本行信用卡单笔20元封顶，他行信用卡不封顶\n" +
                                "微信、支付宝：0.5%。\n" +
                                "建行POS四大优势：\n" +
                                "1)\t节约商家的柜台空间\n" +
                                "建行POS可以帮助商家将POS刷卡、微信二维码、支付宝二维码、银联扫码购等统统合并，只需要一台互联网POS即可完成所有支付需求。\n" +
                                "2)\t节约通信费\n" +
                                "建行POS采用互联网通信，只产生额外通信费用，每天交易30笔，每月节约200元。\n" +
                                "3)\t真正的低成本获客，一次使用终生不忘\n" +
                                "建行POS为商家免费提供微信公众号获客平台，将商家的照片、介绍提供给客户，还可以定位和导航哟！！\n" +
                                "建行POS为商家免费开放后台系统，客户使用建行POS完成交易后将客户信息实时推送至商家的后台，商家还可以添加备注，记录客户消费习惯、喜爱等信息，为二次营销提供帮助。\n" +
                                "4)\t免费的营销推广平台，兜住客户没商量\n" +
                                "建行POS为商家提供建行免费推广平台，商家可自主发放各种优惠券，所有关注建行公众号的客户均可领取商家发放的优惠券，将天南海北的朋友聚集到商家身边。");
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
                            thirdBean.setTitle("建行专门为成都国际商贸城的入驻商家打造一款银行储蓄卡——惠福龙卡，它不仅具备普通储蓄卡的所有基本功能，还增加了结算优惠、存款增值等特色功能：\n" +
                                    "免开立工本费和每年的账户管理费；\n" +
                                    "享受活期便利和定期收益；\n" +
                                    "全国境内银行系统内存取现和转账免手续费；\n" +
                                    "截至2017年12月31日全国跨行取现免手续费；\n" +
                                    "银行手机银行跨行转账免手续费；使用手机银行以外的渠道跨行转账，每年免12笔转账手续费。");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 1:
                            secondBean.setTitle("优惠开立银行对公账户");
                            thirdBean.setTitle("建行对公结算账户既是各商家日常交易结算所需，账户上的交易流水、资金沉淀和缴税金额等更是申请建行无抵押信用贷款的必要条件，建行本次仅针对商贸城入驻商家提供666元对公开户大礼包（原价1360元），优惠幅度达5折：\n" +
                                    "支付密码器免费赠送；\n" +
                                    "网银优惠办理（原价480元/年）；\n" +
                                    "结算卡优惠办理（原价300元/年）；\n" +
                                    "回单卡优惠办理（原价360元/年）；\n" +
                                    "短信通知优惠办理（原价96元/年）；\n" +
                                    "账户管理费优惠办理（原价360元/年）；");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 2:
                            secondBean.setTitle("免费赠送银行POS");
                            thirdBean.setTitle("建行将为商家免费赠送带扫码功能固定POS。支持微信、支付宝和各家银行卡支付。固定POS提供公网通讯模式，速度更快，更可节约通讯费用。免费提供POS用纸。若商家营业执照经营范围有商品批发，可给予特殊费率，收费标准为：\n" +
                                    "借记卡：0.5%，单笔20元封顶\n" +
                                    "贷记卡：0.5%，本行信用卡单笔20元封顶，他行信用卡不封顶\n" +
                                    "微信、支付宝：0.5%。\n" +
                                    "建行POS四大优势：\n" +
                                    "1)\t节约商家的柜台空间\n" +
                                    "建行POS可以帮助商家将POS刷卡、微信二维码、支付宝二维码、银联扫码购等统统合并，只需要一台互联网POS即可完成所有支付需求。\n" +
                                    "2)\t节约通信费\n" +
                                    "建行POS采用互联网通信，只产生额外通信费用，每天交易30笔，每月节约200元。\n" +
                                    "3)\t真正的低成本获客，一次使用终生不忘\n" +
                                    "建行POS为商家免费提供微信公众号获客平台，将商家的照片、介绍提供给客户，还可以定位和导航哟！！\n" +
                                    "建行POS为商家免费开放后台系统，客户使用建行POS完成交易后将客户信息实时推送至商家的后台，商家还可以添加备注，记录客户消费习惯、喜爱等信息，为二次营销提供帮助。\n" +
                                    "4)\t免费的营销推广平台，兜住客户没商量\n" +
                                    "建行POS为商家提供建行免费推广平台，商家可自主发放各种优惠券，所有关注建行公众号的客户均可领取商家发放的优惠券，将天南海北的朋友聚集到商家身边。");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 3:
                            secondBean.setTitle("免费入住建行善融商城");
                            thirdBean.setTitle("建行将为符合条件的入驻商家提供我行善融商务B2B和B2C线上交易平台，多渠道获客，并对商家提供免费宣传，优先开展联合促销。\n" +
                                    "优惠：\n" +
                                    "平台费用免收；\n" +
                                    "交易费率：贷记卡交易——0.5%");
                            thirdBeanArrayList.add(thirdBean);
                            secondBean.setSecondBean(thirdBeanArrayList);
                            break;
                        case 4:
                            secondBean.setTitle("优惠办理银行无抵押信用贷款");
                            thirdBean.setTitle("贷款特点：依据商贸城商家在建行账户上的交易流水、存款余额或商家的缴税金额核定贷款金额，不定期限、随借随还的信用贷款，支持网上申请和归还，最高额度200万元，日利率低于万分之2。");
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
        fetch();

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    //加载数据
    public void fetch(){
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        RetrofitHelper.getPackageAPI()
                .fetchpackage(obj)
                //.compose(this.bindToLifecycle())这里因为在不可见情况下更新页面，所以不能绑定生命周期
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        String Package=isGetStringFromJson.handleData("package", isJsonObj.handleData("data",a));
                        boolean isnewbank= isGetBooleanFromJson.handleData("isnewbank", isJsonObj.handleData("data",a));
                        JsonArray selected= isGetJsonArrayFromJson.handleData("selected", isJsonObj.handleData("data",a));
                        switch (Integer.parseInt(Package)){
                            case 1:
                                expandableListView.expandGroup(0);
                                break;
                            case 2:
                                expandableListView.expandGroup(1);
                                break;
                            case 3:
                                expandableListView.expandGroup(2);
                                break;
                        }

                        account.setChecked(isnewbank);

                        for(int i=0;i<selected.size();i++){
                            switch (selected.get(i).getAsInt()){
                                case 1:
                                    person.setChecked(true);
                                    break;
                                case 2:
                                    com.setChecked(true);
                                    break;
                                case 3:
                                    pos.setChecked(true);
                                    break;
                                case 4:
                                    break;
                            }
                        }

                    }else{
                        //ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }

                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });
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
        com.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    accountLayout.setVisibility(View.VISIBLE);
                    account.setChecked(true);
                }else{
                    accountLayout.setVisibility(View.GONE);
                    account.setChecked(false);
                }
            }
        });
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
                    title_c.setVisibility(View.GONE);
                    posLayout.setVisibility(View.GONE);
                    comLayout.setVisibility(View.GONE);
                    personLayout.setVisibility(View.GONE);
                    accountLayout.setVisibility(View.GONE);
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
            title_c.setVisibility(View.VISIBLE);
            comLayout.setVisibility(View.VISIBLE);
            personLayout.setVisibility(View.VISIBLE);
            posLayout.setVisibility(View.GONE);
        }
        else if(Package==2){
            title_c.setVisibility(View.VISIBLE);
            posLayout.setVisibility(View.VISIBLE);
            comLayout.setVisibility(View.VISIBLE);
            personLayout.setVisibility(View.VISIBLE);
        }
        else if(Package==3){
            title_c.setVisibility(View.VISIBLE);
            posLayout.setVisibility(View.VISIBLE);
            comLayout.setVisibility(View.VISIBLE);
            personLayout.setVisibility(View.VISIBLE);
        }else{
            title_c.setVisibility(View.GONE);
            posLayout.setVisibility(View.GONE);
            comLayout.setVisibility(View.GONE);
            personLayout.setVisibility(View.GONE);
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
