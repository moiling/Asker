package com.moinut.asker.ui.adapter.itemview;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.model.bean.StarInfo;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;

import retrofit2.adapter.rxjava.HttpException;

import static com.moinut.asker.APP.getContext;

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
        TextView title = (TextView) headerView.findViewById(R.id.tv_title);
        TextView content = (TextView) headerView.findViewById(R.id.tv_content);
        TextView author = (TextView) headerView.findViewById(R.id.tv_author);
        TextView date = (TextView) headerView.findViewById(R.id.tv_date);
        TextView type = (TextView) headerView.findViewById(R.id.tv_type);
        TextView answerCount = (TextView) headerView.findViewById(R.id.tv_answer_count);
        TextView starCount = (TextView) headerView.findViewById(R.id.tv_star_count);
        LinearLayout star = (LinearLayout) headerView.findViewById(R.id.ll_star_count);
        ImageView starImage = (ImageView) headerView.findViewById(R.id.iv_star_count);

        title.setText(mQuestion.getTitle());
        content.setText(mQuestion.getContent());
        String name = mQuestion.getAuthorName();
        if (name == null) {
            author.setText(R.string.no_name_user);
        } else {
            author.setText(mQuestion.getAuthorName());
        }
        date.setText(mQuestion.getDateFormat());
        type.setText(mQuestion.getType());
        answerCount.setText(mQuestion.getAnswerCount() + "");
        starCount.setText(mQuestion.getStarCount() + "");
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
                }), APP.getUser(getContext()).getToken(), mQuestion.getId());
            } else {
                Toast.makeText(getContext(), R.string.please_login, Toast.LENGTH_SHORT).show();
            }
        });
        starImage.setColorFilter(mQuestion.isStared() ? ContextCompat.getColor(getContext(), R.color.colorAccent)
                : ContextCompat.getColor(getContext(), R.color.iconGrey));
    }
}
