package com.acg12.net.api;

import com.acg12.entity.User;

import com.acg12.entity.User;
import com.acg12.lib.net.factory.ApiConverter;
import com.acg12.net.converter.UserConverter;

import com.acg12.net.converter.UserConverter;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

public interface UserApi {

    @FormUrlEncoded
    @POST("api/app/common/login.json")
    @ApiConverter(converter = UserConverter.class)
    Observable<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    @ApiConverter(converter = UserConverter.class)
    Observable<User> register(@Field("username") String username, @Field("password") String password, @Field("verify") String verify);

    @FormUrlEncoded
    @POST("api/verify")
    Observable<ResponseBody> verify(@Field("username") String username, @Field("type") String type);

    @FormUrlEncoded
    @POST("api/restPwd")
    Observable<ResponseBody> restPwd(@Field("username") String username, @Field("password") String password, @Field("verify") String verify);

    @POST("api/userInfo")
    Observable<ResponseBody> userInfo();

    @Multipart
    @POST("api/alteruser")
    Observable<ResponseBody> uploadAvatar(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("api/alteruser")
    Observable<ResponseBody> userAlter(@Field("alterType") String type, @Field("param1") String param1, @Field("param2") String param2);

    @FormUrlEncoded
    @POST("api/feedback")
    Observable<ResponseBody> feedback(@Field("message") String message);

    @FormUrlEncoded
    @POST("api/update")
    Observable<ResponseBody> updateApp(@Field("versionCode") String versionCode);
}
