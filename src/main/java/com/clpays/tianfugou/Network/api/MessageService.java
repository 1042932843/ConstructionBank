package com.clpays.tianfugou.Network.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Name: MessageService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //通知
 * Date: 2017-10-25 14:52
 */

public interface MessageService {

    @POST("user/fetchpush")
    Observable<ResponseBody> fetchpush(@Body JsonObject bean);
}
