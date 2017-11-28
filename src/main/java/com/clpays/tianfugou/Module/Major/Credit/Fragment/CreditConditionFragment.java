package com.clpays.tianfugou.Module.Major.Credit.Fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Design.textViewHtml.MImageGetter;
import com.clpays.tianfugou.Entity.Credit.CreditType;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


public class CreditConditionFragment extends BaseFragment {
    CreditType creditType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.head)
    TextView head;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.imageView4)
    ImageView pic;

    @BindView(R.id.next_step)
    Button next_step;
    @OnClick(R.id.next_step)
    public void next(){
        creditType.setCmd("2");
        EventBus.getDefault().post(creditType);
    }

    @OnClick(R.id.back)
    public void back(){
        CreditType creditType= new CreditType();
        creditType.setCmd("back");
        EventBus.getDefault().post(creditType);
    }

    public static CreditConditionFragment newInstance() {
        return new CreditConditionFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_credit_info_new;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        creditType=(CreditType)getArguments().getSerializable("CreditType");
        head.setText(creditType.getTitle());
        tv_title.setText("办理条件");
        String html=creditType.getNeed();
        content.setText(Html.fromHtml(html, new MImageGetter(content, getContext()), null));
        Glide.with(this).load(creditType.getPic()).apply(app.optionsNormal).into(pic);
        next_step.setText("申请贷款");
    }




}
