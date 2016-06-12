package com.moinut.asker.ui.vu;

public interface IAskView {

    void onAskSuccess(String info);
    void onAskError(String info);
    void onProgress();
}
