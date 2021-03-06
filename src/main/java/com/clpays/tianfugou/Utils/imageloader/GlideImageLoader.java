package com.clpays.tianfugou.Utils.imageloader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.clpays.tianfugou.App.app;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;


/**
 * Name: GlideImageLoader
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-22 16:06
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

        Glide.with(activity)                             //配置上下文
                .load(path)
                .apply(app.optionsNormal)
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)                             //配置上下文
                .load(path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .apply(app.optionsNormal)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
