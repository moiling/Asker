package com.moinut.asker.model.network;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.moinut.asker.BuildConfig;
import com.moinut.asker.config.Api;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.Answer;
import com.moinut.asker.model.bean.ApiWrapper;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.model.bean.StarInfo;
import com.moinut.asker.model.bean.Student;
import com.moinut.asker.model.bean.Teacher;
import com.moinut.asker.model.bean.UploadWrapper;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.model.network.service.ApiService;
import com.moinut.asker.model.network.service.UpDownloadService;
import com.moinut.asker.model.network.setting.QualifiedTypeConverterFactory;
import com.moinut.asker.model.network.setting.func.ApiWrapperFunc;
import com.moinut.asker.model.network.setting.func.PageWrapperFunc;
import com.moinut.asker.utils.OkHttpUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public enum RequestManager {

    INSTANCE;

    private static final int DEFAULT_TIMEOUT = 30;
    private ApiService mApiService;
    private UpDownloadService mUpDownloadService;
    private OkHttpClient mOkHttpClient;

    RequestManager() {
        mOkHttpClient = configureOkHttp(new OkHttpClient.Builder());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.END_POINT_MOINUT)
                .client(mOkHttpClient)
                .addConverterFactory(new QualifiedTypeConverterFactory(
                        GsonConverterFactory.create(), SimpleXmlConverterFactory.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
        mUpDownloadService = retrofit.create(UpDownloadService.class);
    }

    public static RequestManager getInstance() {
        return INSTANCE;
    }

    @NonNull
    public OkHttpClient configureOkHttp(OkHttpClient.Builder builder) {
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    private <T> Subscription emitObservable(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    // ------------------------------------------------------------------

    public void download(String url, Subscriber<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = mUpDownloadService.download(url);
        emitObservable(observable, subscriber);
    }

    /* 测试上传文件 */
    @Deprecated
    public void uploadTest(Subscriber<String> subscriber, Uri fileUri) {
        Observable<String> observable = mUpDownloadService.uploadTest(
                /* 请求地址 */UpDownloadService.TEST_UPLOAD_URL,
                /* 除了文件，其他POST参数 *///OkHttpUtils.createStringRequestBody("values"),
                /* 文件，"file"是参数名 */OkHttpUtils.createFileRequestBody("file", fileUri))
                .map(UploadWrapper::getInfo);

        emitObservable(observable, subscriber);
    }

    // ------------------------------------------------------------------

    public Subscription upload(Subscriber<UploadWrapper> subscriber, Uri fileUri) {
        Observable<UploadWrapper> observable = mApiService.upload(
                Api.END_POINT_MOINUT + Api.API_UPLOAD,
                OkHttpUtils.createFileRequestBody("file", fileUri)
        );
        return emitObservable(observable, subscriber);
    }

    public Subscription login(Subscriber<ApiWrapper<User>> subscriber, String accountId, String password) {
        Observable<ApiWrapper<User>> observable = mApiService.login(accountId, password);
        return emitObservable(observable, subscriber);
    }

    public Subscription register(Subscriber<String> subscriber, String accountId, String password, String type) {
        Observable<String> observable = mApiService.register(accountId, password, type)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription searchAllQuestions(Subscriber<List<Question>> subscriber, int page, int count, String token, String search) {
        Observable<List<Question>> observable = mApiService.searchAllQuestions(page, count, token, search)
                .map(new PageWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getAllQuestions(Subscriber<List<Question>> subscriber, int page, int count, String token) {
        Observable<List<Question>> observable = mApiService.getAllQuestions(page, count, token)
                .map(new PageWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getStarQuestions(Subscriber<List<Question>> subscriber, int page, int count, String token) {
        Observable<List<Question>> observable = mApiService.getStarQuestions(page, count, token)
                .map(new PageWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getMyQuestions(Subscriber<List<Question>> subscriber, int page, int count, String token) {
        Observable<List<Question>> observable = mApiService.getMyQuestions(page, count, token)
                .map(new PageWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription askQuestion(Subscriber<String> subscriber, String token, String title, String content, String type) {
        Observable<String> observable = mApiService.askQuestion(token, title, content, type)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }


    public Subscription getStudentInfo(Subscriber<Student> subscriber, String token) {
        Observable<Student> observable = mApiService.getStudentInfo(token, Const.API_STUDENT)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getTeacherInfo(Subscriber<Teacher> subscriber, String token) {
        Observable<Teacher> observable = mApiService.getTeacherInfo(token, Const.API_TEACHER)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription updateStudentInfo(Subscriber<String> subscriber, String token,
                                          String nickName, String sex, String tel, String email,
                                          String college, String academy, int year, String major) {
        Observable<String> observable = mApiService.updateUserInfo(token, Const.API_STUDENT, nickName,
                sex, tel, email, college, academy, year, major, "")
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription updateTeacherInfo(Subscriber<String> subscriber, String token,
                                          String nickName, String sex, String tel, String email,
                                          String college, String academy, String realName) {
        Observable<String> observable = mApiService.updateUserInfo(token, Const.API_TEACHER, nickName,
                sex, tel, email, college, academy, 0, "", realName)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription updatePortrait(Subscriber<String> subscriber, String token, String portrait) {
        Observable<String> observable = mApiService.updatePortrait(token, portrait)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription answer(Subscriber<String> subscriber, String token, int questionId, String content) {
        Observable<String> observable = mApiService.answer(token, questionId, content)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getAnswers(Subscriber<List<Answer>> subscriber, int questionId, int page, int count) {
        Observable<List<Answer>> observable = mApiService.getAnswers(questionId, page, count)
                .map(new PageWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription starQuestion(Subscriber<StarInfo> subscriber, String token, int questionId) {
        Observable<StarInfo> observable = mApiService.starQuestion(token, questionId)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription likeAnswer(Subscriber<Integer> subscriber, String token, int answerId, String type) {
        Observable<Integer> observable = mApiService.likeAnswer(token, answerId, type)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }
}
