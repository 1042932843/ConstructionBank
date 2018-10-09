package com.clpays.tianfugou.Network;

import com.google.gson.JsonObject;

import com.clpays.tianfugou.App.appConfig;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.UserState;

/**
 * Name: RequestProperty
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-10-09 14:22
 */

public class RequestProperty {
    public static JsonObject CreateJsonObjectBody(){
        JsonObject object=new JsonObject();
        object.addProperty("ver", appConfig.versionName);
        object.addProperty("platform", appConfig.platform);
        object.addProperty("flavor", appConfig.flavor);
        return object;
    }

    public static JsonObject CreateTokenJsonObjectBody(){
        JsonObject object=new JsonObject();
        object.addProperty("ver", appConfig.versionName);
        object.addProperty("platform", appConfig.platform);
        object.addProperty("flavor", appConfig.flavor);
        String token= PreferenceUtil.getStringPRIVATE("token", UserState.NA);
        object.addProperty("token", token);
        return object;
    }
}
