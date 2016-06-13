package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.event.AskEvent;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IAskView;

import org.greenrobot.eventbus.EventBus;

import retrofit2.adapter.rxjava.HttpException;

public class AskPresenter extends BasePresenter<IAskView> {

    public AskPresenter(Context context, IAskView v) {
        super(context, v);
    }

    public void ask(String token, String title, String content, String type) {
        if (v != null) {
            v.onProgress();
            RequestManager.getInstance().askQuestion(new SimpleSubscriber<>(context, new SubscriberListener<String>() {
                @Override
                public void onNext(String s) {
                    EventBus.getDefault().post(new AskEvent());
                    if (v != null) v.onAskSuccess(s);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if (v != null) {
                        if (e instanceof HttpException) {
                            if (((HttpException) e).code() == 401) {
                                v.onAskError("本地储存账号信息过期\n请重新登录!");
                            } else {
                                v.onAskError(((HttpException) e).message());
                            }
                        } else {
                            v.onAskError(e.toString());
                        }
                    }
                }
            }), token, title, content, type);
        }
    }
}
