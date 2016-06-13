package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.model.bean.Answer;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IAnswerView;

import java.util.List;

import static com.moinut.asker.APP.getContext;

public class AnswerPresenter extends BasePresenter<IAnswerView> {

    private int page = 0;
    private int count = 10;
    private int questionId;

    public AnswerPresenter(Context context, IAnswerView iAnswerView, int questionId) {
        super(context, iAnswerView);
        this.questionId = questionId;
    }

    public void onRefresh() {
        page = 0;
        RequestManager.getInstance().getAnswers(new SimpleSubscriber<>(getContext(), new SubscriberListener<List<Answer>>() {
            @Override
            public void onNext(List<Answer> answers) {
                super.onNext(answers);
                if (v != null) {
                    v.onRefreshEnd(answers);
                    page++;
                }
            }
        }), questionId, page, count);
    }

    public void onLoadMore() {
        RequestManager.getInstance().getAnswers(new SimpleSubscriber<>(getContext(), new SubscriberListener<List<Answer>>() {
            @Override
            public void onNext(List<Answer> answers) {
                super.onNext(answers);
                if (v != null) {
                    v.onLoadMoreEnd(answers);
                    page++;
                }
            }
        }), questionId, page, count);
    }
}
