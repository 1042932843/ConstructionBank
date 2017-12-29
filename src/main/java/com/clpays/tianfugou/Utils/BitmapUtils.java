package com.clpays.tianfugou.Utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.clpays.tianfugou.App.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.os.Environment.DIRECTORY_PICTURES;

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
     *
     * @param s
     * @param b
     * @return
     */
    public static Bitmap loadBitmap(String s, boolean b) {
        InputStream inputStream = null;
        try {
            inputStream = assets.open(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options op = new BitmapFactory.Options();
        if (b) {
            op.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            op.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, op);
        return bitmap;
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bitmap) {
        //将bitmap保存为本地文件
        File PHOTO_DIR = new File(app.getInstance().getExternalFilesDir(null).getPath());//设置保存路径
        File avaterFile = new File(PHOTO_DIR, System.currentTimeMillis() + ".jpg");//设置文件名称
        if (avaterFile.exists()) {
            avaterFile.delete();
        }
        try {
            avaterFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(avaterFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.flush();
            fos.close();
            ToastUtil.ShortToast("图片保存成功:"+PHOTO_DIR.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.ShortToast("图片保存失败");
        }
    }

    /**
     * 保存bitmap到SD卡
     * @param bitmap
     */
    public static String saveBitmapToSDCard(Bitmap bitmap) {
        String path = Environment.getExternalStorageDirectory() + "img-" + System.currentTimeMillis() + ".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
