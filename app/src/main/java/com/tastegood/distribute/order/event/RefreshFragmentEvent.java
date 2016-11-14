package com.tastegood.distribute.order.event;

/**
 * Created by surandy on 2016/11/3.
 */

public class RefreshFragmentEvent {

    private int mStatus;

    public RefreshFragmentEvent(int status) {
        mStatus = status;
    }

    public int getStatus() {
        return mStatus;
    }

}
