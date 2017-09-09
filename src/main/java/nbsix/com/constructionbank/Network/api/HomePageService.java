package nbsix.com.constructionbank.Network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Name: HomePageService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 16:00
 */

public interface HomePageService {
        /**
         * banner数据
         */
        @GET("homemaking/api/ad/getAll")
        Observable<ResponseBody> getHomePageRecommendedBannerInfo();
}
