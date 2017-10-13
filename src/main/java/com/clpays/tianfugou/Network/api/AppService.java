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

public interface AppService {
    @POST("user/index")
    Observable<ResponseBody> checkStatus(@Body JsonObject bean);
}
