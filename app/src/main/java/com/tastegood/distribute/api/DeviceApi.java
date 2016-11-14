package com.tastegood.distribute.api;

import com.tastegood.distribute.gobal.BaseApiParams;
import com.tastegood.distribute.gobal.BaseApiResponEntity;
import com.tastegood.distribute.gobal.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by surandy on 2016/10/17.
 */

public interface DeviceApi {

    @Headers({
            "Content-type: application/json"
    })

    @POST(Constants.DEVICE_SIGNIN)
    public Call<BaseApiResponEntity<String>> signDeviceInfo(@Body BaseApiParams baseApiParams);

    @POST(Constants.UPLOAD_JPUSHTOKEN)
    public Call<BaseApiResponEntity<Integer>> uploadJpushTokenInfo(@Body BaseApiParams baseApiParams);

}
