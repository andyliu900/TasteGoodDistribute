package com.tastegood.distribute.api;

import com.tastegood.distribute.gobal.BaseApiParams;
import com.tastegood.distribute.gobal.BaseApiResponEntity;
import com.tastegood.distribute.gobal.Constants;
import com.tastegood.distribute.order.domain.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * 订单Api
 *
 * Created by surandy on 2016/10/13.
 */

public interface OrderApi {

    @Headers({
            "Content-type: application/json"
    })

    @POST(Constants.GET_ORDERS)
    public Call<BaseApiResponEntity<List<Order>>> getOrders(@Body BaseApiParams baseApiParams);

    @POST(Constants.ORDER_ARRIVED)
    public Call<BaseApiResponEntity<Boolean>> orderArrived(@Body BaseApiParams baseApiParams);

}
