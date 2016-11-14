package com.tastegood.distribute.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LoadRecyclerView extends LinearLayout {

    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    private LoadRecycleviewAdapter mAdapter;

    /**
     * last update time
     */
    public LoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadRecyclerView(Context context) {
        super(context);
        init();
    }

    /**
     * init
     * <p/>
     * hylin 2012-7-26上午10:08:33
     *
     * @description
     */
    private void init() {
        setOrientation(VERTICAL);
        addRecyclerView();
    }

    private void addRecyclerView() {
        mRecyclerView = new RecyclerView(getContext());

        LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, lps);
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LoadRecycleviewAdapter.LoadState state = mAdapter.getLoadState();
                if ((state != LoadRecycleviewAdapter.LoadState.LoadAgain && state != LoadRecycleviewAdapter.LoadState.LoadFailed) || !mAdapter.isEnableLoad()) {
                    return;
                }
                if (mLayoutManager instanceof LinearLayoutManager) {
                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int lastCompletelyVisiableItemPosition = ((LinearLayoutManager)mLayoutManager).findLastCompletelyVisibleItemPosition();
                    if ((visibleItemCount > 0)
                            && (lastCompletelyVisiableItemPosition >= totalItemCount - 4)) {
                        mAdapter.setLoadFooterState(LoadRecycleviewAdapter.LoadState.LoadAgain);
                        mAdapter.loading();
                        return;
                    }
                } else if (mLayoutManager instanceof GridLayoutManager) {
                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int lastCompletelyVisiableItemPosition = ((GridLayoutManager)mLayoutManager).findLastCompletelyVisibleItemPosition();
                    if ((visibleItemCount > 0)
                            && (lastCompletelyVisiableItemPosition >= totalItemCount - 4)) {
                        mAdapter.setLoadFooterState(LoadRecycleviewAdapter.LoadState.LoadAgain);
                        mAdapter.loading();
                        return;
                    }
                } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                    int[] lastPs = ((StaggeredGridLayoutManager)mLayoutManager).findLastVisibleItemPositions(null);
                    if (lastPs == null) {
                        return;
                    }

                    for (int i = 0; i < lastPs.length; i++) {
                        if (lastPs[i] >= mLayoutManager.getItemCount() - 4) {
                            mAdapter.setLoadFooterState(LoadRecycleviewAdapter.LoadState.LoadAgain);
                            mAdapter.loading();
                            return;
                        }
                    }
                }
            }
        });
    }

    public void addItemDecoration(ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }


    public void setLoadFooterState(LoadRecycleviewAdapter.LoadState state) {
        if (mAdapter != null) {
            mAdapter.setLoadFooterState(state);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mLayoutManager = layout;
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setAdapter(LoadRecycleviewAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public RecyclerView getRecyclerView() {
        if (mRecyclerView != null) {
            return mRecyclerView;
        }
        return null;
    }
}
