package com.tastegood.distribute.data.source.user;

import android.support.annotation.NonNull;

import com.tastegood.distribute.api.BaseCallBack;
import com.tastegood.distribute.api.RetrofitInstance;
import com.tastegood.distribute.api.UserApi;
import com.tastegood.distribute.data.source.user.model.UserLoginParams;
import com.tastegood.distribute.data.source.user.model.UserLogoutParams;
import com.tastegood.distribute.gobal.BaseApiParams;
import com.tastegood.distribute.gobal.BaseApiResponEntity;
import com.tastegood.distribute.user.domain.model.UserInfo;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by surandy on 2016/10/18.
 */

public class UserDataSourceImpl implements UserDataSource {

    private static UserDataSourceImpl INSTANCE;

    private UserApi userApi;

    public static UserDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataSourceImpl();
        }
        return INSTANCE;
    }

    private UserDataSourceImpl() {
        if (userApi == null) {
            userApi = RetrofitInstance.getInstance().create(UserApi.class);
        }
    }

    @Override
    public void login(UserLoginParams userLoginParams,final @NonNull UserLoginCallback callback) {
        BaseApiParams baseApiParams = new BaseApiParams(userLoginParams);
        final Call<BaseApiResponEntity<UserInfo>> call = userApi.userLogin(baseApiParams);

        call.enqueue(new BaseCallBack<BaseApiResponEntity<UserInfo>>() {
            @Override
            public void doSuccess(Response<BaseApiResponEntity<UserInfo>> response) {
                callback.onUserLoginSuccess(response.body().getData());
            }

            @Override
            public void doBusinessFail(Response<BaseApiResponEntity<UserInfo>> response) {
                callback.onUserLoginBusinessFail(response.body().getMessage());
            }

            @Override
            public void doFail(Throwable error) {
                callback.onUserLoginFail();
            }
        });
    }

    @Override
    public void logout(UserLogoutParams userLogoutParams, @NonNull final UserLogoutCallback callback) {
        BaseApiParams baseApiParams = new BaseApiParams(userLogoutParams);
        final Call<BaseApiResponEntity<Boolean>> call = userApi.userLogout(baseApiParams);

//        try {
//            Response<BaseApiResponEntity<Boolean>> result =  call.execute();
//            System.out.println(result.body());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        call.enqueue(new BaseCallBack<BaseApiResponEntity<Boolean>>() {
            @Override
            public void doSuccess(Response<BaseApiResponEntity<Boolean>> response) {
                callback.onUserLogoutSuccess(response.body().getData());
            }

            @Override
            public void doBusinessFail(Response<BaseApiResponEntity<Boolean>> response) {
                callback.onUserLogoutBusinessFail(response.body().getMessage());
            }

            @Override
            public void doFail(Throwable error) {
                callback.onUserLogoutFail();
            }
        });
    }
}
