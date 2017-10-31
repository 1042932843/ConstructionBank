package com.clpays.tianfugou.Design.textViewHtml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Name: MImageGetter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //异步处理textview中html网络图片的显示
 * 调用：tv.setText(Html.fromHtml(html, new MImageGetter(tv, getApplicationContext()), null));
 * Date: 2017-10-30 16:06
 */
public class MImageGetter implements ImageGetter{
		Context c;
		TextView container;

	    public MImageGetter(TextView text, Context c) {
	        this.c = c;
			this.container = text;
	    }
	 public Drawable getDrawable(String source) {
		 final LevelListDrawable drawable = new LevelListDrawable();
		 Glide.with(c).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
			 @Override
			 public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
				 if(resource != null) {
					 BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
					 drawable.addLevel(1,
							 1,
							 bitmapDrawable);
					 drawable.setBounds(
					 		0, 0,
							 resource.getWidth(),
							 resource.getHeight());
					 drawable.setLevel(1);
					 container.invalidate();
					 container.setText(container.getText()); // 解决图文重叠
				 }
			 }

		 });


		 return drawable;
	 }

}
