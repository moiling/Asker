package com.moinut.asker.utils;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.moinut.asker.utils.listener.RecyclerViewUpDownListener;

public class AnimationUtils {

    private AnimationUtils() {
        throw new UnsupportedOperationException("AnimationUtils can't be instantiated");
    }

    public static void hideFabInRecyclerView(RecyclerView recyclerView, FloatingActionButton fab) {
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mActivityFab.getLayoutParams();
//        int fabBottomMargin = lp.bottomMargin;
//        mFabBottom = mActivityFab.getHeight() + fabBottomMargin;
        recyclerView.addOnScrollListener(new RecyclerViewUpDownListener() {
            @Override
            protected void onScrollUp() {
                if (fab != null) {
                    fab.animate()
                            .translationY(700)
                            .setInterpolator(new AccelerateInterpolator(2))
                            .start();
                }
            }

            @Override
            protected void onScrollDown() {
                if (fab != null) {
                    fab.animate()
                            .translationY(0)
                            .setInterpolator(new DecelerateInterpolator(2))
                            .start();
                }
            }
        });
    }

    public static void hideAppBarInRecyclerView(RecyclerView recyclerView, AppBarLayout appBar) {
        recyclerView.addOnScrollListener(new RecyclerViewUpDownListener() {
            @Override
            protected void onScrollUp() {
                if (appBar != null) {
                    appBar.animate()
                            .translationY(-200)
                            .setInterpolator(new AccelerateInterpolator(2))
                            .start();
                }
            }

            @Override
            protected void onScrollDown() {
                if (appBar != null) {
                    appBar.animate()
                            .translationY(0)
                            .setInterpolator(new DecelerateInterpolator(2))
                            .start();
                }
            }
        });
    }

    public static void hideFabAndAppBarInRecyclerView(RecyclerView recyclerView, FloatingActionButton fab, AppBarLayout appBar) {
        recyclerView.addOnScrollListener(new RecyclerViewUpDownListener() {
            @Override
            protected void onScrollUp() {
                if (fab != null) {
                    fab.animate()
                            .translationY(700)
                            .setInterpolator(new AccelerateInterpolator(2))
                            .start();
                }
                if (appBar != null) {
                    appBar.animate()
                            .translationY(-200)
                            .setInterpolator(new AccelerateInterpolator(2))
                            .start();
                }
            }

            @Override
            protected void onScrollDown() {
                if (appBar != null) {
                    appBar.animate()
                            .translationY(0)
                            .setInterpolator(new DecelerateInterpolator(2))
                            .start();
                }
                if (fab != null) {
                    fab.animate()
                            .translationY(0)
                            .setInterpolator(new DecelerateInterpolator(2))
                            .start();
                }
            }
        });
    }
}
