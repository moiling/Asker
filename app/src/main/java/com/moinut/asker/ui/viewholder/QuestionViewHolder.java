package com.moinut.asker.ui.viewholder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.model.bean.StarInfo;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;

import retrofit2.adapter.rxjava.HttpException;

public class QuestionViewHolder extends BaseViewHolder<Question> {

    private TextView title;
    private TextView content;
    private TextView author;
    private TextView date;
    private TextView type;
    private TextView answerCount;
    private TextView starCount;
    private LinearLayout star;
    private ImageView starImage;

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
        starImage = $(R.id.iv_star_count);
    }

    @Override
    public void setData(Question data) {
        title.setText(data.getTitle());
        content.setText(data.getContent());
        String name = data.getAuthorName();
        if (name == null) {
            author.setText(R.string.no_name_user);
        } else {
            author.setText(data.getAuthorName());
        }
        date.setText(data.getDateFormat());
        type.setText(data.getType());
        answerCount.setText(data.getAnswerCount() + "");
        starCount.setText(data.getStarCount() + "");
        star.setOnClickListener(v -> {
            if (APP.getUser(getContext()) != null) {
                RequestManager.getInstance().starQuestion(new SimpleSubscriber<>(getContext(), new SubscriberListener<StarInfo>() {
                    @Override
                    public void onNext(StarInfo starInfo) {
                        starImage.setColorFilter(starInfo.getType().equals(Const.API_STAR) ? ContextCompat.getColor(getContext(), R.color.colorAccent)
                                : ContextCompat.getColor(getContext(), R.color.iconGrey));
                        starCount.setText(starInfo.getCount() + "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            if (((HttpException) e).code() == 401) {
                                Toast.makeText(getContext(), R.string.token_out_date_login_again, Toast.LENGTH_SHORT).show();
                            }
                        }
                        // 其他不想处理……
                    }
                }), APP.getUser(getContext()).getToken(), data.getId());
            } else {
                Toast.makeText(getContext(), R.string.please_login, Toast.LENGTH_SHORT).show();
            }
        });
        starImage.setColorFilter(data.isStared() ? ContextCompat.getColor(getContext(), R.color.colorAccent)
                                                 : ContextCompat.getColor(getContext(), R.color.iconGrey));
    }
}
