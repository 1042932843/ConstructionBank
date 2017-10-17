package com.clpays.tianfugou.Network.api;

import com.clpays.tianfugou.Network.api.response.Result;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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

public interface UploadService {

    /*
   Retrofit get annotation with our URL
   And our method that will return us the List of Contacts
   */
    @Multipart
    @POST("upload.php")
    Call<Result> Upload(@Part MultipartBody.Part file);

    @POST("user/fetchupload")
    Observable<ResponseBody> fetchUpload(@Body JsonObject bean);
}
