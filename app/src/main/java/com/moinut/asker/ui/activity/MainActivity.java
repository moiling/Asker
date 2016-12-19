package com.moinut.asker.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.ui.fragment.MeFragment;
import com.moinut.asker.ui.fragment.QuestionFragment;
import com.moinut.asker.ui.fragment.SearchFragment;
import com.moinut.asker.ui.fragment.StarFragment;
import com.moinut.asker.utils.FragUtils;
import com.moinut.asker.utils.ToolbarUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;

    private TextView mUserName;
    private ImageView mUserPortrait;
    private SearchView mSearchView;

    private User mUser;
    private QuestionFragment mQuestionFragment;
    private MeFragment mMeFragment;
    private StarFragment mStarFragment;
    private SearchFragment mSearchFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFrag();
        initView();
    }

    private void initFrag() {
        mQuestionFragment = new QuestionFragment();
        mMeFragment = new MeFragment();
        mStarFragment = new StarFragment();
        mSearchFragment = new SearchFragment();

        if (!mQuestionFragment.isAdded()) {
            FragUtils.addFragmentToActivity(getSupportFragmentManager(), mCurrentFragment = mQuestionFragment, R.id.content_main);
        } else {
            mCurrentFragment = mQuestionFragment;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUser();
    }

    private void initUser() {
        if ((mUser = APP.getUser(this)) == null) {
            mUserName.setText(R.string.click_to_login);
            mUserName.setOnClickListener(new ToLogin());
            mUserPortrait.setOnClickListener(new ToLogin());
        } else {
            String name = mUser.getNickName();
            if (name == null) {
                mUserName.setText(R.string.please_edit_info);
            } else {
                mUserName.setText(name);
            }
            mUserName.setOnClickListener(new ToDetails());
            mUserPortrait.setOnClickListener(new ToDetails());
        }
    }

    private void initView() {
        initToolbar();
        initNavHeader();

        mFab.setOnClickListener(view -> {
            ask();
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void ask() {
        if (APP.getUser(this) != null) {
            if (APP.getUser(this).getType().equals(Const.API_STUDENT)) {
                startActivity(new Intent(MainActivity.this, AskActivity.class));
            } else {
                Toast.makeText(this, R.string.only_student_can_ask, Toast.LENGTH_SHORT).show();
            }
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private void initNavHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        mUserName = (TextView) headerView.findViewById(R.id.tv_user_name);
        mUserPortrait = (ImageView) headerView.findViewById(R.id.iv_user_portrait);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        TextView title = ToolbarUtils.getToolbarTitleView(this, mToolbar);
        if (title != null)
            title.setTypeface(Typeface.SERIF);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO 搜索
                if (mCurrentFragment instanceof QuestionFragment) {
                    ((QuestionFragment) mCurrentFragment).search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();
        switch (id) {
            case R.id.nav_question:
                if (mCurrentFragment != mQuestionFragment)
                    FragUtils.startAnotherFragment(manager, mCurrentFragment, mCurrentFragment = mQuestionFragment, R.id.content_main);
                mFab.setVisibility(View.VISIBLE);
                mToolbar.setTitle(R.string.asker);
                break;
            case R.id.nav_stars:
                if (mCurrentFragment != mStarFragment)
                    FragUtils.startAnotherFragment(manager, mCurrentFragment, mCurrentFragment = mStarFragment, R.id.content_main);
                mFab.setVisibility(View.GONE);
                mToolbar.setTitle(R.string.star);
                break;
            case R.id.nav_me:
                if (mCurrentFragment != mMeFragment)
                    FragUtils.startAnotherFragment(manager, mCurrentFragment, mCurrentFragment = mMeFragment, R.id.content_main);
                mFab.setVisibility(View.GONE);
                mToolbar.setTitle(R.string.me);
                break;
            case R.id.nav_search:
                if (mCurrentFragment != mSearchFragment)
                    FragUtils.startAnotherFragment(manager, mCurrentFragment, mCurrentFragment = mSearchFragment, R.id.content_main);
                mFab.setVisibility(View.GONE);
                mToolbar.setTitle(R.string.search);
                break;
            //case R.id.nav_settings:
            //    break;
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                break;
            case R.id.nav_ask:
                ask();
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public FloatingActionButton getFab() {
        return mFab;
    }

    public AppBarLayout getAppBar() {
        return mAppBar;
    }

    private class ToLogin implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private class ToDetails implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, UserDetailActivity.class));
        }
    }
}
