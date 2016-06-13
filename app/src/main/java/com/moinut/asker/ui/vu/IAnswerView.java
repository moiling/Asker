package com.moinut.asker.ui.vu;

import com.moinut.asker.model.bean.Answer;

import java.util.List;

public interface IAnswerView {

    void onRefreshEnd(List<Answer> answers);
    void onLoadMoreEnd(List<Answer> answers);
}
