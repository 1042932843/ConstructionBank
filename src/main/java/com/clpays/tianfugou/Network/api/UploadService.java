package com.clpays.tianfugou.Network.api;

import com.clpays.tianfugou.Network.api.response.Result;
import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Name: UploadService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-27 17:49
 */

public interface UploadService {

    /*
   Retrofit get annotation with our URL
   And our method that will return us the List of Contacts
   */
    @Multipart
    @POST("user/pushpic")
    Observable<ResponseBody> UploadPic(@Part MultipartBody.Part file,@Part("json") RequestBody body);

    @POST("user/fetchupload")
    Observable<ResponseBody> fetchUpload(@Body JsonObject bean);

    @POST("user/pushupload")
    Observable<ResponseBody> pushUpload(@Body JsonObject bean);
}
