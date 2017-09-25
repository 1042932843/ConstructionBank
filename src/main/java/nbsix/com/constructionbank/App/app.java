package nbsix.com.constructionbank.App;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.squareup.leakcanary.LeakCanary;

import java.util.Map;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.imageloader.GlideImageLoader;

/**
 * Name: app
 * Author: Dusky、
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 15:01
 */

public class app extends Application{
    public static app mInstance;
    public static RequestOptions optionsRoundedCorners,optionsRoundedCircle,optionsNormal,optionsNormalCrop;
    public static app getInstance() {
        return mInstance;
    }

    // 用于存放倒计时时间（验证码按钮）
    public static Map<String, Long> map;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
        initLeakCanary();
        initImagePicker();
    }

    //初始化glide配置
    private void init() {
        optionsNormal=new RequestOptions()
                .centerInside()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        optionsNormalCrop=new RequestOptions()
                .centerCrop()
                //.error(R.drawable.zhanweitu)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
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

    //初始化内存检测工具
    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }

    //初始化图片选择器
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(3);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1080);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(675);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1080);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(675);                         //保存文件的高度。单位像素
    }

}