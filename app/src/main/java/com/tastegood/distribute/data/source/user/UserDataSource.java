package com.tastegood.distribute.data.source.user;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.user.model.UserLoginParams;
import com.tastegood.distribute.data.source.user.model.UserLogoutParams;
import com.tastegood.distribute.user.domain.model.UserInfo;

/**
 * Created by surandy on 2016/10/18.
 */

public interface UserDataSource {

    interface UserLoginCallback {

        void onUserLoginSuccess(UserInfo userInfo);

        void onUserLoginBusinessFail(String errorMessage);

        void onUserLoginFail();
    }

    interface UserLogoutCallback {

        void onUserLogoutSuccess(boolean result);

        void onUserLogoutBusinessFail(String errorMessage);

        void onUserLogoutFail();
    }

    void login(UserLoginParams userLoginParams, @NonNull UserDataSource.UserLoginCallback callback);

    void logout(UserLogoutParams userLogoutParams, @NonNull UserDataSource.UserLogoutCallback callback);

}
