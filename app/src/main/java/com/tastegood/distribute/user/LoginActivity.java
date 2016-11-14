package com.tastegood.distribute.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.tastegood.distribute.MainActivity;
import com.tastegood.distribute.R;
import com.tastegood.distribute.gobal.BaseToolbarActivity;
import com.tastegood.distribute.gobal.Injection;
import com.tastegood.distribute.util.MD5Util;
import com.tastegood.distribute.util.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 登录界面
 *
 * Created by surandy on 2016/10/13.
 */

public class LoginActivity extends BaseToolbarActivity implements UserContract.LoginView {

    private UserContract.Presenter mPresenter;

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        initToolbar("登录");
    }

    @Override
    protected void initDatas() {
        mPresenter = new UserPresenter(
                Injection.provideUseCaseHandler(),
                this,
                Injection.provideLoginTask());
    }

    @OnClick(R.id.login_btn)
    public void click() {
        if (TextUtils.isEmpty(username.getText())) {
            UIUtils.showShort(this, "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            UIUtils.showShort(this, "密码不能为空");
            return;
        }
        showLoadingView();
        mPresenter.login(username.getText().toString(), MD5Util.md5(password.getText().toString()));
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
    public void showLoadingView() {
        showProgressDialog("正在登录...");
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
    public void showUserNameError() {
        UIUtils.showShort(this, "账号错误，请检查");
    }

    @Override
    public void showAccountForbided() {
        UIUtils.showShort(this, "账号被禁用，请联系管理员");
    }

    @Override
    public void showPasswordError() {
        UIUtils.showShort(this, "密码错误，请检查");
    }

    @Override
    public void goMainActivity() {
        UIUtils.openActivity(this, MainActivity.class);
        finish();
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
