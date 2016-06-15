package com.moinut.asker.presenter;

import android.content.Context;
import android.widget.Toast;

import com.moinut.asker.APP;
import com.moinut.asker.R;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.Student;
import com.moinut.asker.model.bean.Teacher;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.model.network.RequestManager;
import com.moinut.asker.model.subscriber.SimpleSubscriber;
import com.moinut.asker.model.subscriber.SubscriberListener;
import com.moinut.asker.ui.vu.IUserInfoView;

import retrofit2.adapter.rxjava.HttpException;

public class UserInfoPresenter extends BasePresenter<IUserInfoView> {

    private String userType;
    private User user;

    public UserInfoPresenter(Context context, IUserInfoView iUserInfoView, String userType) {
        super(context, iUserInfoView);
        this.userType = userType;
    }

    public void updateStudent(String token, Student student) {
        Student old = (Student) user;
        if (old != null) {
            if (student.equals(old)) {
                Toast.makeText(context, R.string.not_change, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (v != null) {
            v.onUpdateProgress();
            RequestManager.getInstance().updateStudentInfo(new SimpleSubscriber<>(context, new SubscriberListener<String>() {
                @Override
                public void onError(Throwable e) {
                    doError(e);
                }

                @Override
                public void onNext(String s) {
                    if (v != null) v.onUpdateSuccess(s);
                    APP.setUser(context, student);
                }
            }), token, student.getNickName(), student.getSex(), student.getTel(), student.getEmail(),
                    student.getCollege(), student.getAcademy(), student.getYear(), student.getMajor());

        }
    }

    public void updateTeacher(String token, Teacher teacher) {
        Teacher old = (Teacher) user;
        if (old != null) {
            if (teacher.equals(old)) {
                Toast.makeText(context, R.string.not_change, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (v != null) {
            v.onUpdateProgress();
            RequestManager.getInstance().updateTeacherInfo(new SimpleSubscriber<>(context, new SubscriberListener<String>() {
                @Override
                public void onError(Throwable e) {
                    doError(e);
                }

                @Override
                public void onNext(String s) {
                    if (v != null) v.onUpdateSuccess(s);
                    APP.setUser(context, teacher);
                }
            }), token, teacher.getNickName(), teacher.getSex(), teacher.getTel(), teacher.getEmail(),
            teacher.getCollege(), teacher.getAcademy(), teacher.getRealName());
        }
    }

    public void get(String token) {
        if (v != null) {
            v.onGetProgress();
            if (userType.equals(Const.API_STUDENT)) {
                RequestManager.getInstance().getStudentInfo(new SimpleSubscriber<>(context, new SubscriberListener<Student>() {
                    @Override
                    public void onNext(Student student) {
                        if (v != null) v.onGetSuccess(student);
                        UserInfoPresenter.this.user = student;
                    }

                    @Override
                    public void onError(Throwable e) {
                        doError(e);
                    }
                }), token);
                return;
            }
            if (userType.equals(Const.API_TEACHER)) {
                RequestManager.getInstance().getTeacherInfo(new SimpleSubscriber<>(context, new SubscriberListener<Teacher>() {
                    @Override
                    public void onNext(Teacher teacher) {
                        if (v != null) v.onGetSuccess(teacher);
                        UserInfoPresenter.this.user = teacher;
                    }

                    @Override
                    public void onError(Throwable e) {
                        doError(e);
                    }
                }), token);
            }
        }
    }

    private void doError(Throwable e) {
        e.printStackTrace();
        if (v != null) {
            if (e instanceof HttpException) {
                if (((HttpException) e).code() == 401) {
                    v.onGetError(context.getString(R.string.token_out_date_login_again));
                } else {
                    v.onGetError(((HttpException) e).message());
                }
            } else {
                v.onGetError(e.toString());
            }
        }
    }

    public String getUserType() {
        return userType;
    }

    public User getUser() {
        return user;
    }
}
