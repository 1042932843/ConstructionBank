package com.clpays.tianfugou.Module.Major.Authentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.clpays.tianfugou.Design.stepsView.HorizontalStepView;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Module.Major.Authentication.Fragment.CertificateInfoFragment;
import com.clpays.tianfugou.Module.Major.Authentication.Fragment.ResultFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import com.clpays.tianfugou.Design.stepsView.StepBean;
import com.clpays.tianfugou.Module.Major.Authentication.Fragment.BasicInfoFragment;
import com.clpays.tianfugou.Module.Major.Home.HomePageActivity;
import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.LogUtil;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.UserState;

public class StartAuthenticationActivity extends BaseActivity {

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index=-1;
    FragmentTransaction trx;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.step_view)
    HorizontalStepView stepView;

    @OnClick(R.id.back)
    public void back(){
        String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
        if("review_profile".equals(s)||"review_upload".equals(s)){
            showExitDialog();
        }else if("review_profile_upload".equals(s)){
            if(index==1){
                changeFragmentIndex(0);
            }else{
                showExitDialog();
            }
        }else if(index==2){
            finish();
        }else if(index==1){
            goPackage();
        }else{
            showExitDialog();
        }


    }

    private void showExitDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(R.mipmap.launcher);
        normalDialog.setTitle("确定退出认证？");
        normalDialog.setMessage("退出后会保留帐号的填写流程记录,记得下次来补充完整哦~");
        normalDialog.setPositiveButton("退出",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        finish();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        EventBus.getDefault().register(this);
        CheckStatus();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    public void CheckStatus(){
        String s= PreferenceUtil.getStringPRIVATE("status",UserState.NA);
        LogUtil.d(s);
        switch (s){
            case "N/A":
                break;
            case"profile":
            case "review_profile":
            case "review_profile_upload":
                changeFragmentIndex(0);
                break;
            case"package":
                changeFragmentIndex(0);
                goPackage();
                break;
            case "upload":
            case "review_upload":
                changeFragmentIndex(1);
                break;


            case "checked":
            case "prepared":
            case "waiting":
                changeFragmentIndex(2);
                break;
        }
    }


    public void setStepView(){
        String s= PreferenceUtil.getStringPRIVATE("status",UserState.NA);
        switch (index){
            case 0:
                if(!"profile".equals(s)){
                    List<StepBean> stepsBeanList = new ArrayList<>();
                    StepBean stepBean0 = new StepBean("基本资料",StepBean.STEP_COMPLETED);
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
                }else{
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
                }

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
                StepBean stepBean22 = new StepBean("提交完成",StepBean.STEP_COMPLETED);
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
        if(index==currentIndex){//修正重复显示的问题（毕竟是add）

        }else{
            index = currentIndex;
            switchFragment();
        }

    }


    /**
     * Fragment切换
     */
    private void switchFragment() {
        setStepView();
        trx = getSupportFragmentManager().beginTransaction();
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commitAllowingStateLoss();//重要
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

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String Type = event.getType();
        switch (Type){
            case "基本资料":
                changeFragmentIndex(0);
                break;
            case "套餐选择":
                goPackage();
                break;
            case "证照上传":
                changeFragmentIndex(1);
                break;
            case "提交完成":
                changeFragmentIndex(2);
        }
    }

    public void goPackage(){
        Intent it=new Intent(this,PackagesActivity.class);
        startActivity(it);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //登录了并且是在审核状态UserState.isLogin()&&UserState.isAuditing()
            String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
            if("review_profile".equals(s)){
                showExitDialog();
            }
            if("review_profile_upload".equals(s)){
                if(index==1){
                    changeFragmentIndex(0);
                }else{
                    showExitDialog();
                }
            }

            if(index==2){
                this.finish();
            }else if(index!=0&&index!=2){
                changeFragmentIndex(index-1);
            }else {
                showExitDialog();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
