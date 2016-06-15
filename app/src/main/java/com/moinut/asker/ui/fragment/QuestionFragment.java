package com.moinut.asker.ui.fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moinut.asker.APP;
import com.moinut.asker.R;

@SuppressWarnings("WeakerAccess")
public class QuestionFragment extends BaseQuestionFragment {

    @Override
    protected View inflateLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onRefresh() {
        if (APP.getUser(getContext()) == null)
            getQuestionPresenter().onAllQuestionsRefresh(null);
        else
            getQuestionPresenter().onAllQuestionsRefresh(APP.getUser(getContext()).getToken());
    }

    @Override
    public void onLoadMore() {
        if (APP.getUser(getContext()) == null)
            getQuestionPresenter().onAllQuestionsLoadMore(null);
        else
            getQuestionPresenter().onAllQuestionsLoadMore(APP.getUser(getContext()).getToken());
    }
}
