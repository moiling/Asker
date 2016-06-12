package com.moinut.asker.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.presenter.QuestionPresenter;
import com.moinut.asker.ui.activity.MainActivity;
import com.moinut.asker.ui.adapter.QuestionAdapter;
import com.moinut.asker.ui.vu.IQuestionView;
import com.moinut.asker.utils.AnimationUtils;
import com.moinut.asker.utils.ScreenUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QuestionFragment extends BaseFragment implements
        RecyclerArrayAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener,
        IQuestionView {

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.rv_questions)
    EasyRecyclerView mRecyclerView;

    private float mFabBottom;
    private QuestionAdapter mAdapter;
    private QuestionPresenter mQuestionPresenter;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new QuestionAdapter(getContext());
        mQuestionPresenter = new QuestionPresenter(getContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScreenUtils.paddingToNavigationBarWithStatusBar(mRecyclerView.getRecyclerView());
        mRecyclerView.setAdapterWithProgress(mAdapter);
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setRefreshingColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        AnimationUtils.hideFabInRecyclerView(mRecyclerView.getRecyclerView(), ((MainActivity) getActivity()).getFab());

        mAdapter.setOnItemClickListener(position -> {
            // TODO 显示详细
            Toast.makeText(getContext(), mAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
        });
        mAdapter.setMore(R.layout.view_question_more, this);
        mAdapter.setNoMore(R.layout.view_question_nomore);
        mAdapter.setError(R.layout.view_question_empty).setOnClickListener(v -> mAdapter.resumeMore());
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mQuestionPresenter.onRelieveView();
    }

    @Override
    public void onRefresh() {
        mQuestionPresenter.onRefresh();
    }

    @Override
    public void onLoadMore() {
        mQuestionPresenter.onLoadMore();
    }

    @Override
    public void onRefreshEnd(List<Question> questions) {
        mAdapter.clear();
        mAdapter.addAll(questions);
    }

    @Override
    public void onLoadMoreEnd(List<Question> questions) {
        mAdapter.addAll(questions);
    }
}
