package com.moinut.asker.presenter;

import android.content.Context;

import com.moinut.asker.model.bean.Question;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IQuestionView;

import java.util.List;

import static com.moinut.asker.APP.getContext;

public class QuestionPresenter extends BasePresenter<IQuestionView> {
    private int page = 0;
    private int count = 10;

    public QuestionPresenter(Context context, IQuestionView v) {
        super(context, v);
    }

    public void onRefresh() {
        page = 0;
        RequestManager.getInstance().getAllQuestions(new SimpleSubscriber<>(getContext(), new SubscriberListener<List<Question>>() {
            @Override
            public void onNext(List<Question> questions) {
                super.onNext(questions);
                if (v != null) {
                    v.onRefreshEnd(questions);
                    page++;
                }
            }
        }), page, count);
    }

    public void onLoadMore() {
        RequestManager.getInstance().getAllQuestions(new SimpleSubscriber<>(getContext(), new SubscriberListener<List<Question>>() {
            @Override
            public void onNext(List<Question> questions) {
                super.onNext(questions);
                if (v != null) {
                    v.onLoadMoreEnd(questions);
                    page++;
                }
            }
        }), page, count);
    }
}
