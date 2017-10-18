package com.clpays.tianfugou.Network.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Name: UpdateService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-30 14:48
 */

public interface AuthenticationService {

    @POST("user/fetchprofile")//基本资料加载
    Observable<ResponseBody> fetchprofile(@Body JsonObject bean);

    @POST("user/pushprofile")//基本资料上传
    Observable<ResponseBody> pushprofile(@Body JsonObject bean);

    @POST("user/fetchchecked")//审核完，需要准备的资料
    Observable<ResponseBody> fetchchecked(@Body JsonObject bean);

    @POST("user/fetchchecked")//审核完，需要准备的资料OK
    Observable<ResponseBody> pushchecked(@Body JsonObject bean);

}
