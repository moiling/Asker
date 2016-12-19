package com.moinut.asker.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.moinut.asker.R;
import com.moinut.asker.utils.AppUtils;
import com.moinut.asker.utils.ToolbarUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class AboutUsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_about_version)
    TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void init() {
        mVersion.setText(String.format("Ver.%s", AppUtils.getVersionName(this)));
    }

    private void initToolbar() {
        mToolbar.setTitle(getString(R.string.about));
        setSupportActionBar(mToolbar);
        TextView title = ToolbarUtils.getToolbarTitleView(this, mToolbar);
        if (title != null)
            title.setTypeface(Typeface.SERIF);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }
}
