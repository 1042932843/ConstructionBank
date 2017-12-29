package com.clpays.tianfugou.Module.Major.Payment;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.clpays.tianfugou.Adapter.RvItemAdapter;
import com.clpays.tianfugou.Adapter.helper.OnItemListener;
import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.Dialog.SelectDialog;

import com.clpays.tianfugou.Entity.Pay.payItem;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.Utils.tools.isJsonArray;
import com.clpays.tianfugou.alipay.AuthResult;
import com.clpays.tianfugou.alipay.PayResult;
import com.clpays.tianfugou.wxapi.Constants;

import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;

import com.clpays.tianfugou.zxing.encode.CodeCreator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PayListActivity extends BaseActivity {

    private IWXAPI api;
    private String type;
    @OnClick(R.id.back)
    public void back(){
        finish();
    }
    DialogLoading dialogLoading;
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    //记录选择的Item
    private HashSet<Integer> positionSet=  new HashSet<>();
    List<payItem> mDataList= new ArrayList<>();
    JsonArray idarray;
    RvItemAdapter mRvItemAdapter;
    @BindView(R.id.title)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置StatusBar透明
        //SystemBarHelper.immersiveStatusBar(this);
        api = WXAPIFactory.createWXAPI(this,  Constants.APP_ID);
        //showC();
        Intent it=getIntent();
        type = it.getStringExtra("type");
        textView.setText(type);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            if(type.equals("一键缴费")){

            }else {
                initData(type);
            }

        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if(type.equals("一键缴费")){

            }else {
                initData(type);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public void all(){

    }

    public void initData(String type){

        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        obj.addProperty("type",type);
        RetrofitHelper.getPayAPI()
                .getTypeBill(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    String a = bean.string();
                    String message= isGetStringFromJson.handleData("message",a);
                    if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                        Gson gson = new Gson();
                        JsonArray array= isJsonArray.handleData("data",a);
                        List<payItem> list=gson.fromJson(array,
                                new TypeToken<List<payItem>>() {
                                }.getType());

                        mDataList.clear();
                        mDataList.addAll(list);
                        mRvItemAdapter.notifyDataSetChanged();
                    }else{
                        ToastUtil.ShortToast(message);
                    }

                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }



    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_paylist;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        dialogLoading=new DialogLoading();
        dialogLoading.setCancelable(false);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvItemAdapter = new RvItemAdapter(this, mDataList);

        mRvList.setAdapter(mRvItemAdapter);
        mRvItemAdapter.setOnItemListener(new OnItemListener() {

            @Override
            public void checkBoxClick(int position) {
                //已经有Item被选择,执行添加或删除操作
                addOrRemove(position);
            }

            @Override
            public void onItemClick(View view, int position) {
                if(mDataList.get(position).isPaid())
                {
                    return;
                }
                showC(mDataList.get(position).getId());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /**
     * 操作Item记录集合
     */
    private void addOrRemove(int position) {
        if(positionSet.contains(position)) {
            // 如果包含，则撤销选择
            Log.e("----","remove");
            positionSet.remove(position);
        } else {
            // 如果不包含，则添加
            Log.e("----","add");
            positionSet.add(position);
        }

        if(positionSet.size() == 0) {

        }
    }

    @Override
    public void initToolBar() {

    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayListActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        initData(type);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayListActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(PayListActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayListActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(PayListActivity.this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!PayListActivity.this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    public void showC(int cid){
        idarray=new JsonArray();

        List<String> names = new ArrayList<>();
        names.add("支付宝");
        names.add("微信");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String paytype="";
                JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
                if(position==0){
                    paytype = "alipay";
                }
                if(position==1){
                    paytype= "wechat";
                }
                obj.addProperty("paytype",paytype);
                if(positionSet.size()>0){
                    for (int p : positionSet) {
                        idarray.add(mDataList.get(p).getId());
                    }
                }else{
                    idarray.add(cid);
                }

                obj.add("billids",idarray);
                RetrofitHelper.getPayAPI()
                        .paybill(obj)
                        .compose(PayListActivity.this.bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bean -> {
                            String a = bean.string();
                            if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                                String data=isJsonObj.handleData("data",a);
                                switch (position) {
                                    case 0:
                                        String order=isGetStringFromJson.handleData("result",data);
                                        Runnable payRunnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                PayTask alipay = new PayTask(PayListActivity.this);
                                                Map<String, String> result = alipay.payV2(order, true);
                                                Log.i("msp", result.toString());
                                                Message msg = new Message();
                                                msg.what = SDK_PAY_FLAG;
                                                msg.obj = result;
                                                mHandler.sendMessage(msg);
                                            }
                                        };

                                        Thread payThread = new Thread(payRunnable);
                                        payThread.start();
                                        break;
                                    case 1:
                                        if(api.getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT) {

                                            ToastUtil.ShortToast("您未安装最新版本微信，不支持微信支付，请安装或升级微信版本");
                                            return;
                                        }
                                       JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();

                                       weixinPay(jsonObject);

                                        break;
                                    default:
                                        break;
                                }
                            }
                            String message= isGetStringFromJson.handleData("message",a);
                        }, throwable -> {
                            ToastUtil.ShortToast("数据错误,无法查看支付方式");
                        });



            }
        }, names);
    }

    public void weixinPay(JsonObject jsonObject){
        jsonObject.addProperty("package","Sign=WXPay");
        PayReq req = new PayReq();
        req.appId			= jsonObject.get("appid").getAsString();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        req.partnerId		= jsonObject.get("mch_id").getAsString();
        req.prepayId		= jsonObject.get("prepay_id").getAsString();
        req.nonceStr		= jsonObject.get("nonce_str").getAsString();
        req.timeStamp		= jsonObject.get("timestamp").getAsString();
        req.packageValue	= jsonObject.get("package").getAsString();
        req.sign			= jsonObject.get("sign").getAsString();
        req.extData			= "天府购"; // optional
        Toast.makeText(this, "正常调起支付", Toast.LENGTH_SHORT).show();
        api.sendReq(req);
    }
}
