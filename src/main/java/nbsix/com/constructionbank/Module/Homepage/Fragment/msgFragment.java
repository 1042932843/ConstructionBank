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


public class msgFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
