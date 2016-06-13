package com.moinut.asker.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.Answer;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.presenter.AnswerPresenter;
import com.moinut.asker.ui.adapter.AnswerAdapter;
import com.moinut.asker.ui.vu.IAnswerView;
import com.moinut.asker.utils.AnimationUtils;
import com.moinut.asker.utils.ScreenUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.moinut.asker.APP.getContext;

@SuppressWarnings("WeakerAccess")
public class AnswerActivity extends BaseActivity implements
        RecyclerArrayAdapter.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener,
        IAnswerView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_answers)
    EasyRecyclerView mRecyclerView;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    private Question mQuestion;
    private AnswerAdapter mAdapter;
    private AnswerPresenter mAnswerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        mQuestion = (Question) getIntent().getSerializableExtra("question");
        mAnswerPresenter = new AnswerPresenter(this, this, mQuestion.getId());
        initView();

        onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAnswerPresenter.onRelieveView();
    }

    private void initView() {
        initToolbar();
        initRecycler();
        mFab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void initRecycler() {
        mAdapter = new AnswerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScreenUtils.paddingToNavigationBar(mRecyclerView.getRecyclerView());
        mRecyclerView.setAdapterWithProgress(mAdapter);
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setRefreshingColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        AnimationUtils.hideFabInRecyclerView(mRecyclerView.getRecyclerView(), mFab);

        mAdapter.setMore(R.layout.view_question_more, this);
        mAdapter.setNoMore(R.layout.view_question_nomore);
        mAdapter.setError(R.layout.view_question_empty).setOnClickListener(v -> mAdapter.resumeMore());
    }

    private void initToolbar() {
        mToolbar.setTitle("Answer");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void onRefresh() {
        mAnswerPresenter.onRefresh();
    }

    @Override
    public void onLoadMore() {
        mAnswerPresenter.onLoadMore();
    }

    @Override
    public void onRefreshEnd(List<Answer> answers) {
        mAdapter.clear();
        mAdapter.addAll(answers);
    }

    @Override
    public void onLoadMoreEnd(List<Answer> answers) {
        mAdapter.addAll(answers);
    }
}
