package com.moinut.asker.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.moinut.asker.R;

public abstract class BaseActivity extends AppCompatActivity {

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void showProgress(String title) {
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleColor(ContextCompat.getColor(this, R.color.primary_text))
                .backgroundColor(ContextCompat.getColor(this, R.color.white))
                .positiveColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .content(this.getResources().getString(R.string.please_wait))
                .theme(Theme.LIGHT)
                .progress(true, 100)
                .cancelable(true)
                .show();
    }

    protected void dismissProgress() {
        if (dialog != null) if (dialog.isShowing()) dialog.dismiss();
    }

    protected void showDialog(String title, String content) {
        new MaterialDialog.Builder(this)
                .title(title)
                .titleColor(ContextCompat.getColor(this, R.color.primary_text))
                .backgroundColor(ContextCompat.getColor(this, R.color.white))
                .positiveColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .content(content)
                .theme(Theme.LIGHT)
                .positiveText("OK")
                .positiveColor(ContextCompat.getColor(this, R.color.colorAccent))
                .show();
    }
}
