package com.moinut.asker.ui.vu;

import com.moinut.asker.model.bean.User;

public interface IUserInfoView {

    void onGetSuccess(User user);
    void onGetError(String info);
    void onUpdateSuccess(String info);
    void onUpdateError(String info);
    void onGetProgress();
    void onUpdateProgress();
}
