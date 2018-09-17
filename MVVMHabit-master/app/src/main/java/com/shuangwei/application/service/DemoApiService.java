package com.shuangwei.application.service;

import com.shuangwei.application.entity.DemoEntity;
import com.shuangwei.application.entity.UserEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface DemoApiService {
    //https://www.oschina.net/
    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<DemoEntity>> demoGet();
    @FormUrlEncoded
    @POST("staff_api/login/CheckAccount")
    Observable<BaseResponse<UserEntity>> demoPost(@Field("account") String account);
    @FormUrlEncoded
    //@POST(Constants.URL_REGISTER)
    @POST("staff_api/login/Login")
    Observable<BaseResponse<UserEntity>> loginPost(@Field("account") String account, @Field("password") String encryptpassword);
}