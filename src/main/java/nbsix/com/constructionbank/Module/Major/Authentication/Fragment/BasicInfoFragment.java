package nbsix.com.constructionbank.Module.Major.Authentication.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;


public class BasicInfoFragment extends BaseFragment {

    public static BasicInfoFragment newInstance() {

        return new BasicInfoFragment();
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
        return R.layout.fragment_basicinfo;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initRecyclerView();
    }



}
