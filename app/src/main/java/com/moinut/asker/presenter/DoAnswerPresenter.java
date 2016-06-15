package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.R;
import com.moinut.asker.event.AnswerEvent;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IDoAnswerView;

import org.greenrobot.eventbus.EventBus;

import retrofit2.adapter.rxjava.HttpException;

public class DoAnswerPresenter extends BasePresenter<IDoAnswerView> {

    private int questionId;
    private String token;

    public DoAnswerPresenter(Context context, IDoAnswerView iDoAnswerView, String token, int questionId) {
        super(context, iDoAnswerView);
        this.token = token;
        this.questionId = questionId;
    }

    public void answer(String content) {
        if (v != null) {
            v.onDoAnswerProgress();
            RequestManager.getInstance().answer(new SimpleSubscriber<>(context, new SubscriberListener<String>() {
                @Override
                public void onNext(String s) {
                    EventBus.getDefault().post(new AnswerEvent());
                    if (v != null) v.onDoAnswerSuccess(s);
                }

                @Override
                public void onError(Throwable e) {
                    if (v != null) {
                        if (e instanceof HttpException) {
                            if (((HttpException) e).code() == 401) {
                                v.onDoAnswerError(context.getString(R.string.token_out_date_login_again));
                            } else {
                                v.onDoAnswerError(((HttpException) e).message());
                            }
                        } else {
                            v.onDoAnswerError(e.toString());
                        }
                    }
                }
            }), token, questionId, content);
        }
    }
}
