package nbsix.com.constructionbank.Module.Homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import nbsix.com.constructionbank.Adapter.HomePageAdapter;
import nbsix.com.constructionbank.Adapter.helper.SpacesItemDecoration;
import nbsix.com.constructionbank.Entity.HomePage.homePageItem;
import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.R;

public class HomePage extends BaseActivity {
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    TextBadgeItem numberBadgeItem;

    @Nullable
    ShapeBadgeItem shapeBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {


        initNumberBadge();
        initBottomNavigationBar();
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView.addItemDecoration(new SpacesItemDecoration(32));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<homePageItem> homePageItems=new ArrayList<>();
        homePageItem item1=new homePageItem();
        item1.setName("二维码收款");
        item1.setImg(R.drawable.shoukuan_pic);
        homePageItems.add(item1);
        homePageItem item2=new homePageItem();
        item2.setName("建行贷款");
        item2.setImg(R.drawable.daikuan_pic);
        homePageItems.add(item2);
        homePageItem item3=new homePageItem();
        item3.setName("商城缴费");
        item3.setImg(R.drawable.shangchengjiaofei_pic);
        homePageItems.add(item3);

        HomePageAdapter adapter=new HomePageAdapter(this,homePageItems);
        recyclerView.setAdapter(adapter);
    }

    private void initNumberBadge(){
        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorPrimary)
                .setText("" + 99)
                .setHideOnSelect(false);
    }

    private void initBottomNavigationBar(){
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.shouye, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.xiaoxi, "消息").setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.wo, "我的"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    public void initToolBar() {

    }
}
