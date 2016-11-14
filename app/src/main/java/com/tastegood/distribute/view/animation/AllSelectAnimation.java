package com.tastegood.distribute.view.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

import com.tastegood.distribute.util.UIUtils;

/**
 * Created by surandy on 2016/10/31.
 */

public class AllSelectAnimation extends Animation {

    private View mListViewLayout = null;
    private boolean mEditFlag = false;
    private LayoutParams mListViewLayoutParams = null;
    private int listViewHeight = 0;
    private int changeHeight = 0;

    public AllSelectAnimation(Context context, View listViewLayout, boolean editFlag) {
        setDuration(500);

        mListViewLayout = listViewLayout;
        mEditFlag = editFlag;
        mListViewLayoutParams = (LayoutParams) mListViewLayout.getLayoutParams();
        listViewHeight = mListViewLayout.getHeight();
        changeHeight = UIUtils.dip2px(context, 60);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        if (mEditFlag) {
            mListViewLayoutParams.height = listViewHeight - (int) (changeHeight * interpolatedTime);
        } else {
            mListViewLayoutParams.height = listViewHeight + (int) (changeHeight * interpolatedTime);
        }

        mListViewLayout.requestLayout();
    }

}
