package com.tastegood.distribute.api;

import com.tastegood.distribute.gobal.BaseApiParams;
import com.tastegood.distribute.gobal.BaseApiResponEntity;
import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.user.domain.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by surandy on 2016/10/18.
 */

public interface UserApi {

    @Headers({
            "Content-type: application/json"
    })

    @POST(Constants.DZ_LOGIN)
    public Call<BaseApiResponEntity<UserInfo>> userLogin(@Body BaseApiParams baseApiParams);

    @POST(Constants.DZ_LOGOUT)
    public Call<BaseApiResponEntity<Boolean>> userLogout(@Body BaseApiParams baseApiParams);

}
