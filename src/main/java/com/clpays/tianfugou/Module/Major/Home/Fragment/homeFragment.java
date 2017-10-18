package com.clpays.tianfugou.Module.Major.Home.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.clpays.tianfugou.Adapter.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.clpays.tianfugou.Entity.HomePage.homeItem;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;


public class homeFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static homeFragment newInstance() {

        return new homeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<homeItem> homeItems =new ArrayList<>();
        homeItem item1=new homeItem();
        item1.setName("二维码收款");
        item1.setImg(R.drawable.shoukuan_pic);
        homeItems.add(item1);
        homeItem item2=new homeItem();
        item2.setName("银行贷款");
        item2.setImg(R.drawable.daikuan_pic);
        homeItems.add(item2);
        homeItem item3=new homeItem();
        item3.setName("商城缴费");
        item3.setImg(R.drawable.shangchengjiaofei_pic);
        homeItems.add(item3);

        HomePageAdapter adapter=new HomePageAdapter(getContext(), homeItems);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        initRecyclerView();
    }


}