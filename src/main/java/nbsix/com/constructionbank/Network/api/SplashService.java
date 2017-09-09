package nbsix.com.constructionbank.Network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Name: SplashService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 15:30
 */

public interface SplashService {
    /**
     * 升级相关数据
     */
    @GET("apps/latest/596de879ca87a866aa00019e?api_token=e6dc00d350bbf238c2f333655f7666f4")
    Observable<ResponseBody> getUpdateInfo();
}
