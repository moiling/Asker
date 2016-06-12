package com.moinut.asker.ui.vu;

import com.moinut.asker.model.bean.User;

public interface ILoginView {

    void onShouldRegister(String accountId, String password);
    void onLoginSuccess(User user);
    void onLoginError(String errorInfo);
    void onLoginProgress();
}
