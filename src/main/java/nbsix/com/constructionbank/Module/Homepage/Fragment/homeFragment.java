package nbsix.com.constructionbank.Module.Homepage.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import nbsix.com.constructionbank.Adapter.HomePageAdapter;
import nbsix.com.constructionbank.Adapter.helper.SpacesItemDecoration;
import nbsix.com.constructionbank.Entity.HomePage.homeItem;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;


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
        item2.setName("建行贷款");
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
