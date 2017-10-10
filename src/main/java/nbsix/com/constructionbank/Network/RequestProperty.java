package nbsix.com.constructionbank.Network;

import com.google.gson.JsonObject;

import nbsix.com.constructionbank.App.appConfig;

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
}
