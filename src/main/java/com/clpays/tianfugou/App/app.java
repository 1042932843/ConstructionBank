package com.clpays.tianfugou.App;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.clpays.tianfugou.Module.Major.Home.HomePageActivity;
import com.clpays.tianfugou.Network.NetObserver.DuskyObserver;
import com.clpays.tianfugou.Network.NetObserver.NetReceiver;
import com.clpays.tianfugou.Network.NetObserver.NetWorkUtil;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.CommonUtil;
import com.clpays.tianfugou.Utils.Crash.CrashHandler;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.Receiver.TagAliasOperatorHelper;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.tools.isJsonArray;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidkun.com.versionupdatelibrary.entity.VersionUpdateConfig;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;

import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Utils.imageloader.GlideImageLoader;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.zxy.tiny.Tiny;

import static com.clpays.tianfugou.Utils.Receiver.TagAliasOperatorHelper.ACTION_DELETE;
import static com.clpays.tianfugou.Utils.Receiver.TagAliasOperatorHelper.ACTION_SET;

/**
 * Name: app
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 15:01
 */

public class app extends Application implements DuskyObserver, Application.ActivityLifecycleCallbacks{
    public static app mInstance;
    private ArrayList<Activity> activities=new ArrayList<>();
    public static RequestOptions optionsRoundedCorners,optionsRoundedCircle,optionsNormal,optionsNormalCrop;
    public static app getInstance() {
        return mInstance;
    }
    private String url = "";
    public static String md5="";

    private NetReceiver netReceiver;
    Activity contextActivity;

    // 用于存放倒计时时间（验证码按钮）
    public static Map<String, Long> map;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler= CrashHandler.getInstance();
        crashHandler.init(this);
        Tiny.getInstance().init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        addObserver(this);
        mInstance = this;
        getAppVersionName(this);
        getRegistrationID();
        init();
        initLeakCanary();
        initImagePicker();
        //注册
        registerActivityLifecycleCallbacks(this);
        EventBus.getDefault().register(this);

