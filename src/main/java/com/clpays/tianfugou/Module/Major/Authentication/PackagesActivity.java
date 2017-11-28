package com.clpays.tianfugou.Module.Major.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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
import com.clpays.tianfugou.Adapter.PackagesAdapter.PackagesExpandableListViewAdapter;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.myExpandableListview.CustomExpandableListView;
import com.clpays.tianfugou.Design.myScrollView.myScrollView;
import com.clpays.tianfugou.Entity.PackageChoice.FirstBean;
import com.clpays.tianfugou.Entity.PackageChoice.NewPackagesBean;
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
import com.clpays.tianfugou.Utils.GsonUtil;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetBooleanFromJson;
import com.clpays.tianfugou.Utils.tools.isGetJsonArrayFromJson;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class PackagesActivity extends BaseActivity{
    DialogLoading dialogLoading;


    private ArrayList<NewPackagesBean> mDatas = new ArrayList<NewPackagesBean>();
    PackagesExpandableListViewAdapter packagesExpandableListViewAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.isnew)
    RelativeLayout isnew;

    @BindView(R.id.account)
    CheckBox account;

    @OnClick(R.id.back)
    public void back(){
        EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("基本资料"));
        finish();
    }

    @OnClick(R.id.next_step)
    public void next(){

        JsonArray jsonArray = new JsonArray();
        for(int i=0;i<mDatas.size();i++){
            if(mDatas.get(i).isChoice()){
             String id=  mDatas.get(i).getId();
                jsonArray.add(id);
            }

        }

        if(jsonArray.size()<=0){
            ToastUtil.ShortToast("请选择相应服务后提交");
            return;
        }

        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        obj.addProperty("isnewbank",packagesExpandableListViewAdapter.isnewbank());
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

    //MainAdapter mainAdapter;


    @Override
    public void loadData(){
        fetch();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    boolean isnewbank;
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
                        //{"success":true,"message":"","data":{"selected":["1"],"isnewbank":false,"packages":{"1":{"id":"1","name":"\u514d\u8d39\u83b7\u5f97\u4e2a\u4eba\u6536\u6b3e\u94f6\u884c\u8d26\u6237","intro":"","related":null},"2":{"id":"2","name":"\u4f18\u60e0\u5f00\u7acb\u5bf9\u516c\u6536\u6b3e\u8d26\u6237","intro":"","related":"4"},"3":{"id":"3","name":"\u514d\u8d39\u83b7\u53d6\u667a\u80fdPOS\u673a","intro":"","related":null},"4":{"id":"4","name":"\u514d\u8d39\u5165\u4f4f\u5efa\u884c\u5584\u878d\u5546\u57ce","intro":"","related":"2"}}}}
                        //{"1":{"id":"1","name":"亮照经营（工商局要求）、一码通扫","intro":"","required":"1"},"2":{"id":"2","name":"免费获得个人收款银行账户","intro":"<h3>银行惠福龙卡（免费）</h3>\r\n<p>建行专门为成都国际商贸城的入驻商家打造一款银行储蓄卡——惠福龙卡，它不仅具备普通储蓄卡的所有基本功能，还增加了结算优惠、存款增值等特色功能：</p>\r\n免开立工本费和每年的账户管理费；<br/>\r\n享受活期便利和定期收益；<br/>\r\n全国境内银行系统内存取现和转账免手续费；<br/>\r\n截至2017年12月31日全国跨行取现免手续费；<br/>\r\n银行手机银行跨行转账免手续费；使用手机银行以外的渠道跨行转账，每年免12笔转账手续费。<br/>\r\n<br/>\r\n<img src=\"https://api.clpays.com:29001/intro/1-1.png\" /><br/>\r\n<img src=\"https://api.clpays.com:29001/intro/1-2.png\" /><br/>","required":"1"},"3":{"id":"3","name":"优惠开立对公收款账户，获得无抵押信用贷款资格","intro":"<h3>享受5折价格优惠</h3>\r\n<p>建行对公结算账户既是各商家日常交易结算所需，账户上的交易流水、资金沉淀和缴税金额等更是<font color='red'><b>申请建行无抵押信用贷款的必要条件</b>，建行本次仅针对</font>商贸城入驻商家提供<b>666元对公开户大礼包（原价1360元）</b>，优惠幅度达5折：</p>\r\n支付密码器免费赠送；<br/>\r\n网银优惠办理（原价480元/年）；<br/>\r\n结算卡优惠办理（原价300元/年）；<br/>\r\n回单卡优惠办理（原价360元/年）；<br/>\r\n短信通知优惠办理（原价96元/年）；<br/>\r\n账户管理费优惠办理（原价360元/年）；<br/>\r\n<br/>\r\n<img src=\"https://api.clpays.com:29001/intro/2-1.png\" /><br/>\r\n\r\n<h3>无需担保抵押，最高额度可达200万元</h3> <p><font color='red'><b>贷款特点：依据商贸城商家在建行账户上的交易流水、存款余额或商家的缴税金额核定贷款金额，不定期限、随借随还的信用贷款，支持网上申请和归还，最高额度200万元，日利率低于万分之2。</b></font></p> <img src=\"https://api.clpays.com:29001/intro/4-1.png\" /><br/> <img src=\"https://api.clpays.com:29001/intro/4-2.png\" /><br/>","required":null},"4":{"id":"4","name":"免费获取智能POS机","intro":"<h3>建行POS</h3> <p>建行将为商家免费赠送带扫码功能固定POS。支持微信、支付宝和各家银行卡支付。固定POS提供公网通讯模式，速度更快，更可节约通讯费用。免费提供POS用纸。若商家<b>营业执照经营范围有商品批发</b>，可给予特殊费率，<b>收费标准为：</b></p> <b>借记卡：0.5%，单笔20元封顶<br/> 贷记卡：0.5%，本行信用卡单笔20元封顶，他行信用卡不封顶<br/> 微信、支付宝：0.5%。<br/></b> 建行POS四大优势：<br/> 1)\t节约商家的柜台空间<br/> 建行POS可以帮助商家将POS刷卡、微信二维码、支付宝二维码、银联扫码购等统统合并，只需要一台互联网POS即可完成所有支付需求。<br/> 2)\t节约通信费<br/> 建行POS采用互联网通信，不产生通讯费用，每天交易30笔，每月节约200元。<br/> 3)\t真正的低成本获客，一次使用终生不忘<br/> 建行POS为商家免费提供微信公众号获客平台，将商家的照片、介绍提供给客户，还可以定位和导航哟！！<br/> 建行POS为商家免费开放后台系统，客户使用建行POS完成交易后将客户信息实时推送至商家的后台，商家还可以添加备注，记录客户消费习惯、喜爱等信息，为二次营销提供帮助。<br/> 4)\t免费的营销推广平台，兜住客户没商量<br/> 建行POS为商家提供建行免费推广平台，商家可自主发放各种优惠券，所有关注建行公众号的客户均可领取商家发放的优惠券，将天南海北的朋友聚集到商家身边。<br/><img src=\"https://api.clpays.com:29001/intro/3-1.png\" /><br/> <img src=\"https://api.clpays.com:29001/intro/3-2.png\" /><br/> <img src=\"https://api.clpays.com:29001/intro/3-3.png\" /><br/> <img src=\"https://api.clpays.com:29001/intro/3-4.png\" /><br/> ","required":null}}
                        String Package=isJsonObj.handleData("packages", isJsonObj.handleData("data",a));
                        JsonObject myJsondata = new JsonParser().parse(Package).getAsJsonObject();
                        int what=  myJsondata.size();
                        isnewbank= isGetBooleanFromJson.handleData("isnewbank", isJsonObj.handleData("data",a));
                        //account.setChecked(isnewbank);
                        JsonArray selected= isGetJsonArrayFromJson.handleData("selected", isJsonObj.handleData("data",a));
                        int size=selected.size();
                        for (int p=0;p<what;p++){
                            String data = isJsonObj.handleData(p+1+"",Package);

                            NewPackagesBean newPackagesBean=new NewPackagesBean();
                            boolean select=false;
                            for(int i=0;i<size;i++){
                                int s= selected.get(i).getAsInt()-1;//套餐1是0项
                                if(s==p){
                                    select=true;
                                }
                            }
                            newPackagesBean.setChoice(select);
                            String title=isGetStringFromJson.handleData("name",data);
                            newPackagesBean.setTitle(title);

                            String required=isGetStringFromJson.handleData("required",data);
                            newPackagesBean.setRequired(required);

                            String id=isGetStringFromJson.handleData("id",data);
                            newPackagesBean.setId(id);
                            String Related=isGetStringFromJson.handleData("related",data);
                            newPackagesBean.setRelated(Related);
                            String content=isGetStringFromJson.handleData("intro",data);
                            List<ThirdBean> thirdBeenlist=new ArrayList<>();
                            ThirdBean thirdBean=new ThirdBean();
                            thirdBean.setTitle(content);
                            thirdBeenlist.add(thirdBean);

                            newPackagesBean.setBeenList(thirdBeenlist);
                            mDatas.add(newPackagesBean);
                        }
                        packagesExpandableListViewAdapter.setIsnewbank(isnewbank);
                        packagesExpandableListViewAdapter.notifyDataSetChanged();


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
        dialogLoading=new DialogLoading();
        packagesExpandableListViewAdapter =new PackagesExpandableListViewAdapter(mDatas, this, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        expandableListView.setAdapter(packagesExpandableListViewAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if(mDatas.get(groupPosition).getBeenList().get(0).getTitle().isEmpty()){
                    return true;//返回true,表示不可点击
                }
                else {
                    return false;
                }
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                // TODO Auto-generated method stub
                for (int i = 0; i < packagesExpandableListViewAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }

            }

        });

    }

    @Override
    public void initToolBar() {

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("基本资料"));
            finish();//修复返回问题
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
