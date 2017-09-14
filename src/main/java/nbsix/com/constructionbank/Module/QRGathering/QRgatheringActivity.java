package nbsix.com.constructionbank.Module.QRGathering;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.Module.QRGathering.Fragement.GeneralJournalFragment;
import nbsix.com.constructionbank.Module.QRGathering.Fragement.QrFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.EventUtil;
import nbsix.com.constructionbank.Utils.SystemBarHelper;

public class QRgatheringActivity extends BaseActivity {

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_qrgathering;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        GeneralJournalFragment generalJournalFragment=GeneralJournalFragment.newInstance();
        QrFragment qrFragment=QrFragment.newInstance();
        fragments = new Fragment[] {
                qrFragment,
                generalJournalFragment
        };

        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.qrcontainer, qrFragment)
                .show(qrFragment).commit();

    }

    @Override
    public void initToolBar() {

    }

    /**
     * 切换Fragment的下标
     */
     public void changeFragmentIndex(int currentIndex) {
        index = currentIndex;
        switchFragment();
    }

    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.replace(R.id.qrcontainer, fragments[index]);
        }
        if(index!=0){
            trx.addToBackStack(null);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String msglog = event.getMsg();
        switch (msglog){
            case "流水明细":
                changeFragmentIndex(1);
                break;
        }
    }
}
