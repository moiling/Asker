package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.APP;
import com.moinut.asker.config.Api;
import com.moinut.asker.config.Const;
import com.moinut.asker.event.LoginEvent;
import com.moinut.asker.model.bean.ApiWrapper;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.ILoginView;

import org.greenrobot.eventbus.EventBus;

public class LoginPresenter extends BasePresenter<ILoginView> {

    public LoginPresenter(Context context, ILoginView v) {
        super(context, v);
    }

    public void login(String accountId, String password) {
        if (v != null) {
            v.onLoginProgress();
            RequestManager.getInstance().login(new SimpleSubscriber<>(context, new SubscriberListener<ApiWrapper<User>>() {
                @Override
                public void onNext(ApiWrapper<User> userApiWrapper) {
                    if (v != null) {
                        if (userApiWrapper.getState() != Api.API_STATUS_SUCCESS) {
                            String info = userApiWrapper.getInfo();
                            if (info.equals(Const.API_UN_REGISTER)) {
                                v.onShouldRegister(accountId, password);
                            } else if (info.equals(Const.API_WRONG_PASSWORD)) {
                                v.onLoginError("密码输入错误");
                            }
                        } else {
                            User user = userApiWrapper.getData();
                            APP.setUser(context, user);
                            EventBus.getDefault().post(LoginEvent.class);
                            v.onLoginSuccess(user);
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (v != null) v.onLoginError(e.getMessage());
                }
            }), accountId, password);
        }
    }

}
