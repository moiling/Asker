package com.moinut.asker.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        mToolbar.setTitle("Login");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void login() {
        if (mEditAccountId.getText().toString().isEmpty()) {
            mEditAccountId.setError("账号不能为空");
            return;
        }
        if (mEditPassword.getText().toString().isEmpty()) {
            mEditPassword.setError("密码不能为空");
            return;
        }
        mLoginPresenter.login(mEditAccountId.getText().toString(), mEditPassword.getText().toString());
    }

    @Override
    public void onShouldRegister(String accountId, String password) {
        dismissProgress();
        Toast.makeText(this, "未注册", Toast.LENGTH_SHORT).show();
        // TODO 应该弹出选择框之类的
        mRegisterPresenter.register(accountId, password, Const.API_STUDENT);
    }

    @Override
    public void onLoginSuccess(User user) {
        dismissProgress();
        finish();
    }

    @Override
    public void onLoginError(String errorInfo) {
        dismissProgress();
        showDialog("ERROR", errorInfo);
    }

    @Override
    public void onLoginProgress() {
        showProgress("登录中");
    }

    @Override
    public void onRegisterSuccess(String info) {
        dismissProgress();
        showDialog("SUCCESS", info);
    }

    @Override
    public void onRegisterError(String info) {
        showDialog("ERROR", info);
        dismissProgress();
    }

    @Override
    public void onRegisterProgress() {
        showProgress("注册中");
    }
}

