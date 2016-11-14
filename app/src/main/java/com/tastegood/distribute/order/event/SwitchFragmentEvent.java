package com.tastegood.distribute.order.event;

import android.support.v4.app.Fragment;

/**
 * Created by surandy on 2016/11/3.
 */

public class SwitchFragmentEvent {

    private Fragment mTargetFragment;

    public SwitchFragmentEvent(Fragment targetFragment) {
        mTargetFragment = targetFragment;
    }

    public Fragment getTargetFragment() {
        return mTargetFragment;
    }

}
