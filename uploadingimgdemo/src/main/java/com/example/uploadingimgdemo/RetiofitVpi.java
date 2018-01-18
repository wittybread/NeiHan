package com.example.uploadingimgdemo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * author:Created by Conan on 2018/1/11.
 */



public interface RetiofitVpi {
    //查找用户信息
    @GET("user/getUserInfo")
    Observable<UserBean> userBean(@Query("uid") String uid);

    //上传文件
    @Multipart
    @POST("file/upload")
    Observable<FileBean> uploadFile(@Query("uid") String uid,
                                    @Part("file\"; filename=\"avatar.jpg") RequestBody file);
}
