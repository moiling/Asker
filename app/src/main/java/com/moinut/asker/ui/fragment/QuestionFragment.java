package com.moinut.asker.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.ui.adapter.itemview.QuestionListHeader;

@SuppressWarnings("WeakerAccess")
public class QuestionFragment extends BaseQuestionFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAdapter.getHeaderCount() == 0) {
            mAdapter.addHeader(new QuestionListHeader());
        }
    }

    @Override
    protected View inflateLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onRefresh() {
        if (mAdapter.getHeaderCount() == 0) {
            mAdapter.addHeader(new QuestionListHeader());
        }
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

    public void search(String search) {
        if (mAdapter.getHeaderCount() > 0) {
            mAdapter.removeHeader(mAdapter.getHeader(0));
        }
        if (APP.getUser(getContext()) == null)
            getQuestionPresenter().searchAllQuestions(search, null);
        else
            getQuestionPresenter().searchAllQuestions(search, APP.getUser(getContext()).getToken());
    }
}
