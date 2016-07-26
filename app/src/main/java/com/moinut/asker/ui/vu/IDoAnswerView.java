package com.moinut.asker.ui.vu;

public interface IDoAnswerView {

    void onDoAnswerSuccess(String info);
    void onDoAnswerError(String info);
    void onDoAnswerProgress();
}
