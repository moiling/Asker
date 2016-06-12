package com.moinut.asker.ui.vu;

import com.moinut.asker.model.bean.Question;

import java.util.List;

public interface IQuestionView {

    void onRefreshEnd(List<Question> questions);
    void onLoadMoreEnd(List<Question> questions);
}
