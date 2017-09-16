package nbsix.com.constructionbank.Module.Homepage.AccountInfo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import nbsix.com.constructionbank.Design.CreditSesame.CreditSesameView;
import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;

public class AccountInfoActivity extends BaseActivity {
    @BindView(R.id.sesame_view)
    CreditSesameView sesameView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final int[] mColors = new int[] {
            0xFFFF80AB,
            0xFFFF4081,
            0xFFFF5177,
            0xFFFF7997
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        sesameView.setSesameValues(879);
    }

    @Override
    public void initToolBar() {

    }

    public void startColorChangeAnim() {

        ObjectAnimator animator = ObjectAnimator.ofInt(sesameView, "backgroundColor", mColors);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }
}
