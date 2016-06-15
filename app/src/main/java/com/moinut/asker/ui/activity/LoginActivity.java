package com.moinut.asker.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.presenter.LoginPresenter;
import com.moinut.asker.presenter.RegisterPresenter;
import com.moinut.asker.ui.vu.ILoginView;
import com.moinut.asker.ui.vu.IRegisterView;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class LoginActivity extends BaseActivity implements ILoginView, IRegisterView {

    @Bind(R.id.edit_account_id)
    EditText mEditAccountId;
    @Bind(R.id.edit_password)
    EditText mEditPassword;
    @Bind(R.id.btn_sign_in)
    Button mBtnSignIn;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private LoginPresenter mLoginPresenter;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenter(this, this);
        mRegisterPresenter = new RegisterPresenter(this, this);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onRelieveView();
        mRegisterPresenter.onRelieveView();
    }

    private void initView() {
        initToolbar();
        mBtnSignIn.setOnClickListener(v -> login());
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.login);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void login() {
        if (mEditAccountId.getText().toString().isEmpty()) {
            mEditAccountId.setError(getString(R.string.account_id_not_null));
            return;
        }
        if (mEditPassword.getText().toString().isEmpty()) {
            mEditPassword.setError(getString(R.string.password_not_null));
            return;
        }
        mLoginPresenter.login(mEditAccountId.getText().toString(), mEditPassword.getText().toString());
    }

    @Override
    public void onShouldRegister(String accountId, String password) {
        dismissProgress();
        new MaterialDialog.Builder(this)
                .title(R.string.register)
                .content(R.string.please_choose_register_type)
                .items(getString(R.string.student), getString(R.string.teacher))
                .itemsCallbackSingleChoice(-1, (dialog, view, which, text) -> {
                    switch (which) {
                        case 0:
                            mRegisterPresenter.register(accountId, password, Const.API_STUDENT);
                            break;
                        case 1:
                            mRegisterPresenter.register(accountId, password, Const.API_TEACHER);
                            break;
                    }
                    return true;
                })
                .negativeText(R.string.cancer)
                .show();
    }

    @Override
    public void onLoginSuccess(User user) {
        dismissProgress();
        finish();
    }

    @Override
    public void onLoginError(String errorInfo) {
        dismissProgress();
        showDialog(getString(R.string.error), errorInfo);
    }

    @Override
    public void onLoginProgress() {
        showProgress(getString(R.string.logging));
    }

    @Override
    public void onRegisterSuccess(String info) {
        dismissProgress();
        showDialog(getString(R.string.success), getString(R.string.register_success_login), (dialog, which) -> login());
    }

    @Override
    public void onRegisterError(String info) {
        showDialog(getString(R.string.error), info);
        dismissProgress();
    }

    @Override
    public void onRegisterProgress() {
        showProgress(getString(R.string.registering));
    }
}

