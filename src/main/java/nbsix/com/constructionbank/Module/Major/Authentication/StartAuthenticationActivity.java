package nbsix.com.constructionbank.Module.Major.Authentication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Design.stepsView.HorizontalStepView;
import nbsix.com.constructionbank.Design.stepsView.StepBean;
import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.Module.Major.Authentication.Fragment.BasicInfoFragment;
import nbsix.com.constructionbank.Module.Major.Authentication.Fragment.CertificateInfoFragment;
import nbsix.com.constructionbank.Module.Major.Authentication.Fragment.ResultFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;

public class StartAuthenticationActivity extends BaseActivity {

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.step_view)
    HorizontalStepView stepView;

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
        return R.layout.activity_start_authentication;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("基本资料",StepBean.STEP_CURRENT);
        StepBean stepBean1 = new StepBean("证照上传",StepBean.STEP_UNDO);
        StepBean stepBean2 = new StepBean("提交完成",StepBean.STEP_UNDO);

        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepView.setStepViewTexts(stepsBeanList)
                .setTextSize(12)//set textSize
                .setLinePaddingProportion(3f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon

        BasicInfoFragment BasicInfo= BasicInfoFragment.newInstance();
        CertificateInfoFragment CertificateInfo= CertificateInfoFragment.newInstance();
        ResultFragment Result= ResultFragment.newInstance();
        fragments = new Fragment[] {
                BasicInfo,
                CertificateInfo,
                Result
        };

        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, BasicInfo)
                .show(BasicInfo).commit();
    }

    @Override
    public void initToolBar() {

    }

    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(int currentIndex) {
        index = currentIndex;
        switchFragment();
    }

    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

}
