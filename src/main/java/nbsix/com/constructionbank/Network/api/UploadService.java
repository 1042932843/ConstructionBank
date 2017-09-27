package nbsix.com.constructionbank.Network.api;

import nbsix.com.constructionbank.Network.api.response.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
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
    Call<Result> uploadImage(@Part MultipartBody.Part file);
}
