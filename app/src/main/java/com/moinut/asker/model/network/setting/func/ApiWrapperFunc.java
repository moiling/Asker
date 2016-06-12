package com.moinut.asker.model.network.setting.func;

import android.util.Log;

import com.moinut.asker.config.Api;
import com.moinut.asker.model.bean.ApiWrapper;

import rx.functions.Func1;

public class ApiWrapperFunc<T> implements Func1<ApiWrapper<T>, T> {
    private static final String TAG = ApiWrapperFunc.class.getName();

    @Override
    public T call(ApiWrapper<T> wrapper) {
        if (wrapper.getState() != Api.API_STATUS_SUCCESS) {
            Log.d(TAG, "call: " + wrapper);
            throw new RuntimeException(wrapper.getInfo());
        }
        return wrapper.getData();
    }
}
