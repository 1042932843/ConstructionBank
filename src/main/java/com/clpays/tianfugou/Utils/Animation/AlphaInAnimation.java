package com.clpays.tianfugou.Utils.Animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Name: AlphaInAnimation
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-10-28 14:22
 */
public class AlphaInAnimation implements BaseAnimation {
    private final float mFrom;

    public AlphaInAnimation() {
        this(0f);
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
