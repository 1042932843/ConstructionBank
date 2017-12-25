package com.clpays.tianfugou.Network.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Name: QRService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //用户中心相关api
 * Date: 2017-12-21 14:48
 */

public interface UcenterService {
    @POST("user/fetchcardinfo")
    Observable<ResponseBody> getCardinfo(@Body JsonObject bean);
}
