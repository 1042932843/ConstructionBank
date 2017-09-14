package nbsix.com.constructionbank.Module.Homepage.Fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import butterknife.BindView;
import nbsix.com.constructionbank.Design.WaveView.WaveView;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;


public class userFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.waveview)
    WaveView waveView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static userFragment newInstance() {

        return new userFragment();
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
