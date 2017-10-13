package com.clpays.tianfugou.Module.Major.Authentication.Fragment;

import android.os.Bundle;

import com.clpays.tianfugou.Module.Base.BaseFragment;
import nbsix.clpays.tianfugou.R;


public class ResultFragment extends BaseFragment {

    public static ResultFragment newInstance() {

        return new ResultFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initRecyclerView(){

    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_result;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initRecyclerView();
    }



}
