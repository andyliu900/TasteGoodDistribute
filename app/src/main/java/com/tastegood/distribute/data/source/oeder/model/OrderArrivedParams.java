package com.tastegood.distribute.data.source.oeder.model;

import com.tastegood.distribute.data.source.BaseParams;

/**
 * Created by surandy on 2016/11/13.
 */

public class OrderArrivedParams extends BaseParams {

    private long distributionId;

    private String orderNo;

    public long getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(long distributionId) {
        this.distributionId = distributionId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
