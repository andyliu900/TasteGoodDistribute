package com.tastegood.distribute.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by surandy on 16/4/21.
 */
public abstract class LoadRecycleviewAdapter<T extends List> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private T items;
    private boolean mIsEnableLoad = false;
    private int mCount;
    private final int TYPE_LOAD = Integer.MAX_VALUE;
    private FootViewHolder mFootVH;
    private LoadState mState = LoadState.LoadAgain;
    private ILoadListener mLoadListener;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    public static enum LoadState{
        LoadFailed,LoadFinish,LoadAgain,Loading,NoAllowLoad,LoadFINISHKONG
    }

    public LoadRecycleviewAdapter( boolean isEnableLoad, ILoadListener listener){
        mIsEnableLoad = isEnableLoad;
        mLoadListener = listener;
    }
    public LoadRecycleviewAdapter( boolean isEnableLoad){
        mIsEnableLoad = isEnableLoad;
    }

    public boolean isEnableLoad(){
        return mIsEnableLoad;
    }

    public LoadState getLoadState(){
        return mState;
    }

    public void loading(){
        if(mLoadListener != null){
            mLoadListener.loading();
        }
    }

    @Override
    public final int getItemCount() {
        int subItemCount = getSubItemCount();
        if(subItemCount == 0)return 0;
        mCount = mIsEnableLoad ? subItemCount + 1 : subItemCount;
        return mCount;
    }

    @Override
    public int getItemViewType(int position) {
        if(mIsEnableLoad && position == mCount - 1){
            return TYPE_LOAD;
        }else{
            return getSubItemViewType(position);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mIsEnableLoad && position == mCount - 1) {
            if (mFootVH != null) {
                mFootVH.refresh(mState);
            }
        } else {
            onBindSubViewHolder(holder, position);
        }
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_LOAD){
            mFootVH = new FootViewHolder(parent,this);
            return mFootVH;
        }
        return onCreateSubViewHolder(parent, viewType);
    }

    public void setLoadFooterState(LoadState state){
        mState = state;
        if(mFootVH != null){
            mFootVH.refresh(state);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
                return;
            }
        } else {
            if (mFootVH == null) {
                return;
            }
            if(v == mFootVH.itemView && mLoadListener != null){
                mLoadListener.loading();
                mFootVH.refresh(LoadState.Loading);
            }
        }
    }

    public void setLoadListener(ILoadListener listener){
        mLoadListener = listener;
    }

    public interface ILoadListener{
        void loading();
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public void cleanItems() {
        if (items != null && items.size() > 0) {
            items.clear();
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public abstract int getSubItemCount();
    public abstract void onBindSubViewHolder(RecyclerView.ViewHolder holder, int position);
    public abstract RecyclerView.ViewHolder onCreateSubViewHolder(ViewGroup parent, int viewType);
    public abstract int getSubItemViewType(int position);

}
