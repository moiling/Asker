package com.moinut.asker.ui.vu;

public interface IRegisterView {

    void onRegisterSuccess(String info);
    void onRegisterError(String info);
    void onRegisterProgress();
}
