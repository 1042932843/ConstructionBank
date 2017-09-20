package nbsix.com.constructionbank.Module.Major.Authentication.Fragment;

import android.os.Bundle;

import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;


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
