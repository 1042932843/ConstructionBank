package com.clpays.tianfugou.Network.Intercepter;

import com.clpays.tianfugou.Utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Utils.LogUtil;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Name: StatusInterceptor
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //状态拦截器
 * Date: 2017-08-25 14:41
 */

public class StatusInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        int code=originalResponse.code();
        LogUtil.d("Code="+code);
        switch (code){
            case 202:
                String url="";

                ResponseBody value = originalResponse.body();
                if(value!=null){
                    value.string();
                    byte[] resp = value.bytes();
                    String json = new String(resp, "UTF-8");
                    url= isGetStringFromJson.handleData("url",json);
                    EventBus.getDefault().post(new EventUtil("强制升级",url));
                    // 注意。由于前面value.bytes()把响应流读完并关闭了，所以这里需要重新生成一个response，否则数据就无法正常解析了
                    originalResponse = originalResponse.newBuilder()
                            .body(ResponseBody.create(null, resp))
                            .build();
                }

                break;
            case 403:
                //ToastUtil.ShortToast("错误"+code+"，身份验证失效，请重新登录！");
                EventBus.getDefault().post(new EventUtil("重新登录"));
            case 404:
                ToastUtil.ShortToast("错误"+code+"，服务器维护中");
                break;
            case 500:
            case 502:
                ToastUtil.ShortToast("错误"+code+"，服务器维护中");
                break;

        }

        return originalResponse;
    }

}
