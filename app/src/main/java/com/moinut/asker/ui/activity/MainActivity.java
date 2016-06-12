package com.moinut.asker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.ui.fragment.QuestionFragment;
import com.moinut.asker.utils.FragUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    private TextView mUserName;
    private SearchView mSearchView;

    private User mUser;
    private QuestionFragment mQuestionFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initUser();

        FragmentManager manager = getSupportFragmentManager();
        if (mQuestionFragment == null) {
            FragUtils.addFragmentToActivity(manager, mCurrentFragment = mQuestionFragment = new QuestionFragment(), R.id.content_main);
        } else {
            FragUtils.startAnotherFragment(manager, mCurrentFragment, mQuestionFragment, R.id.content_main);
        }
    }

    private void initUser() {
        // TODO 测试登录
        APP.setUser(this, new Gson().fromJson("{"+
                "    \"id\": 5,\n" +
                "    \"type\": \"student\",\n" +
                "    \"nickName\": \"MOILING\",\n" +
                "    \"date\": \"2016-06-01 17:47:21\",\n" +
                "    \"sex\": \"male\",\n" +
                "    \"tel\": \"110\",\n" +
                "    \"email\": \"super8moi@gmail.com\",\n" +
                "    \"token\": \"3e2d3c1fdb5ae064c435d07613c5ef9f4f482e38\""+
                "}", User.class));

        if ((mUser = APP.getUser(this)) == null) {
            mUserName.setText("未登录, 请点击登录");
            mUserName.setOnClickListener(new ToLogin());
        } else {
            mUserName.setText(mUser.getNickName());
            mUserName.setOnClickListener(new ToDetails());
        }
    }

    private void initView() {
        initToolbar();
        initNavHeader();
        mFab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AskActivity.class)));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initNavHeader() {
        View headerView = mNavigationView.getHeaderView(0);
        mUserName = (TextView) headerView.findViewById(R.id.tv_user_name);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
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
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public FloatingActionButton getFab() {
        return mFab;
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
            // TODO 跳转详细页面
        }
    }
}