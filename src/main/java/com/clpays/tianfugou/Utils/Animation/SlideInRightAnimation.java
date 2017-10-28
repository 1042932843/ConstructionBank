package com.clpays.tianfugou.Utils.Animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Name: SlideInRightAnimation
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-10-28 15:25
 */
public class SlideInRightAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
        };
    }
}
