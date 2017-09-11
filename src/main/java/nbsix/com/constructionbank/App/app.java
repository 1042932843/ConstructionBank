package nbsix.com.constructionbank.App;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.leakcanary.LeakCanary;

import nbsix.com.constructionbank.R;

/**
 * Name: app
 * Author: Dusky、
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 15:01
 */

public class app extends Application{
    public static app mInstance;
    public static RequestOptions optionsRoundedCorners,optionsRoundedCircle;
    public static app getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
        initLeakCanary();
    }

    private void init() {

        //配置glide圆角
        optionsRoundedCorners  = new RequestOptions()
                .centerCrop()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .transform(new RoundedCorners(this,4))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        //头像圆形配置
        optionsRoundedCircle  = new RequestOptions()
                .centerCrop()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .transform(new CircleCrop())
                //.placeholder(R.drawable.ic_user_default)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }

}