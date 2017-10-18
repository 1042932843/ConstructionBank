package com.clpays.tianfugou.Module.Major.Home.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;


public class msgFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.yidu)
    TextView yidu;
    @OnClick(R.id.yidu)
    public void yidu(){
        yidu.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        yidu.setBackgroundResource(R.drawable.shape_corner_right_blue);
        weidu.setTextColor(ContextCompat.getColor(getContext(),R.color.black_alpha_45));
        weidu.setBackgroundResource(R.drawable.shape_corner_left_gray);
    }

    @BindView(R.id.weidu)
    TextView weidu;
    @OnClick(R.id.weidu)
    public void weidu(){
        weidu.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        weidu.setBackgroundResource(R.drawable.shape_corner_left_blue);
        yidu.setTextColor(ContextCompat.getColor(getContext(),R.color.black_alpha_45));
        yidu.setBackgroundResource(R.drawable.shape_corner_right_gray);
    }

    public static msgFragment newInstance() {

        return new msgFragment();
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
        return R.layout.fragment_msg;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        initRecyclerView();
    }



}