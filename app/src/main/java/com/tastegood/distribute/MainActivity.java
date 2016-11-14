package com.tastegood.distribute;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tastegood.distribute.gobal.BaseToolbarActivity;
import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.gobal.Injection;
import com.tastegood.distribute.order.OrdersListFragment;
import com.tastegood.distribute.order.event.NewOrderActionEvent;
import com.tastegood.distribute.order.event.RefreshFragmentEvent;
import com.tastegood.distribute.order.event.SwitchFragmentEvent;
import com.tastegood.distribute.order.view.OrderManagerAdapter;
import com.tastegood.distribute.user.LoginActivity;
import com.tastegood.distribute.user.UserContract;
import com.tastegood.distribute.user.UserPresenter;
import com.tastegood.distribute.user.domain.model.UserInfo;
import com.tastegood.distribute.util.SPUtils;
import com.tastegood.distribute.util.UIUtils;
import com.tastegood.distribute.view.PagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends BaseToolbarActivity implements UserContract.LogoutView {

    public static final String NEWORDER_FLAG = "neworder_flag";

    String[] titles = { "待处理", "配送中", "已送达"};

    @Bind(R.id.id_stickynavlayout_indicator)
    PagerSlidingTabStrip viewPagerIndicator;
    @Bind(R.id.id_stickynavlayout_viewpager)
    ViewPager viewPager;

    private List<Fragment> fragmentList;
    private UserContract.Presenter mPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        initToolbar("今日订单", R.mipmap.ic_logout, new onNavigationOnClickListener() {
            @Override
            public void setOnNavigationOnClickListener() {
                showdialog("", "你确定要注销？", new String[]{"取消", "确认"});
            }
        });

        fragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Fragment f1 = OrdersListFragment.newInstance(i);
            fragmentList.add(f1);
        }

        OrderManagerAdapter adapter = new OrderManagerAdapter(getSupportFragmentManager(), titles, fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPagerIndicator.setTextSize(40);
        viewPagerIndicator.setViewPager(viewPager);
    }

    @Override
    protected void initDatas() {
        mPresenter = new UserPresenter(
                Injection.provideUseCaseHandler(),
                this,
                Injection.provideLogoutTask());
    }

    long lastBackPressTime = 0;

    @Override
    public void onBackPressed() {
        if (lastBackPressTime < System.currentTimeMillis() - 3000L) {
            UIUtils.showShort(this, "再按一次返回键退出");
            lastBackPressTime = System.currentTimeMillis();
        } else {
            if (application != null) {
                application.exit();
            }
        }
    }

    @Override
    public void goLoginActivity() {
        UIUtils.openActivity(this, LoginActivity.class);
        finish();
    }

    @Override
    public void showLogoutSuccessToast() {

    }

    @Override
    public void showLogoutFailToast() {
        UIUtils.showShort(this, "账号退出失败");
    }

    @Override
    public void showLoadingView() {
        showProgressDialog("请稍等...");
    }

    @Override
    public void hideLoadingView() {
        dismissProgressDialog();
    }

    @Override
    public void showBusinessError(String businessErrorMessage) {
        UIUtils.showShort(this, businessErrorMessage);
    }

    @Override
    public void SureListener() {
        super.SureListener();
        showLoadingView();
        UserInfo userInfo = (UserInfo) SPUtils.get(Constants.USER_INFO_KEY, UserInfo.class);
        mPresenter.logout(userInfo.getLoginName());
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onEventMainThread(Object event) {
        if (event instanceof SwitchFragmentEvent) {
            viewPager.setCurrentItem(2, true);
        } else if (event instanceof NewOrderActionEvent) {
            viewPager.setCurrentItem(0, true);
            EventBus.getDefault().post(new RefreshFragmentEvent(0));
        }
    }
}