        netReceiver =new NetReceiver();
        //注册网络监听广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, intentFilter);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
        //注销网络监听广播
        if (netReceiver != null) {
            unregisterReceiver(netReceiver);
        }
        removeObserver(this);

    }

    //初始化glide配置
    private void init() {
        optionsNormal=new RequestOptions()
                .centerInside()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        optionsNormalCrop=new RequestOptions()
                .centerCrop()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        //配置glide圆角
        optionsRoundedCorners  = new RequestOptions()
                .centerCrop()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .transform(new RoundedCorners(this,4))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        //头像圆形配置
        optionsRoundedCircle  = new RequestOptions()
                .centerCrop()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .transform(new CircleCrop())
                //.placeholder(R.drawable.ic_user_default)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    //初始化内存检测工具
    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }

    //初始化图片选择器
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(500);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(800);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(500);                         //保存文件的高度。单位像素
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String Type = event.getType();
        switch (Type){
            case "强制升级":
                url=event.getMsg();
                //ToastUtil.ShortToast("强制升级");
                showDialog(Type,"应用正常使用需要进行升级");
                break;
            case "重新登录":
                PreferenceUtil.resetPrivate();//清空
                app.getInstance().delAlias();//删除推送对象
                showDialog(Type,"身份验证失效，请重新登录");
                break;
            case "状态改变":
                showDialog(Type,"您的状态已经改变");
                break;

        }
    }

    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            appConfig.versionName = pi.versionName;
            appConfig.versioncode = pi.versionCode;
            versionName=appConfig.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public void getRegistrationID(){
        appConfig.rid = JPushInterface.getRegistrationID(getApplicationContext());
    }


    public void update(){
        JsonObject obj= RequestProperty.CreateJsonObjectBody();
        RetrofitHelper.getUpdateAPI()
                .update(obj)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if(isGetStringFromJson.handleData("success",a).equals("true")){
                        String data=isJsonObj.handleData("data",a);
                        url=isGetStringFromJson.handleData("url",data);
                        md5=isGetStringFromJson.handleData("md5",data);
                        JsonArray content= isJsonArray.handleData("info",data);
                        String c="是否进行更新？\n";
                        for(int i=0;i<content.size();i++){
                          String u= content.get(i).getAsString();
                            c=c+u+"\n";
                        }

                        showDialog("版本升级",c);
                    }
                     //{"success":false,"message":"","data":[]}

                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });

    }
    Dialog dialog;
    Activity current;
    private void showDialog(String title,String message){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        current=contextActivity;
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(contextActivity.isDestroyed()){
                return;
            }
        }
        if (contextActivity == null || contextActivity.isFinishing()) {
            return;
        }
            final AlertDialog.Builder normalDialog = new AlertDialog.Builder(current);
            //normalDialog.setIcon(R.mipmap.launcher);
            normalDialog.setTitle(title);
            normalDialog.setMessage(message);
            normalDialog.setCancelable(false);
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if("强制升级".equals(title)||"版本升级".equals(title)){
                                doUpdate(url);
                            }
                            if("重新登录".equals(title)){
                                Intent it=new Intent(app.this,LRpageActivity.class);
                                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(it);
                            }
                            if("状态改变".equals(title)){
                                contextActivity.finish();
                            }

                        }
                    });
        if("状态改变".equals(title)){

        }else{
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }
            );
        }

            // 显示
        dialog= normalDialog.show();

    }

    public void doUpdate(String url){
        VersionUpdateConfig.getInstance()//获取配置实例
                .setContext(this)//设置上下文
                .setDownLoadURL(url)//设置文件下载链接
                .setMD5(md5)
                .setNotificationIconRes(R.mipmap.launcher)//设置通知大图标
                .setNotificationSmallIconRes(R.mipmap.launcher)//设置通知小图标
                .setNotificationTitle("版本升级")//设置通知标题
                .startDownLoad();//开始下载
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activities.add(activity);
        if(activity.getParent()!=null){
            contextActivity = activity.getParent();
        }else
            contextActivity = activity;
        if(contextActivity instanceof HomePageActivity||contextActivity instanceof LRpageActivity){
            update();
        }

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(activity.getParent()!=null){
            contextActivity = activity.getParent();
        }else
            contextActivity = activity;

    }


    @Override
    public void onActivityResumed(Activity activity) {
        if(activity.getParent()!=null){
            contextActivity = activity.getParent();
        }else
            contextActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //处理内存泄漏
        activities.remove(activity);
        //处理内存泄漏
        if(current==activity){//dialog显示的上下文如果和销毁的Activity是一个，那么销毁dialog
            if(dialog!=null&&dialog.isShowing()){
                dialog.dismiss();
                dialog=null;
            }
        }

    }
    public void addObserver(DuskyObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    public void removeObserver(DuskyObserver observer) {
        if (observers != null && observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    private int currentNetType = -1;
    private List<DuskyObserver> observers = new ArrayList<>();
    public void notifyNetObserver(int type) {
        //与当前一样就不用发了
        if (currentNetType == type) {
            return;
        } else {
            currentNetType = type;
            if (observers != null && observers.size() > 0) {
                for (DuskyObserver observer : observers) {
                    observer.updateNetStatus(type);
                }
            }
        }
    }
    @Override
    public void updateNetStatus(int type) {
        if(contextActivity==null||contextActivity instanceof HomePageActivity){
            return;
        }
        String Message="";
        switch (type){
            case NetWorkUtil.NET_NO_CONNECTION:
                Message="没有可靠的网络链接";
                CommonUtil.showNoNetWorkDlg(contextActivity);
                break;
            case NetWorkUtil.NET_TYPE_2G:
                Message="正在使用2G流量";
                break;
            case NetWorkUtil.NET_TYPE_3G:
                Message="正在使用3G流量";
                break;
            case NetWorkUtil.NET_TYPE_4G:
                Message="正在使用4G流量";
                break;
            case NetWorkUtil.NET_TYPE_WIFI:
                Message="当前为Wifi环境";
                break;
            case NetWorkUtil.NET_TYPE_UNKNOWN:
                Message="未知的网络类型";
                CommonUtil.showNoNetWorkDlg(contextActivity);
                break;

        }
        if (!Message.equals("")){
            ToastUtil.ShortToast(Message);
        }

    }


    public void setAlias(String alias){
        int action = ACTION_SET;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        tagAliasBean.isAliasAction = true;
        tagAliasBean.alias=alias;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),1,tagAliasBean);
    }
    public void delAlias(){
        int action = ACTION_DELETE;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),1,tagAliasBean);
    }

    //想了半天搞出的遍历关闭app的方式。
    public void Exit(){
        int size=activities.size();
        for(int i=0;i<size;i++){
            if(activities.get(i)!=null){
                activities.get(i).finish();
            }
        }
    }
}