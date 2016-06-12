package com.moinut.asker.model.network;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.moinut.asker.BuildConfig;
import com.moinut.asker.config.Api;
import com.moinut.asker.model.bean.Question;
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

    public Subscription login(Subscriber<User> subscriber, String accountId, String password) {
        Observable<User> observable = mApiService.login(accountId, password)
                .map(new ApiWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getAllQuestions(Subscriber<List<Question>> subscriber, int page, int count) {
        Observable<List<Question>> observable = mApiService.getAllQuestions(page, count)
                .map(new PageWrapperFunc<>());
        return emitObservable(observable, subscriber);
    }
}
