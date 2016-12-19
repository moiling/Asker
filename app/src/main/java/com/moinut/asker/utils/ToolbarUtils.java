package com.moinut.asker.utils;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * @author moi
 */

public class ToolbarUtils {

    public static TextView getToolbarTitleView(AppCompatActivity activity, Toolbar toolbar) {
        ActionBar actionBar = activity.getSupportActionBar();
        CharSequence actionbarTitle = null;

        if (actionBar != null)
            actionbarTitle = actionBar.getTitle();

        actionbarTitle = TextUtils.isEmpty(actionbarTitle) ? toolbar.getTitle() : actionbarTitle;

        if (TextUtils.isEmpty(actionbarTitle))
            return null;

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View v = toolbar.getChildAt(i);

            if (v != null && v instanceof TextView) {
                TextView t = (TextView) v;
                CharSequence title = t.getText();
                if (!TextUtils.isEmpty(title) && actionbarTitle.equals(title) && t.getId() == View.NO_ID) {
                    return t;
                }
            }
        }

        return null;
    }
}
