package com.clpays.tianfugou.Module.Major.Credit.Fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.clpays.tianfugou.Adapter.CreditAdapter;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @OnClick(R.id.back)
    public void back(){
        //EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("基本资料"));
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
    }




}
