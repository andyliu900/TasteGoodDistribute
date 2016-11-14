package com.tastegood.distribute.user;

import android.support.annotation.NonNull;

import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.gobal.UseCase;
import com.tastegood.distribute.gobal.UseCaseHandler;
import com.tastegood.distribute.user.domain.model.UserInfo;
import com.tastegood.distribute.user.domain.usecase.LoginTask;
import com.tastegood.distribute.user.domain.usecase.LogoutTask;
import com.tastegood.distribute.util.SPUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/10/18.
 */

public class UserPresenter implements UserContract.Presenter {

    private UserContract.LoginView mLoginView;
    private UserContract.LogoutView mLogoutView;
    private LoginTask mLoginTask;
    private LogoutTask mLogoutTask;

    private final UseCaseHandler mUseCaseHandler;

    public UserPresenter(@NonNull UseCaseHandler useCaseHandler,
                          @NonNull UserContract.LoginView loginView,
                          @NonNull LoginTask loginTask) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mLoginView = checkNotNull(loginView, "loginView cannot be null!");
        mLoginTask = checkNotNull(loginTask, "loginTask cannot be null!");

        mLoginView.setPresenter(this);
    }

    public UserPresenter(@NonNull UseCaseHandler useCaseHandler,
                          @NonNull UserContract.LogoutView logoutView,
                          @NonNull LogoutTask logoutTask) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mLogoutView = checkNotNull(logoutView, "logoutView cannot be null!");
        mLogoutTask = checkNotNull(logoutTask, "logoutTask cannot be null!");

        mLogoutView.setPresenter(this);
    }

    @Override
    public void login(String username, String password) {
        LoginTask.RequestValues requestValue = new LoginTask.RequestValues(username,
                password);

        mUseCaseHandler.execute(mLoginTask, requestValue,
                new UseCase.UseCaseCallback<LoginTask.ResponseValue>() {
                    @Override
                    public void onSuccess(LoginTask.ResponseValue response) {
                        mLoginView.hideLoadingView();
                        UserInfo userInfo = response.getUserInfo();
                        if (0 == userInfo.getStatus()) {
                            SPUtils.put(Constants.USER_INFO_KEY, userInfo);
                            mLoginView.goMainActivity();
                        } else if (1 == userInfo.getStatus()) {
                            mLoginView.showUserNameError();
                        } else if (2 == userInfo.getStatus()) {
                            mLoginView.showAccountForbided();
                        } else if (3 == userInfo.getStatus()) {
                            mLoginView.showPasswordError();
                        }
                    }

                    @Override
                    public void onBusinessError(String businessErrorMessage) {
                        mLoginView.hideLoadingView();
                        mLoginView.showBusinessError(businessErrorMessage);
                    }

                    @Override
                    public void onError() {
                        mLoginView.hideLoadingView();
                    }
                });
    }

    @Override
    public void logout(String username) {
        LogoutTask.RequestValues requestValue = new LogoutTask.RequestValues(username);

        mUseCaseHandler.execute(mLogoutTask, requestValue,
                new UseCase.UseCaseCallback<LogoutTask.ResponseValue>() {
                    @Override
                    public void onSuccess(LogoutTask.ResponseValue response) {
                        mLogoutView.hideLoadingView();
                        Boolean result = response.getResult();
                        if (result) {
                            SPUtils.remove(Constants.USER_INFO_KEY);
                            mLogoutView.showLogoutSuccessToast();
                            mLogoutView.goLoginActivity();
                        } else {
                            mLogoutView.showLogoutFailToast();
                        }
                    }

                    @Override
                    public void onBusinessError(String businessErrorMessage) {
                        mLogoutView.hideLoadingView();
                        mLogoutView.showBusinessError(businessErrorMessage);
                    }

                    @Override
                    public void onError() {
                        mLogoutView.hideLoadingView();
                    }
                });
    }

}
