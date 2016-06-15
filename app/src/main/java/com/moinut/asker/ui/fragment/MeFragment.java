package com.moinut.asker.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.ui.activity.LoginActivity;

public class MeFragment extends BaseQuestionFragment {


    @Override
    protected View inflateLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setEmptyView(R.layout.view_question_un_login);
        mRecyclerView.getEmptyView().findViewById(R.id.tv_need_login).setOnClickListener(v -> startActivity(new Intent(getContext(), LoginActivity.class)));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        if (APP.getUser(getContext()) != null) {
            getQuestionPresenter().onMyQuestionsRefresh(APP.getUser(getContext()).getToken());
        } else {
            mRecyclerView.showEmpty();
        }
    }

    @Override
    public void onLoadMore() {
        if (APP.getUser(getContext()) != null) {
            getQuestionPresenter().onMyQuestionsLoadMore(APP.getUser(getContext()).getToken());
        } else {
            mRecyclerView.showEmpty();
        }
    }
}
