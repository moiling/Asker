package com.moinut.asker.ui.vu;

public interface IUserInfoView {

    void onGetSuccess(Object o);
    void onGetError(String info);
    void onUpdateSuccess(String info);
    void onUpdateError(String info);
    void onGetProgress();
    void onUpdateProgress();
}
