package com.clpays.tianfugou.Module.LoginRegister.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.R;

import butterknife.BindView;
import butterknife.OnClick;


public class RetrieveFragmentStep2 extends BaseFragment {

    @BindView(R.id.go_login)
    Button go;
    @OnClick(R.id.go_login)
    public void go_login(){
        Intent it=new Intent(getActivity(), LRpageActivity.class);
        startActivity(it);
        getActivity().finish();
    }


    public static RetrieveFragmentStep2 newInstance() {

        return new RetrieveFragmentStep2();
    }

    @Override
    public void initRecyclerView(){

    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_retrieve_step2;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }


}
