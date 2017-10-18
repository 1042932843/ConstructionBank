package com.clpays.tianfugou.Network.NetObserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.clpays.tianfugou.App.app;

/**
 * Name: DuskyReceiver
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 服务于网络状态的广播接收器
 * Date: 2017-04-11 19:51
 */

public class NetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int connectionType = NetWorkUtil.getConnectionType(context);
            //更改网络状态
            if (app.getInstance() != null) {
                app.getInstance().notifyNetObserver(connectionType);
            }

        }

    }
}
