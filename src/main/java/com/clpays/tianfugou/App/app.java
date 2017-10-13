package com.clpays.tianfugou.App;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.google.gson.JsonObject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nbsix.com.VersionUpdate.entity.VersionUpdateConfig;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;

import nbsix.clpays.tianfugou.R;
import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Utils.imageloader.GlideImageLoader;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;

/**
 * Name: app
 * Author: Dusky、
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 15:01
 */

public class app extends Application implements Application.ActivityLifecycleCallbacks{
    public static app mInstance;
    public static RequestOptions optionsRoundedCorners,optionsRoundedCircle,optionsNormal,optionsNormalCrop;
    public static app getInstance() {
        return mInstance;
    }
    private String url = "";

    Context contextActivity;

    // 用于存放倒计时时间（验证码按钮）
    public static Map<String, Long> map;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
        initLeakCanary();
        initImagePicker();
        //注册
        registerActivityLifecycleCallbacks(this);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
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
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(3);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1080);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(675);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1080);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(675);                         //保存文件的高度。单位像素
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String Type = event.getType();
        switch (Type){
            case "强制升级":
                url=event.getMsg();
                //ToastUtil.ShortToast("强制升级");
                showUpDateDialog(Type,"如果取消将无法使用app");
                break;

        }
    }



    public void update(){
        JsonObject obj= RequestProperty.CreateJsonObjectBody();
        RetrofitHelper.getUpdateAPI()
                .update(obj)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if(isGetStringFromJson.handleData("success",a).equals(true)){
                        url=isGetStringFromJson.handleData("url",a);
                        showUpDateDialog("版本升级","检查到更新,是否进行升级");
                    }
                     //{"success":false,"message":"","data":[]}

                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });

    }

    private void showUpDateDialog(String title,String message){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(contextActivity);
        //normalDialog.setIcon(R.mipmap.launcher);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doUpdate(url);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        if("强制升级".equals(title)){
                            System.exit(0);
                        }
                    }
                });
        // 显示
        normalDialog.show();
    }

    public void doUpdate(String url){
        VersionUpdateConfig.getInstance()//获取配置实例
                .setContext(this)//设置上下文
                .setDownLoadURL(url)//设置文件下载链接
                .setNotificationIconRes(R.mipmap.launcher)//设置通知大图标
                .setNotificationSmallIconRes(R.mipmap.launcher)//设置通知小图标
                .setNotificationTitle("版本升级")//设置通知标题
                .startDownLoad();//开始下载
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity.getParent()!=null){
            contextActivity = activity.getParent();
        }else
            contextActivity = activity;
        if(contextActivity instanceof LRpageActivity){
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

    }

}