package com.moinut.asker.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.Answer;

public class AnswerViewHolder extends BaseViewHolder<Answer> {

    private TextView author;
    private TextView content;
    private TextView date;
    private TextView likeNumber;
    private ImageView like;
    private ImageView dislike;

    public AnswerViewHolder(View itemView) {
        super(itemView);
        author = $(R.id.tv_author);
        content = $(R.id.tv_content);
        date = $(R.id.tv_date);
        likeNumber = $(R.id.tv_like_number);
        like = $(R.id.iv_like);
        dislike = $(R.id.iv_dislike);
    }

    @Override
    public void setData(Answer data) {
        super.setData(data);
        String name = data.getAuthorName();
        if (name == null) {
            author.setText("一位没有名字的用户");
        } else {
            author.setText(data.getAuthorName());
        }
        content.setText(data.getContent());
        date.setText(data.getDateFormat());
        likeNumber.setText(data.getLikeNumber() - data.getDislikeNumber() + "");
        like.setOnClickListener(v -> Toast.makeText(v.getContext(), "喜欢", Toast.LENGTH_SHORT).show());
        dislike.setOnClickListener(v -> Toast.makeText(v.getContext(), "不喜欢", Toast.LENGTH_SHORT).show());
    }
}
