package com.clpays.tianfugou.Network.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Name: UploadService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-27 17:49
 */

public interface CreditService {

    /*
   Retrofit get annotation with our URL
   And our method that will return us the List of Contacts
   */


    @POST("user/fetchloan")
    Observable<ResponseBody> fetchloan(@Body JsonObject bean);

    @POST("user/pushloan")
    Observable<ResponseBody> pushloan(@Body JsonObject bean);
}
