package com.moinut.asker.model.network.setting.func;

import android.util.Log;

import com.moinut.asker.config.Api;
import com.moinut.asker.model.bean.PageWrapper;

import rx.functions.Func1;

public class PageWrapperFunc<T> implements Func1<PageWrapper<T>, T> {
    public static final String TAG = "PageWrapperFunc";

    @Override
    public T call(PageWrapper<T> wrapper) {
        if (wrapper.getState() != Api.API_STATUS_SUCCESS) {
            Log.d(TAG, "call: " + wrapper);
            throw new RuntimeException(wrapper.getInfo());
        }
        return wrapper.getData();
    }
}
