package com.clpays.tianfugou.Module.Major.Credit.Fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.clpays.tianfugou.Adapter.CreditAdapter;
import com.clpays.tianfugou.Design.textViewHtml.MImageGetter;
import com.clpays.tianfugou.Entity.Credit.CreditType;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreditInfoFragment extends BaseFragment {
    CreditType creditType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.head)
    TextView head;

    @BindView(R.id.content)
    TextView content;

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

    public static CreditInfoFragment newInstance() {
        return new CreditInfoFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_credit_info;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        creditType=(CreditType)getArguments().getSerializable("CreditType");
        head.setText(creditType.getTitle());
        String html=creditType.getContent();
        content.setText(Html.fromHtml(html, new MImageGetter(content, getContext()), null));
    }




}
