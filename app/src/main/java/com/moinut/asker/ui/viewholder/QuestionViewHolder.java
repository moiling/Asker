package com.moinut.asker.ui.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.Question;

public class QuestionViewHolder extends BaseViewHolder<Question> {

    private TextView title;
    private TextView content;
    private TextView author;
    private TextView date;
    private TextView type;
    private TextView answerCount;
    private TextView starCount;
    private LinearLayout star;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        title = $(R.id.tv_title);
        content = $(R.id.tv_content);
        author = $(R.id.tv_author);
        date = $(R.id.tv_date);
        type = $(R.id.tv_type);
        answerCount = $(R.id.tv_answer_count);
        starCount = $(R.id.tv_star_count);
        star = $(R.id.ll_star_count);
    }

    @Override
    public void setData(Question data) {
        title.setText(data.getTitle());
        content.setText(data.getContent());
        String name = data.getAuthorName();
        if (name == null) {
            author.setText("一位没有名字的用户");
        } else {
            author.setText(data.getAuthorName());
        }
        date.setText(data.getDateFormat());
        type.setText(data.getType());
        answerCount.setText(data.getAnswerCount() + "");
        starCount.setText(data.getStarCount() + "");
        star.setOnClickListener(v -> {
            //aa
            Toast.makeText(v.getContext(), "收藏", Toast.LENGTH_SHORT).show();
        });
    }
}
