package nbsix.com.constructionbank.Module.Homepage.Credit;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;

public class MyCreditActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_credit;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

}
