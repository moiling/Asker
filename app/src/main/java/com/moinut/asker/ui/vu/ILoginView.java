package com.moinut.asker.ui.vu;

import com.moinut.asker.model.bean.User;

public interface ILoginView {
    void onLoginSuccess(User user);
    void onLoginError(String errorInfo);
}
