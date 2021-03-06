package com.clpays.tianfugou.Network;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Module.Message.MessageActivity;
import com.clpays.tianfugou.Network.Intercepter.StatusInterceptor;
import com.clpays.tianfugou.Network.api.ApiConstants;
import com.clpays.tianfugou.Network.api.AppService;
import com.clpays.tianfugou.Network.api.AuthenticationService;
import com.clpays.tianfugou.Network.api.CreditService;
import com.clpays.tianfugou.Network.api.HomePageService;
import com.clpays.tianfugou.Network.api.LoginRegisterService;
import com.clpays.tianfugou.Network.api.MessageService;
import com.clpays.tianfugou.Network.api.PackageService;
import com.clpays.tianfugou.Network.api.PayService;
import com.clpays.tianfugou.Network.api.QRService;
import com.clpays.tianfugou.Network.api.UcenterService;
import com.clpays.tianfugou.Network.api.UpdateService;
import com.clpays.tianfugou.Network.api.UploadService;
import com.clpays.tianfugou.Utils.CommonUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Name: RetrofitHelper
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 15:30
 */
public class RetrofitHelper {

  private static OkHttpClient mOkHttpClient;

  static {
    initOkHttpClient();
  }


  public static HomePageService getAPI() {
    return createApi(HomePageService.class, ApiConstants.Base_URL);
  }

  public static UpdateService getUpdateAPI(){
    return createApi(UpdateService.class, ApiConstants.Base_URL);
  }


  public static LoginRegisterService getLoginRegisterAPI(){
    return createApi(LoginRegisterService.class, ApiConstants.Base_URL);
  }

  public static AuthenticationService getAuthenticationAPI(){
    return createApi(AuthenticationService.class, ApiConstants.Base_URL);
  }

  public static AppService getAppAPI(){
    return createApi(AppService.class, ApiConstants.Base_URL);
  }

  public static PackageService getPackageAPI(){
    return createApi(PackageService.class, ApiConstants.Base_URL);
  }

  public static UploadService getUploadAPI(){
    return createApi(UploadService.class, ApiConstants.Base_URL);
  }

  public static MessageService getMSGAPI(){
    return createApi(MessageService.class, ApiConstants.Base_URL);
  }

  public static CreditService getCreditAPI(){
    return createApi(CreditService.class, ApiConstants.Base_URL);
  }

  public static QRService getQRAPI(){
    return createApi(QRService.class, ApiConstants.Base_URL);
  }

  public static UcenterService getUcenterAPI(){
    return createApi(UcenterService.class, ApiConstants.Base_URL);
  }

  public static PayService getPayAPI(){
    return createApi(PayService.class, ApiConstants.Base_URL);
  }


  /**
   * 根据传入的baseUrl，和api创建retrofit
   */
  private static <T> T createApi(Class<T> clazz, String baseUrl) {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(mOkHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    return retrofit.create(clazz);
  }

  /**
   * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
   */
  private static void initOkHttpClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    StatusInterceptor statusInterceptor=new StatusInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    if (mOkHttpClient == null) {
      synchronized (RetrofitHelper.class) {
        if (mOkHttpClient == null) {
          //设置Http缓存
          Cache cache = new Cache(new File(app.getInstance()
              .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

          mOkHttpClient = new OkHttpClient.Builder()
              .cache(cache)
              .addInterceptor(interceptor).addInterceptor(statusInterceptor)
              //.addNetworkInterceptor(new CacheInterceptor())这里关闭缓存
              .retryOnConnectionFailure(true)
              .connectTimeout(10, TimeUnit.SECONDS)
              .writeTimeout(10, TimeUnit.SECONDS)
              .readTimeout(10, TimeUnit.SECONDS)
              //.addInterceptor(new UserAgentInterceptor())
              .build();
        }
      }
    }
  }


  /**
   * 添加UA拦截器

  private static class UserAgentInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

      Request originalRequest = chain.request();
      Request requestWithUserAgent = originalRequest.newBuilder()
          .removeHeader("User-Agent")
          .addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
          .build();
      return chain.proceed(requestWithUserAgent);
    }
  } */

  /**
   * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
   */
  private static class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

      // 有网络时 设置缓存超时时间1个小时
      int maxAge = 60 * 60;
      // 无网络时，设置超时为1天
      int maxStale = 60 * 60 * 24;
      Request request = chain.request();
      if (CommonUtil.isNetworkAvailable(app.getInstance())) {
        //有网络时只从网络获取
        request = request.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build();
      } else {
        //无网络时只从缓存中读取
        request = request.newBuilder()
            .cacheControl(CacheControl.FORCE_CACHE)
            .build();
      }
      Response response = chain.proceed(request);
      if (CommonUtil.isNetworkAvailable(app.getInstance())) {
        response = response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, max-age=" + maxAge)
            .build();
      } else {
        response = response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
            .build();
      }
      return response;
    }
  }
}
