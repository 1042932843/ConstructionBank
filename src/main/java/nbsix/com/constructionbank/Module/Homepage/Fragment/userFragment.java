package nbsix.com.constructionbank.Module.Homepage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Adapter.ucenterAdapter;
import nbsix.com.constructionbank.Design.WaveView.WaveView;
import nbsix.com.constructionbank.Entity.UCenter.ucItem;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.Module.Homepage.Set.SetActivity;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;


public class userFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.waveview)
    WaveView waveView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.set)
    public void set(){
        Intent it=new Intent(getActivity(), SetActivity.class);
        startActivity(it);
    }

    public static userFragment newInstance() {

        return new userFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initRecyclerView(){

        List<ucItem> ucItemList =new ArrayList<>();
        ucItem item=new ucItem();
        item.setType("认证信息");
        item.setImg(R.drawable.renzheng);
        ucItemList.add(item);

        ucItem item2=new ucItem();
        item2.setType("我的账户");
        item2.setImg(R.drawable.zhanghu);
        ucItemList.add(item2);

        ucItem item3=new ucItem();
        item3.setType("我的贷款");
        item3.setImg(R.drawable.daikuan);
        ucItemList.add(item3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ucenterAdapter adapter=new ucenterAdapter(getContext(),ucItemList);
        recyclerView.setAdapter(adapter);


    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ucenter;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        waveView.setMode(WaveView.MODE_RECT);
        waveView.setMax(50);
        waveView.setProgress(40);
        initRecyclerView();
    }


}
