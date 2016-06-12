package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IAskView;

public class AskPresenter {
    private IAskView v;
    private Context context;

    public AskPresenter(Context context, IAskView v) {
        this.v = v;
        this.context = context;
    }

    public void ask(String token, String title, String content, String type) {
        if (v != null) {
            v.onProgress();
            RequestManager.getInstance().askQuestion(new SimpleSubscriber<>(context, new SubscriberListener<String>() {
                @Override
                public void onNext(String s) {
                    if (v != null) v.onAskSuccess(s);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    if (v != null) v.onAskError(e.toString());
                }
            }), token, title, content, type);
        }
    }

    public void onRelieveView() {
        this.v = null;
        this.context = null;
    }
}
