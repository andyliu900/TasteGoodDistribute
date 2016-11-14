package com.tastegood.distribute.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tastegood.distribute.R;

/**
 * Created by surandy on 16/4/21.
 */
public class FootViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar loadPb;
    private TextView loadTv;
    private TextView loadEndTextView;

    public FootViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.refresh_footer, parent, false));
        loadEndTextView = (TextView) itemView
                .findViewById(R.id.pull_to_refresh_end);
        loadEndTextView.setVisibility(View.GONE);
        loadPb = (ProgressBar) itemView
                .findViewById(R.id.pull_to_load_progress);
        loadPb.setVisibility(View.VISIBLE);
        loadTv = (TextView) itemView.findViewById(R.id.pull_to_load_text);
        loadTv.setText(R.string.pull_to_refresh_footer_refreshing_label);
        RecyclerView.LayoutParams ps = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (ps instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) ps).setFullSpan(true);
        }
        loadAgain();
        itemView.setEnabled(false);
    }

    public FootViewHolder(ViewGroup parent, View.OnClickListener listener) {
        this(parent);
        itemView.setOnClickListener(listener);
    }

    public void refresh(LoadRecycleviewAdapter.LoadState state) {
        if (state == null) {
            return;
        }
        switch (state) {
            case LoadAgain:
                loadAgain();
                break;
            case LoadFinish:
                loadFinish();
                break;
            case LoadFailed:
                loadFailed();
                break;
            case Loading:
                loading();
                break;
            case NoAllowLoad:
                noAllowLoad();
                break;
            case LoadFINISHKONG:
                loadfiishkong();
                break;
        }
    }

    public void loadFinish() {
        loadPb.setVisibility(View.GONE);
        loadTv.setVisibility(View.GONE);
        loadEndTextView.setVisibility(View.VISIBLE);
        itemView.setEnabled(false);
    }

    public void loadAgain() {
        loading();
    }

    public void loading() {
        loadEndTextView.setVisibility(View.GONE);
        loadPb.setVisibility(View.VISIBLE);
        loadTv.setVisibility(View.VISIBLE);
        loadTv.setText(R.string.pull_to_refresh_footer_refreshing_label);
        itemView.setEnabled(false);
    }


    public void loadFailed() {
        itemView.setEnabled(true);
        loadEndTextView.setVisibility(View.INVISIBLE);
        loadPb.setVisibility(View.INVISIBLE);
        loadTv.setText("加载失败，点击重新加载");
    }

    public void loadfiishkong() {
        itemView.setEnabled(false);
        loadPb.setVisibility(View.INVISIBLE);
        loadEndTextView.setVisibility(View.INVISIBLE);
        loadTv.setText("  ");
    }

    public void noAllowLoad() {
        itemView.setEnabled(false);
        loadPb.setVisibility(View.GONE);
        loadTv.setVisibility(View.GONE);
        loadEndTextView.setVisibility(View.GONE);
    }
}
