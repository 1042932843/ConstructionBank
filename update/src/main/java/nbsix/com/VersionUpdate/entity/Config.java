package nbsix.com.VersionUpdate.entity;

import android.os.Environment;

/**
 * Name: Config
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //配置文件
 * Date: 2017-09-25 15:49
 */

public class Config {
    /**
     * 通知栏标题
     */
    public static String notificationTitle ;
    /**
     * 通知栏小图标
     */
    public static int notificaionSmallIconResId = 0;
    /**
     * 通知栏图标
     */
    public static int notificaionIconResId = 0;
    /**
     * 文件下载地址
     */
    public static String downLoadPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/downloads/";

    /**
     * 开始下载
     */
    public final static String ACTION_START = "ACTION_START";
    /**
     * 暂停下载
     */
    public final static String ACTION_PAUSE = "ACTION_PAUSE";
    /**
     * 下载完成
     */
    public final static String ACTION_FININSHED = "ACTION_FININSHED";
    /**
     * 刷新下载进度
     */
    public final static String ACTION_REFRESH = "ACTION_REFRESH";
}
