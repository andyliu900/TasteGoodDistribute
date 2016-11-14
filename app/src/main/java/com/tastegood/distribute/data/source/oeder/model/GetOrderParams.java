package com.tastegood.distribute.data.source.oeder.model;


import com.tastegood.distribute.data.source.BaseParams;

/**
 * Created by surandy on 2016/10/17.
 */

public class GetOrderParams extends BaseParams {

    private long distributionId;

    private int pageNumber;

    private int pageSize;

    private int status;

    public long getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(long distributionId) {
        this.distributionId = distributionId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
