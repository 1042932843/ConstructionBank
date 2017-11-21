package com.clpays.tianfugou.Module.Major.Credit.Fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import butterknife.BindView;
import butterknife.OnClick;


public class CreditStatusFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @OnClick(R.id.back)
    public void back(){
        //EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("基本资料"));
    }

    public static CreditStatusFragment newInstance() {
        return new CreditStatusFragment();
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
