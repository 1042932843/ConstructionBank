package com.clpays.tianfugou.Network.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Name: PackageService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-10-14 14:18
 */

public interface PackageService {
    @POST("user/pushpackage")
    Observable<ResponseBody> pushpackage(@Body JsonObject bean);

    @POST("user/fetchpackage")
    Observable<ResponseBody> fetchpackage(@Body JsonObject bean);
}
