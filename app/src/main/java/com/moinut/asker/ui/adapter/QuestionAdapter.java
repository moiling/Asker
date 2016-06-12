package com.moinut.asker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.ui.viewholder.QuestionViewHolder;

public class QuestionAdapter extends RecyclerArrayAdapter<Question> {

    public QuestionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false));
    }
}
