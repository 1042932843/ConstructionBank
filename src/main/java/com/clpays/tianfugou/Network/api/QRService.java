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
 * Comment: //二维码收款相关
 * Date: 2017-12-21 14:48
 */

public interface QRService {
    @POST("user/fetchqrcode")
    Observable<ResponseBody> getQRContent(@Body JsonObject bean);

    @POST("user/fetchtodayorder")
    Observable<ResponseBody> fetchtodayorder(@Body JsonObject bean);

    @POST("user/fetchallorder")
    Observable<ResponseBody> fetchallorder(@Body JsonObject bean);
}
