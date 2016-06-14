package com.moinut.asker.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
    @Bind(R.id.edit_name)
    EditText mEditName;
    @Bind(R.id.rb_sex_male)
    AppCompatRadioButton mRbSexMale;
    @Bind(R.id.rb_sex_female)
    AppCompatRadioButton mRbSexFemale;
    @Bind(R.id.rg_sex)
    RadioGroup mRgSex;
    @Bind(R.id.edit_tel)
    EditText mEditTel;
    @Bind(R.id.edit_email)
    EditText mEditEmail;
    @Bind(R.id.view_stub_student_info)
    ViewStubCompat mStudentViewStub;
    @Bind(R.id.view_stub_teacher_info)
    ViewStubCompat mTeacherViewStub;

    // Student
    private EditText mEditYear;
    private EditText mEditMajor;
    // Teacher
    private EditText mEditRealName;
    // Comment
    private EditText mEditCollege;
    private EditText mEditAcademy;

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
        if (mUserInfoPresenter.getUserType().equals(Const.API_STUDENT)) {
            mStudentViewStub.inflate();
            mEditYear = (EditText) findViewById(R.id.edit_year);
            mEditMajor = (EditText) findViewById(R.id.edit_major);
        } else {
            mTeacherViewStub.inflate();
            mEditRealName = (EditText) findViewById(R.id.edit_real_name);
        }
        mEditCollege = (EditText) findViewById(R.id.edit_college);
        mEditAcademy = (EditText) findViewById(R.id.edit_academy);
    }

    private void initToolbar() {
        mToolbar.setTitle("Detail");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void showStudent(Student student) {
        mEditName.setText(student.getUser().getNickName());
        mEditAcademy.setText(student.getAcademy());
        mEditCollege.setText(student.getCollege());
        mEditEmail.setText(student.getUser().getEmail());
        mEditTel.setText(student.getUser().getTel());
        mEditMajor.setText(student.getMajor());
        mEditYear.setText(student.getYear() + "");
        if (student.getUser().getSex().equals(Const.API_FEMALE)) {
            mRbSexFemale.setChecked(true);
        } else {
            mRbSexMale.setChecked(true);
        }
    }

    private void showTeacher(Teacher teacher) {
        mEditName.setText(teacher.getUser().getNickName());
        mEditAcademy.setText(teacher.getAcademy());
        mEditCollege.setText(teacher.getCollege());
        mEditEmail.setText(teacher.getUser().getEmail());
        mEditTel.setText(teacher.getUser().getTel());
        mEditRealName.setText(teacher.getRealName());
        if (teacher.getUser().getSex().equals(Const.API_FEMALE)) {
            mRbSexFemale.setChecked(true);
        } else {
            mRbSexMale.setChecked(true);
        }
    }

    @Override
    public void onGetSuccess(Object o) {
        dismissProgress();
        if (mUserInfoPresenter.getUserType().equals(Const.API_STUDENT)) showStudent((Student) o);
        else if (mUserInfoPresenter.getUserType().equals(Const.API_TEACHER))
            showTeacher((Teacher) o);
    }

    @Override
    public void onGetError(String info) {
        dismissProgress();
        showDialog("ERROR", info);
    }


    @Override
    public void onGetProgress() {
        showProgress("获取信息中");
    }

    @Override
    public void onUpdateSuccess(String info) {
        dismissProgress();
        showDialog("SUCCESS", info);
    }

    @Override
    public void onUpdateError(String info) {
        dismissProgress();
        showDialog("ERROR", info);
    }

    @Override
    public void onUpdateProgress() {
        showProgress("更新信息中");
    }

}
