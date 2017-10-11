package nbsix.com.constructionbank.Module.Major.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import nbsix.com.constructionbank.Module.Major.Home.HomePageActivity;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.EventUtil;
import nbsix.com.constructionbank.Utils.LogUtil;
import nbsix.com.constructionbank.Utils.PreferenceUtil;
import nbsix.com.constructionbank.Utils.SystemBarHelper;
import nbsix.com.constructionbank.Utils.UserState;

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
        if(index!=0){
            changeFragmentIndex(index-1);
        }else{
            finish();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        CheakStatus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_authentication;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        BasicInfoFragment BasicInfo= BasicInfoFragment.newInstance();
        CertificateInfoFragment CertificateInfo= CertificateInfoFragment.newInstance();
        ResultFragment Result= ResultFragment.newInstance();
        fragments = new Fragment[] {
                BasicInfo,
                CertificateInfo,
                Result
        };



    }

    /**
     * 检查状态
     */
    public void CheakStatus(){
        String s= PreferenceUtil.getStringPRIVATE("status",UserState.NA);
        LogUtil.d(s);
        switch (s){
            case "N/A":
                break;
            case"profile":
                changeFragmentIndex(0);
                break;
            case"2":
                changeFragmentIndex(1);
                break;
        }
    }


    public void setStepView(){
        switch (index){
            case 0:
                List<StepBean> stepsBeanList = new ArrayList<>();
                StepBean stepBean0 = new StepBean("基本资料",StepBean.STEP_UNDO);
                StepBean stepBean1 = new StepBean("证照上传",StepBean.STEP_UNDO);
                StepBean stepBean2 = new StepBean("提交完成",StepBean.STEP_UNDO);
                stepsBeanList.add(stepBean0);
                stepsBeanList.add(stepBean1);
                stepsBeanList.add(stepBean2);
                stepView.setStepViewTexts(stepsBeanList)
                        .setTextSize(14)//set textSize
                        .setLinePaddingProportion(2.8f)//设置indicator线与线间距的比例系数
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator完成线的颜色
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator未完成线的颜色
                        .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.white))//设置StepsView text完成线的颜色
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.white))//设置StepsView text未完成线的颜色
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, android.R.drawable.presence_online))//设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, android.R.drawable.presence_offline))//设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                break;
            case 1:
                List<StepBean> stepsBeanList2 = new ArrayList<>();
                StepBean stepBean01 = new StepBean("基本资料",StepBean.STEP_COMPLETED);
                StepBean stepBean11= new StepBean("证照上传",StepBean.STEP_UNDO);
                StepBean stepBean21 = new StepBean("提交完成",StepBean.STEP_UNDO);
                stepsBeanList2.add(stepBean01);
                stepsBeanList2.add(stepBean11);
                stepsBeanList2.add(stepBean21);
                stepView.setStepViewTexts(stepsBeanList2)
                        .setTextSize(12)//set textSize
                        .setLinePaddingProportion(2.8f)//设置indicator线与线间距的比例系数
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator完成线的颜色
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator未完成线的颜色
                        .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.white))//设置StepsView text完成线的颜色
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.white))//设置StepsView text未完成线的颜色
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, android.R.drawable.presence_online))//设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, android.R.drawable.presence_offline))//设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                break;
            case 2:
                List<StepBean> stepsBeanList3 = new ArrayList<>();
                StepBean stepBean02 = new StepBean("基本资料",StepBean.STEP_COMPLETED);
                StepBean stepBean12= new StepBean("证照上传",StepBean.STEP_COMPLETED);
                StepBean stepBean22 = new StepBean("提交完成",StepBean.STEP_CURRENT);
                stepsBeanList3.add(stepBean02);
                stepsBeanList3.add(stepBean12);
                stepsBeanList3.add(stepBean22);
                stepView.setStepViewTexts(stepsBeanList3)
                        .setTextSize(14)//set textSize
                        .setLinePaddingProportion(2.8f)//设置indicator线与线间距的比例系数
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator完成线的颜色
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.white))//设置StepsViewIndicator未完成线的颜色
                        .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.white))//设置StepsView text完成线的颜色
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.white))//设置StepsView text未完成线的颜色
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, android.R.drawable.presence_online))//设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, android.R.drawable.presence_offline))//设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.default_icon));//设置StepsViewIndicator AttentionIcon
                break;
        }
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
        setStepView();
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    //protected覆写，属于eventBus的bug? -->https://github.com/greenrobot/EventBus/issues/156  倒数第三行
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String Type = event.getType();
        switch (Type){
            case "证件上传":
                changeFragmentIndex(1);
                break;
            case "提交完成":
                changeFragmentIndex(2);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //登录了并且是在审核状态UserState.isLogin()&&UserState.isAuditing()
            if(index==2){
                Intent it=new Intent(this, HomePageActivity.class);
                startActivity(it);
                this.finish();
            }

            if(index!=0&&index!=2){
                changeFragmentIndex(index-1);
            }

            else {
                this.finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
