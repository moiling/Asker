package com.moinut.asker.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewStubCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.config.Api;
import com.moinut.asker.config.Const;
import com.moinut.asker.event.ExitEvent;
import com.moinut.asker.event.UpdatePortraitEvent;
import com.moinut.asker.model.bean.Student;
import com.moinut.asker.model.bean.Teacher;
import com.moinut.asker.model.bean.UploadWrapper;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.presenter.UserInfoPresenter;
import com.moinut.asker.ui.vu.IUserInfoView;
import com.moinut.asker.utils.ToolbarUtils;
import com.moinut.picrop.CropIntent;
import com.moinut.picrop.PiCrop;
import com.moinut.picrop.callback.OnCropListener;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

@SuppressWarnings("WeakerAccess")
public class UserDetailActivity extends BaseActivity implements IUserInfoView {

    public static final String TAG = UserDetailActivity.class.getSimpleName();

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
    @Bind(R.id.iv_edit_portrait)
    ImageView mEditPortrait;

    // Student
    private EditText mEditYear;
    private EditText mEditMajor;
    // Teacher
    private EditText mEditRealName;
    // Comment
    private EditText mEditCollege;
    private EditText mEditAcademy;

    private String mToken;
    private UserInfoPresenter mUserInfoPresenter;

    private PiCrop mPiCrop;

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        mPiCrop = new PiCrop(this);
        getType();
        initView();
        mUserInfoPresenter.get(mToken);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mUserInfoPresenter.onRelieveView();
    }

    private void getType() {
        User currentUser;
        if ((currentUser = APP.getUser(this)) != null) {
            mUserInfoPresenter = new UserInfoPresenter(this, this, currentUser.getType());
            mToken = currentUser.getToken();
            loadPortrait(currentUser.getPortrait());
        } else {
            Toast.makeText(this, R.string.un_login_how_to_come_in, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        initToolbar();

        mBtnExit.setOnClickListener(v -> {
            APP.exitUser(this);
            EventBus.getDefault().post(new ExitEvent());
            finish();
        });


        mEditPortrait.setOnClickListener(v -> {

            mPiCrop.setCropIntent(new CropIntent.Builder()
                    .setSaveUri(Uri.parse("file://"
                            + this.getExternalFilesDir(null).getPath()
                            + "/"
                            + System.currentTimeMillis()
                            + APP.getUser(this).getId()
                            + ".jpg")));

            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            // All requested permissions are granted
                            mPiCrop.get(PiCrop.FROM_ALBUM, new OnCropListener() {
                                @Override
                                public void onStart() {
                                    // When PiCrop start working. You can do something like show a progressbar.
                                }

                                @Override
                                public void onSuccess(Uri picUri) {
                                    RequestManager.getInstance().upload(new Subscriber<UploadWrapper>() {
                                        @Override
                                        public void onCompleted() { }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onNext(UploadWrapper uploadWrapper) {
                                            Log.i(TAG, "onNext: " + uploadWrapper.toString());
                                            if (uploadWrapper.getCode() == Api.API_STATUS_SUCCESS) {
                                                mUserInfoPresenter.updatePortrait(mToken, uploadWrapper.getUrl());
                                            }
                                        }
                                    }, picUri);
                                }

                                @Override
                                public void onError(String errorMsg) {
                                    Log.e(TAG, "onError: " + errorMsg);
                                }
                            });
                        } else {
                            // At least one permission is denied
                        }
                    });
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
        mToolbar.setTitle(R.string.detail);
        setSupportActionBar(mToolbar);
        TextView title = ToolbarUtils.getToolbarTitleView(this, mToolbar);
        if (title != null)
            title.setTypeface(Typeface.SERIF);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    private void showStudent(Student student) {
        mEditName.setText(student.getNickName());
        mEditAcademy.setText(student.getAcademy());
        mEditCollege.setText(student.getCollege());
        mEditEmail.setText(student.getEmail());
        mEditTel.setText(student.getTel());
        mEditMajor.setText(student.getMajor());

        if (student.getYear() != 0)
            mEditYear.setText(String.format(Locale.getDefault(), "%d", student.getYear()));
        if (student.getSex() != null && student.getSex().equals(Const.API_FEMALE)) {
            mRbSexFemale.setChecked(true);
        } else {
            mRbSexMale.setChecked(true);
        }
    }

    private void showTeacher(Teacher teacher) {
        mEditName.setText(teacher.getNickName());
        mEditAcademy.setText(teacher.getAcademy());
        mEditCollege.setText(teacher.getCollege());
        mEditEmail.setText(teacher.getEmail());
        mEditTel.setText(teacher.getTel());
        mEditRealName.setText(teacher.getRealName());

        if (teacher.getSex() != null && teacher.getSex().equals(Const.API_FEMALE)) {
            mRbSexFemale.setChecked(true);
        } else {
            mRbSexMale.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_done:
                send();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void send() {

        if (mUserInfoPresenter.getUserType().equals(Const.API_STUDENT)) {
            Student student;
            try {
                if (mUserInfoPresenter.getUser() != null) {
                    student = (Student) mUserInfoPresenter.getUser().clone();
                } else {
                    student = new Student();
                    student.setUser(APP.getUser(this));
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return;
            }

            String nickName = mEditName.getText().toString();
            String tel = mEditTel.getText().toString();
            String email = mEditEmail.getText().toString();
            String year = mEditYear.getText().toString();
            String college = mEditCollege.getText().toString();
            String academy = mEditAcademy.getText().toString();
            String major = mEditMajor.getText().toString();

            if (!nickName.isEmpty()) student.setNickName(nickName);
            student.setSex(mRbSexFemale.isChecked() ? Const.API_FEMALE : Const.API_MALE);
            if (!tel.isEmpty()) student.setTel(tel);
            Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches() && !email.isEmpty()) {
                mEditEmail.setError(getString(R.string.wrong_email));
                return;
            }
            if (!email.isEmpty()) student.setEmail(email);
            pattern = Pattern.compile("^\\d{4}$");
            matcher = pattern.matcher(year);
            if (!matcher.matches() && !year.isEmpty()) {
                mEditYear.setError(getString(R.string.wrong_grade_year));
                return;
            }
            if (!year.isEmpty()) student.setYear(Integer.parseInt(year));
            if (!college.isEmpty()) student.setCollege(college);
            if (!academy.isEmpty()) student.setAcademy(academy);
            if (!major.isEmpty()) student.setMajor(major);

            mUserInfoPresenter.updateStudent(mToken, student);
            return;
        }
        if (mUserInfoPresenter.getUserType().equals(Const.API_TEACHER)) {
            Teacher teacher;
            try {
                if (mUserInfoPresenter.getUser() != null) {
                    teacher = (Teacher) mUserInfoPresenter.getUser().clone();
                } else {
                    teacher = new Teacher();
                    teacher.setUser(APP.getUser(this));
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return;
            }

            String nickName = mEditName.getText().toString();
            String tel = mEditTel.getText().toString();
            String email = mEditEmail.getText().toString();
            String college = mEditCollege.getText().toString();
            String academy = mEditAcademy.getText().toString();
            String realName = mEditRealName.getText().toString();

            if (!nickName.isEmpty()) teacher.setNickName(nickName);
            teacher.setSex(mRbSexFemale.isChecked() ? Const.API_FEMALE : Const.API_MALE);
            if (!tel.isEmpty()) teacher.setTel(tel);
            Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                mEditEmail.setError(getString(R.string.wrong_email));
                return;
            }
            if (!email.isEmpty()) teacher.setEmail(email);
            if (!college.isEmpty()) teacher.setCollege(college);
            if (!academy.isEmpty()) teacher.setAcademy(academy);
            if (!realName.isEmpty()) teacher.setRealName(realName);

            mUserInfoPresenter.updateTeacher(mToken, teacher);
        }
    }

    @Override
    public void onGetSuccess(User user) {
        dismissProgress();
        if (user != null) {
            if (user instanceof Student) showStudent((Student) user);
            else if (user instanceof Teacher) showTeacher((Teacher) user);
        }
    }

    @Override
    public void onGetError(String info) {
        dismissProgress();
        showDialog(getString(R.string.error), info);
    }


    @Override
    public void onGetProgress() {
        showProgress(getString(R.string.getting_info));
    }

    @Override
    public void onUpdateSuccess(String info) {
        dismissProgress();
        showDialog(getString(R.string.success), info);
    }

    @Override
    public void onUpdateError(String info) {
        dismissProgress();
        showDialog(getString(R.string.error), info);
    }

    @Override
    public void onUpdateProgress() {
        showProgress(getString(R.string.updating_info));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPiCrop.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdatePortraitEvent(UpdatePortraitEvent event){
        if (APP.getUser(this) != null)
            loadPortrait(APP.getUser(this).getPortrait());
    }

    private void loadPortrait(String portrait) {
        if (portrait != null) {
            Glide.with(this)
                    .load(portrait)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.default_portrait)
                    .error(R.drawable.error_portrait)
                    .into(new BitmapImageViewTarget(mEditPortrait) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mEditPortrait.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            Glide.with(this)
                    .load(R.drawable.default_portrait)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.default_portrait)
                    .error(R.drawable.error_portrait)
                    .into(new BitmapImageViewTarget(mEditPortrait) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mEditPortrait.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }

}
