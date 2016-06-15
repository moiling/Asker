package com.moinut.asker.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.presenter.DoAnswerPresenter;
import com.moinut.asker.ui.vu.IDoAnswerView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoAnswerActivity extends BaseActivity implements IDoAnswerView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edit_content)
    EditText mEditContent;

    private DoAnswerPresenter mDoAnswerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_answer);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDoAnswerPresenter != null) mDoAnswerPresenter.onRelieveView();
    }

    private void initView() {
        initToolbar();
        mEditContent.setSingleLine(false);
        mEditContent.setHorizontallyScrolling(false);
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.answer);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initData() {
        int questionId = getIntent().getIntExtra(Const.INTENT_QUESTION, -1);
        String token;
        if (APP.getUser(this) != null && (token = APP.getUser(this).getToken())!= null) {
            mDoAnswerPresenter = new DoAnswerPresenter(this, this, token, questionId);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_send:
                send();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void send() {
        if (mEditContent.getText().toString().isEmpty()) {
            mEditContent.setError(getString(R.string.content_not_null));
            return;
        }
        mDoAnswerPresenter.answer(mEditContent.getText().toString());
    }

    @Override
    public void onDoAnswerSuccess(String info) {
        dismissProgress();
        Toast.makeText(this, R.string.reply_success, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDoAnswerError(String info) {
        dismissProgress();
        showDialog(getString(R.string.error), info);
    }

    @Override
    public void onDoAnswerProgress() {
        showProgress(getString(R.string.answering));
    }
}
