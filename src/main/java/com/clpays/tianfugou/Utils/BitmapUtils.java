package com.clpays.tianfugou.Utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Name: BitmapUtils
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-07-12 15:48
 */

public  class BitmapUtils {
    public static AssetManager assets;
    /**
     * 传入图片名和是否需要透明处理
     * @param s
     * @param b
     * @return
     */
    public static Bitmap loadBitmap(String s, boolean b) {
        InputStream inputStream=null;
        try {
            inputStream= assets.open(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options op=new BitmapFactory.Options();
        if(b){
            op.inPreferredConfig= Bitmap.Config.ARGB_8888;
        }else {
            op.inPreferredConfig= Bitmap.Config.RGB_565;
        }
        Bitmap bitmap=BitmapFactory.decodeStream(inputStream,null,op);
        return bitmap;
    }
}
