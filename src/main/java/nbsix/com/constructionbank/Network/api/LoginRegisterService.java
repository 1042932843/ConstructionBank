package nbsix.com.constructionbank.Network.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Name: LoginRegisterService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-30 16:33
 */

public interface LoginRegisterService {
    @POST("user/captcha")
    Observable<ResponseBody> getCaptcha(@Body JsonObject bean);

    @POST("user/register")
    Observable<ResponseBody> register(@Body JsonObject bean);

    @POST("user/login")
    Observable<ResponseBody> login(@Body JsonObject bean);
}
