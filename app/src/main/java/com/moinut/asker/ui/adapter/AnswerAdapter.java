package com.moinut.asker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.Answer;
import com.moinut.asker.ui.viewholder.AnswerViewHolder;

public class AnswerAdapter extends RecyclerArrayAdapter<Answer> {

    public AnswerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnswerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false));
    }
}
