package com.tastegood.distribute.api;

import com.orhanobut.logger.Logger;
import com.tastegood.distribute.gobal.BaseApiResponEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by surandy on 16/4/19.
 */
public abstract class BaseCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Logger.i("callback success    " + response.body());
        try {
            BaseApiResponEntity baseApiResponEntity = (BaseApiResponEntity)response.body();
            if (!"200000".equals(baseApiResponEntity.getCode())) {
                doBusinessFail(response);
                return;
            }
            doSuccess(response);
        } catch (Exception e) {
            doFail(e.getCause());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Logger.e("callback fail    " + t.getMessage());
        doFail(t);
    }


    public abstract void doSuccess(Response<T> response);

    public abstract void doBusinessFail(Response<T> response);

    public abstract void doFail(Throwable error);

}
