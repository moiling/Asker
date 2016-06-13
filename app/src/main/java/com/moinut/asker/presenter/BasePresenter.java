package com.moinut.asker.presenter;

import android.content.Context;

public class BasePresenter<V> {
    protected V v;
    protected Context context;

    public BasePresenter(Context context, V v) {
        this.v = v;
        this.context = context;
    }

    public void onRelieveView() {
        this.v = null;
        this.context = null;
    }
}
