package com.moinut.asker.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.moinut.asker.R;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.activity.LoginActivity;
import com.moinut.asker.ui.vu.IQuestionView;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;

import static com.moinut.asker.APP.getContext;

public class QuestionPresenter extends BasePresenter<IQuestionView> {
    private int page = 0;
    private int count = 10;

    public QuestionPresenter(Context context, IQuestionView v) {
        super(context, v);
    }

    public void onAllQuestionsRefresh(String token) {
        page = 0;
        RequestManager.getInstance().getAllQuestions(new SimpleSubscriber<>(getContext(), new QuestionRefreshListener()), page, count, token);
    }

    public void onAllQuestionsLoadMore(String token) {
        RequestManager.getInstance().getAllQuestions(new SimpleSubscriber<>(getContext(), new QuestionLoadListener()), page, count, token);
    }

    public void onStarQuestionsRefresh(String token) {
        page = 0;
        RequestManager.getInstance().getStarQuestions(new SimpleSubscriber<>(getContext(), new QuestionRefreshListener()), page, count, token);
    }

    public void onStarQuestionsLoadMore(String token) {
        RequestManager.getInstance().getStarQuestions(new SimpleSubscriber<>(getContext(), new QuestionLoadListener()), page, count, token);
    }

    public void onMyQuestionsRefresh(String token) {
        page = 0;
        RequestManager.getInstance().getMyQuestions(new SimpleSubscriber<>(getContext(), new QuestionRefreshListener()), page, count, token);
    }

    public void onMyQuestionsLoadMore(String token) {
        RequestManager.getInstance().getMyQuestions(new SimpleSubscriber<>(getContext(), new QuestionLoadListener()), page, count, token);
    }

    private void doError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            if (((HttpException) e).code() == 401) {
                Toast.makeText(context, R.string.token_out_date_login_again, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        }
        // 其他错误不处理
    }

    private class QuestionLoadListener extends SubscriberListener<List<Question>> {
        @Override
        public void onNext(List<Question> questions) {
            super.onNext(questions);
            if (v != null) {
                v.onLoadMoreEnd(questions);
                page++;
            }
        }
        @Override
        public void onError(Throwable e) {
            doError(e);
        }
    }

    private class QuestionRefreshListener extends SubscriberListener<List<Question>> {
        @Override
        public void onNext(List<Question> questions) {
            super.onNext(questions);
            if (v != null) {
                v.onRefreshEnd(questions);
                page++;
            }
        }
        @Override
        public void onError(Throwable e) {
            doError(e);
        }
    }
}
