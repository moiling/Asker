package com.moinut.asker.utils.listener;

import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewUpDownListener extends RecyclerView.OnScrollListener {
    private int mScrollThreshold = 4;

    protected abstract void onScrollUp();

    protected abstract void onScrollDown();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (dy > 0) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
    }

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }
}