package com.moinut.asker.model.network.service;

import com.moinut.asker.config.Api;
import com.moinut.asker.model.bean.Answer;
import com.moinut.asker.model.bean.ApiWrapper;
import com.moinut.asker.model.bean.PageWrapper;
import com.moinut.asker.model.bean.Question;
import com.moinut.asker.model.bean.StarInfo;
import com.moinut.asker.model.bean.Student;
import com.moinut.asker.model.bean.Teacher;
import com.moinut.asker.model.bean.UploadWrapper;
import com.moinut.asker.model.bean.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {

    @Multipart
    @POST
    Observable<UploadWrapper> upload(@Url String url,
                                     @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST(Api.API_LOGIN)
    Observable<ApiWrapper<User>> login(
            @Field("accountId") String accountId,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Api.API_SEARCH_ALL_QUESTIONS)
    Observable<PageWrapper<List<Question>>> searchAllQuestions(
            @Field("page") int page,
            @Field("count") int count,
            @Field("token") String token,
            @Field("search") String search
    );

    @FormUrlEncoded
    @POST(Api.API_GET_ALL_QUESTIONS)
    Observable<PageWrapper<List<Question>>> getAllQuestions(
            @Field("page") int page,
            @Field("count") int count,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST(Api.API_GET_STAR_QUESTIONS)
    Observable<PageWrapper<List<Question>>> getStarQuestions(
            @Field("page") int page,
            @Field("count") int count,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST(Api.API_GET_MY_QUESTIONS)
    Observable<PageWrapper<List<Question>>> getMyQuestions(
            @Field("page") int page,
            @Field("count") int count,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST(Api.API_ASK_QUESTION)
    Observable<ApiWrapper<String>> askQuestion(
            @Field("token") String token,
            @Field("title") String title,
            @Field("content") String content,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(Api.API_REGISTER)
    Observable<ApiWrapper<String>> register(
            @Field("accountId") String accountId,
            @Field("password") String password,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(Api.API_GET_USER_INFO)
    Observable<ApiWrapper<Student>> getStudentInfo(
            @Field("token") String token,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(Api.API_GET_USER_INFO)
    Observable<ApiWrapper<Teacher>> getTeacherInfo(
            @Field("token") String token,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(Api.API_UPDATE_USER_INFO)
    Observable<ApiWrapper<String>> updateUserInfo(
            @Field("token") String token,
            @Field("type") String type,
            @Field("nickName") String nickName,
            @Field("sex") String sex,
            @Field("tel") String tel,
            @Field("email") String email,
            @Field("college") String college,
            @Field("academy") String academy,
            @Field("year") int year,
            @Field("major") String major,
            @Field("realName") String realName
    );

    @FormUrlEncoded
    @POST(Api.API_ANSWER)
    Observable<ApiWrapper<String>> answer(
            @Field("token") String token,
            @Field("questionId") int questionId,
            @Field("content") String content
    );

    @FormUrlEncoded
    @POST(Api.API_GET_ANSWERS)
    Observable<PageWrapper<List<Answer>>> getAnswers(
            @Field("questionId") int questionId,
            @Field("page") int page,
            @Field("count") int count
    );

    @FormUrlEncoded
    @POST(Api.API_STAR_QUESTION)
    Observable<ApiWrapper<StarInfo>> starQuestion(
            @Field("token") String token,
            @Field("questionId") int questionId
    );

    @FormUrlEncoded
    @POST(Api.API_LIKE_ANSWER)
    Observable<ApiWrapper<Integer>> likeAnswer(
            @Field("token") String token,
            @Field("answerId") int answerId,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(Api.API_UPDATE_PORTRAIT)
    Observable<ApiWrapper<String>> updatePortrait(
            @Field("token") String token,
            @Field("portrait") String portrait
    );
}
