package com.moinut.asker.ui.adapter.itemview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.Question;

public class QuestionHeader implements RecyclerArrayAdapter.ItemView {

    private Question mQuestion;

    public QuestionHeader(Question question) {
        mQuestion = question;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_question, parent, false);
    }

    @Override
    public void onBindView(View headerView) {
        ((TextView)headerView.findViewById(R.id.tv_title)).setText(mQuestion.getTitle());
        ((TextView)headerView.findViewById(R.id.tv_content)).setText(mQuestion.getContent());
        String name = mQuestion.getAuthorName();
        if (name == null) {
            ((TextView)headerView.findViewById(R.id.tv_author)).setText("一位没有名字的用户");
        } else {
            ((TextView)headerView.findViewById(R.id.tv_author)).setText(name);
        }
        ((TextView)headerView.findViewById(R.id.tv_date)).setText(mQuestion.getDateFormat());
        ((TextView)headerView.findViewById(R.id.tv_type)).setText(mQuestion.getType());
        ((TextView)headerView.findViewById(R.id.tv_answer_count)).setText(mQuestion.getAnswerCount() + "");
        ((TextView)headerView.findViewById(R.id.tv_star_count)).setText(mQuestion.getStarCount() + "");
        headerView.findViewById(R.id.ll_star_count).setOnClickListener(v -> {
            //aa
            Toast.makeText(v.getContext(), "收藏", Toast.LENGTH_SHORT).show();
        });
    }
}
