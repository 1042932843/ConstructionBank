package nbsix.com.constructionbank.Module.Major.Home;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import nbsix.com.constructionbank.Module.Base.BaseActivity;
import nbsix.com.constructionbank.Module.Major.Home.Fragment.homeFragment;
import nbsix.com.constructionbank.Module.Major.Home.Fragment.msgFragment;
import nbsix.com.constructionbank.Module.Major.Home.Fragment.userFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;

public class HomePageActivity extends BaseActivity {
    public static final String TAG = HomePageActivity.class.getSimpleName();

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @Nullable
    TextBadgeItem numberBadgeItem;

    @Nullable
    ShapeBadgeItem shapeBadgeItem;

    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        initPermission();
        //初始化Fragment
        initFragments();
    }

    /**
     * 初始化Fragments
     */
    private void initFragments() {
        homeFragment home=homeFragment.newInstance();
        msgFragment msg=msgFragment.newInstance();
        userFragment user=userFragment.newInstance();
        fragments = new Fragment[] {
                home,
                msg,
                user
        };
        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, home)
                .show(home).commit();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        initNumberBadge();
        initBottomNavigationBar();
    }

    /**
     * 消息数量
     */
    private void initNumberBadge(){
        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorPrimary)
                .setText("" + 99)
                .setHideOnSelect(false);
    }

    /**
     * 初始化底部菜单
     */
    private void initBottomNavigationBar(){
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.shouye, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.xiaoxi, "消息").setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.wo, "我的"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                changeFragmentIndex(position);
            }
            @Override
            public void onTabUnselected(int position) {

            }
            @Override
            public void onTabReselected(int position) {
            }
        });
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

    @Override
    public void initToolBar() {

    }

    /**
     * RxPermission权限动态申请
     */
    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.requestEach(
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE

        )
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        Log.d(TAG, permission.name + " is granted.");
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Log.d(TAG, permission.name + " is denied. More info should be provided.");
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Log.d(TAG, permission.name + " is denied.");
                    }
                });
    }

    /**
     * 检查更新
     */
    private void CheakUpdate(){

    }

    private long exitTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
