package com.moinut.asker.presenter;

import android.content.Context;
import android.util.Log;

import com.moinut.asker.config.Const;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IRegisterView;

public class RegisterPresenter {

    private static final String TAG = RegisterPresenter.class.getName();

    private IRegisterView v;
    private Context context;

    public RegisterPresenter(Context context, IRegisterView v) {
        this.v = v;
        this.context = context;
    }

    public void register(String accountId, String password, String type) {
        Log.d(TAG, "register: 1");
        if (v != null) {
            Log.d(TAG, "register: 2");
            if (!type.equals(Const.API_STUDENT) && !type.equals(Const.API_TEACHER)) {
                v.onRegisterError("注册类型错误");
                return;
            }

            v.onRegisterProgress();
            RequestManager.getInstance().register(new SimpleSubscriber<>(context, new SubscriberListener<String>() {
                @Override
                public void onNext(String s) {
                    if (v != null) v.onRegisterSuccess(s);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if (v != null) v.onRegisterError(e.toString());
                }
            }), accountId, password, type);
        }
    }

    public void onRelieveView() {
        this.v = null;
        this.context = null;
    }
}
