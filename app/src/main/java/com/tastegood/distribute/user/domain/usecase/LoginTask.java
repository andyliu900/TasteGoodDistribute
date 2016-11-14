package com.tastegood.distribute.user.domain.usecase;

import android.support.annotation.NonNull;

import com.tastegood.distribute.gobal.UseCase;
import com.tastegood.distribute.data.source.user.UserDataSource;
import com.tastegood.distribute.data.source.user.UserRepository;
import com.tastegood.distribute.data.source.user.model.UserLoginParams;
import com.tastegood.distribute.user.domain.model.UserInfo;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/10/18.
 */

public class LoginTask extends UseCase<LoginTask.RequestValues, LoginTask.ResponseValue> {

    private final UserRepository mUserRepository;

    public LoginTask(@NonNull UserRepository userRepository) {
        mUserRepository = checkNotNull(userRepository, "mUserRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        UserLoginParams param = new UserLoginParams();
        param.setLoginName(requestValues.mUserName);
        param.setPassword(requestValues.mPassword);

        mUserRepository.login(param, new UserDataSource.UserLoginCallback() {
            @Override
            public void onUserLoginSuccess(UserInfo userInfo) {
                ResponseValue responseValue = new ResponseValue(userInfo);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onUserLoginBusinessFail(String errorMessage) {
                getUseCaseCallback().onBusinessError(errorMessage);
            }

            @Override
            public void onUserLoginFail() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mUserName;
        private final String mPassword;

        public RequestValues(String userName, String password) {
            mUserName = userName;
            mPassword = password;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final UserInfo mUserInfo;

        public ResponseValue(@NonNull UserInfo userInfo) {
            mUserInfo = checkNotNull(userInfo, "userInfo cannot be null!");
        }

        public UserInfo getUserInfo() {
            return mUserInfo;
        }
    }

}
