package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IRegisterView;

public class RegisterPresenter extends BasePresenter<IRegisterView> {

    private static final String TAG = RegisterPresenter.class.getName();

    public RegisterPresenter(Context context, IRegisterView v) {
        super(context, v);
    }

    public void register(String accountId, String password, String type) {
        if (v != null) {
            if (!type.equals(Const.API_STUDENT) && !type.equals(Const.API_TEACHER)) {
                v.onRegisterError(context.getString(R.string.wrong_register_type));
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
}
