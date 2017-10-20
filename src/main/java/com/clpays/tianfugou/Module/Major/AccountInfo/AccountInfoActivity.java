package com.clpays.tianfugou.Module.Major.AccountInfo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.OnClick;
import com.clpays.tianfugou.Design.CreditSesame.CreditSesameView;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.TextToSpeechUtil;
import com.clpays.tianfugou.Utils.ToastUtil;

public class AccountInfoActivity extends BaseActivity {

    @BindView(R.id.sesame_view)
    CreditSesameView sesameView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @OnClick(R.id.daikuan)
    public void daikuan(){
        ToastUtil.ShortToast("功能暂时未开放，请留意后续更新");

    }

    //有需要可以对背景实现色彩根据分数渐变
    private final int[] mColors = new int[] {
            0xFFFF80AB,
            0xFFFF4081,
            0xFFFF5177,
            0xFFFF7997
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        sesameView.setSesameValues(0);
    }

    @Override
    public void initToolBar() {

    }


    public void startColorChangeAnim() {
        ObjectAnimator animator = ObjectAnimator.ofInt(sesameView, "backgroundColor", mColors);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
