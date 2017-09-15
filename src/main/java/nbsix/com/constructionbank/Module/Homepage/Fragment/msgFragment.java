package nbsix.com.constructionbank.Module.Homepage.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Adapter.HomePageAdapter;
import nbsix.com.constructionbank.Adapter.helper.SpacesItemDecoration;
import nbsix.com.constructionbank.Entity.HomePage.homeItem;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;


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
