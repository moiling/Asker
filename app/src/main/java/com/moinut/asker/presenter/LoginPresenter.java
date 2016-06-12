package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.APP;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.ILoginView;

public class LoginPresenter {
    private ILoginView v;
    private Context context;

    public LoginPresenter(Context context, ILoginView v) {
        this.v = v;
        this.context = context;
    }

    public void login(String accountId, String password) {
        RequestManager.getInstance().login(new SimpleSubscriber<>(context, new SubscriberListener<User>() {
            @Override
            public void onNext(User user) {
                super.onNext(user);
                APP.setUser(context, user);
                v.onLoginSuccess(user);
            }

            @Override
            public void onError(Throwable e) {
                v.onLoginError(e.getMessage());
            }
        }), accountId, password);
    }

    public void onRelieveView() {
        this.v = null;
        this.context = null;
    }
}
