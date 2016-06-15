package com.moinut.asker.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.presenter.AskPresenter;
import com.moinut.asker.ui.vu.IAskView;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class AskActivity extends BaseActivity implements IAskView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edit_title)
    EditText mEditTitle;
    @Bind(R.id.edit_type)
    EditText mEditType;
    @Bind(R.id.edit_content)
    EditText mEditContent;
    private AskPresenter mAskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        ButterKnife.bind(this);
        mAskPresenter = new AskPresenter(this, this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAskPresenter.onRelieveView();
    }

    private void initView() {
        initToolbar();
        mEditContent.setSingleLine(false);
        mEditContent.setHorizontallyScrolling(false);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ask, menu);
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
        if (APP.getUser(this) == null || APP.getUser(this).getToken().isEmpty()) {
            Toast.makeText(this, R.string.un_login_please_login, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEditTitle.getText().toString().isEmpty()) {
            mEditTitle.setError(getString(R.string.title_not_null));
            return;
        }
        if (mEditType.getText().toString().isEmpty()) {
            mEditType.setError(getString(R.string.type_not_null));
            return;
        }
        mAskPresenter.ask(APP.getUser(this).getToken(),
                mEditTitle.getText().toString(),
                mEditContent.getText().toString(),
                mEditType.getText().toString());
    }

    @Override
    public void onAskSuccess(String info) {
        dismissProgress();
        Toast.makeText(this, R.string.ask_success, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onAskError(String info) {
        dismissProgress();
        showDialog(getString(R.string.error), info);
    }

    @Override
    public void onProgress() {
        showProgress(getString(R.string.asking));
    }
}
