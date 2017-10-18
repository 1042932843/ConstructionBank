package nbsix.clpays.VersionUpdate.entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import nbsix.clpays.VersionUpdate.Activity.TranslucentActivity;
import nbsix.clpays.VersionUpdate.service.UpdateService;


/**
 * Name: VersionUpdateConfig
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //基本配置
 * Date: 2017-09-25 17:10
 */

public class VersionUpdateConfig {

    private static VersionUpdateConfig config = new VersionUpdateConfig();
    private FileBean fileBean;
    private Context context;

    private VersionUpdateConfig(){

    }
    public static VersionUpdateConfig getInstance(){
        return config;
    }
    /**
     * 设置上下文
     * @param context
     * @return
     */
    public  VersionUpdateConfig setContext(Context context){
        this.context = context;
        return config;
    }
    /**
     * 设置文件保存路径
     * @param path
     * @return
     */
    public  VersionUpdateConfig setFileSavePath(String path){
        Config.downLoadPath = path;
        return config;
    }

    /**
     * 设置通知标题
     */
    public VersionUpdateConfig setNotificationTitle(String title){
        Config.notificationTitle = title;
        return config;
    }
    /**
     * 设置通知图标
     */
    public VersionUpdateConfig setNotificationIconRes(int resId){
        Config.notificaionIconResId = resId;
        return config;
    }
    /**
     * 设置通知小图标
     */
    public VersionUpdateConfig setNotificationSmallIconRes(int resId){
        Config.notificaionSmallIconResId = resId;
        return config;
    }

    /**
     * 设置下载链接
     * @param url
     * @return
     */
    public VersionUpdateConfig setDownLoadURL(String url){
        fileBean = new FileBean(0, System.currentTimeMillis()+".apk", url, 0, 0);
       return config;
    }

    /**
     * 开始下载
     */
    public void startDownLoad(){
        if(context == null){
            throw  new NullPointerException("context cannot be null, you must first call setContext().");
        }
        if(fileBean == null){
            throw  new NullPointerException("url cannot be null, you must first call setDownLoadURL().");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((Activity)context).startActivity(new Intent(context, TranslucentActivity.class));
        }else {
            passCheck();
        }
    }

    public void passCheck() {
        Intent startIntent = new Intent(context, UpdateService.class);
        startIntent.setAction(UpdateService.ACTION_START);
        startIntent.putExtra("FileBean", fileBean);
        context.startService(startIntent);
    }


}