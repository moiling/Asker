package com.moinut.asker.presenter;

import android.content.Context;

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
                        e.printStackTrace();
                        if (v != null) {
                            if (e instanceof HttpException) {
                                if (((HttpException) e).code() == 401) {
                                    v.onGetError("本地储存账号信息过期\n请重新登录!");
                                } else {
                                    v.onGetError(((HttpException) e).message());
                                }
                            } else {
                                v.onGetError(e.toString());
                            }
                        }
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
                        e.printStackTrace();
                        if (v != null) {
                            if (e instanceof HttpException) {
                                if (((HttpException) e).code() == 401) {
                                    v.onGetError("本地储存账号信息过期\n请重新登录!");
                                } else {
                                    v.onGetError(((HttpException) e).message());
                                }
                            } else {
                                v.onGetError(e.toString());
                            }
                        }
                    }
                }), token);
            }
        }
    }

    public String getUserType() {
        return userType;
    }
}
