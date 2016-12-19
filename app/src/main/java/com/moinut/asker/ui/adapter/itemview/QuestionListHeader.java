package com.moinut.asker.ui.adapter.itemview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.moinut.asker.R;
import com.moinut.asker.ui.adapter.QuestionListHeaderAdapter;

/**
 * @author moi
 */

public class QuestionListHeader implements RecyclerArrayAdapter.ItemView {

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header_question_list, parent, false);
    }

    @Override
    public void onBindView(View headerView) {
        RollPagerView rollPagerView = (RollPagerView) headerView.findViewById(R.id.roll_pager_view);
        rollPagerView.setAdapter(new QuestionListHeaderAdapter(rollPagerView));
        rollPagerView.setHintView(null);
    }
}
