package com.tastegood.distribute.data.source.user;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.user.model.UserLoginParams;
import com.tastegood.distribute.data.source.user.model.UserLogoutParams;
import com.tastegood.distribute.user.domain.model.UserInfo;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/10/18.
 */

public class UserRepository implements UserDataSource {

    private static UserRepository INSTANCE = null;

    private final UserDataSource mUserDataSource;

    private UserRepository(@NonNull UserDataSource userDataSource) {
        mUserDataSource = checkNotNull(userDataSource);
    }

    public static UserRepository getInstance(UserDataSource userDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(userDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void login(UserLoginParams userLoginParams,final @NonNull UserLoginCallback callback) {
        mUserDataSource.login(userLoginParams, new UserLoginCallback() {

            @Override
            public void onUserLoginSuccess(UserInfo userInfo) {
                callback.onUserLoginSuccess(userInfo);
            }

            @Override
            public void onUserLoginBusinessFail(String errorMessage) {
                callback.onUserLoginBusinessFail(errorMessage);
            }

            @Override
            public void onUserLoginFail() {
                callback.onUserLoginFail();
            }
        });
    }

    @Override
    public void logout(UserLogoutParams userLogoutParams, @NonNull final UserLogoutCallback callback) {
        mUserDataSource.logout(userLogoutParams, new UserLogoutCallback() {

            @Override
            public void onUserLogoutSuccess(boolean result) {
                callback.onUserLogoutSuccess(result);
            }

            @Override
            public void onUserLogoutBusinessFail(String errorMessage) {
                callback.onUserLogoutBusinessFail(errorMessage);
            }

            @Override
            public void onUserLogoutFail() {
                callback.onUserLogoutFail();
            }
        });
    }
}
