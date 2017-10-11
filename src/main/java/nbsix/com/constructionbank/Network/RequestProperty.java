package nbsix.com.constructionbank.Network;

import com.google.gson.JsonObject;

import nbsix.com.constructionbank.App.appConfig;
import nbsix.com.constructionbank.Utils.PreferenceUtil;
import nbsix.com.constructionbank.Utils.UserState;

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
        object.addProperty("ver", appConfig.ver);
        object.addProperty("platform", appConfig.platform);
        return object;
    }

    public static JsonObject CreateTokenJsonObjectBody(){
        JsonObject object=new JsonObject();
        object.addProperty("ver", appConfig.ver);
        object.addProperty("platform", appConfig.platform);
        String token= PreferenceUtil.getStringPRIVATE("Token", UserState.NA);
        object.addProperty("token", token);
        return object;
    }
}
