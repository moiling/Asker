package com.moinut.asker.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.Student;
import com.moinut.asker.model.bean.Teacher;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.presenter.UserInfoPresenter;
import com.moinut.asker.ui.vu.IUserInfoView;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class UserDetailActivity extends BaseActivity implements IUserInfoView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btn_exit)
    Button mBtnExit;

    private String token;
    private UserInfoPresenter mUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        getType();
        initView();
        mUserInfoPresenter.get(token);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserInfoPresenter.onRelieveView();
    }

    private void getType() {
        User currentUser;
        if ((currentUser = APP.getUser(this)) != null) {
            mUserInfoPresenter = new UserInfoPresenter(this, this, currentUser.getType());
            token = currentUser.getToken();
        } else {
            Toast.makeText(this, "未登录, 你怎么进来的!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        initToolbar();
        mBtnExit.setOnClickListener(v -> {
            APP.exitUser(this);
            finish();
        });
    }

    private void initToolbar() {
        mToolbar.setTitle("Detail");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void showStudent(Student student) {
        Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show();
    }

    private void showTeacher(Teacher teacher) {
        Toast.makeText(this, teacher.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSuccess(Object o) {
        if (mUserInfoPresenter.getUserType().equals(Const.API_STUDENT)) showStudent((Student) o);
        else if (mUserInfoPresenter.getUserType().equals(Const.API_TEACHER)) showTeacher((Teacher) o);
    }

    @Override
    public void onGetError(String info) {
        showDialog("ERROR", info);
    }

    @Override
    public void onUpdateSuccess(String info) {

    }

    @Override
    public void onUpdateError(String info) {

    }

    @Override
    public void onGetProgress() {

    }

    @Override
    public void onUpdateProgress() {

    }

}
