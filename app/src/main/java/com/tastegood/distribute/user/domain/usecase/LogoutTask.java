package com.tastegood.distribute.user.domain.usecase;

import android.support.annotation.NonNull;

import com.tastegood.distribute.data.source.user.UserDataSource;
import com.tastegood.distribute.data.source.user.UserRepository;
import com.tastegood.distribute.data.source.user.model.UserLogoutParams;
import com.tastegood.distribute.gobal.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by surandy on 2016/10/19.
 */

public class LogoutTask extends UseCase<LogoutTask.RequestValues, LogoutTask.ResponseValue> {

    private final UserRepository mUserRepository;

    public LogoutTask(@NonNull UserRepository userRepository) {
        mUserRepository = checkNotNull(userRepository, "mUserRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        UserLogoutParams param = new UserLogoutParams();
        param.setNickname(requestValues.mUserName);

        mUserRepository.logout(param, new UserDataSource.UserLogoutCallback() {
            @Override
            public void onUserLogoutSuccess(boolean result) {
                LogoutTask.ResponseValue responseValue = new LogoutTask.ResponseValue(result);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onUserLogoutBusinessFail(String errorMessage) {
                getUseCaseCallback().onBusinessError(errorMessage);
            }

            @Override
            public void onUserLogoutFail() {
                getUseCaseCallback().onError();
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mUserName;

        public RequestValues(String userName) {
            mUserName = userName;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final boolean mResult;

        public ResponseValue(@NonNull boolean result) {
            mResult = checkNotNull(result, "result cannot be null!");
        }

        public Boolean getResult() {
            return mResult;
        }
    }

}
