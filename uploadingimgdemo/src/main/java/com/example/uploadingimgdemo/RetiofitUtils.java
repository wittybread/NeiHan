package com.example.uploadingimgdemo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author:Created by Conan on 2018/1/11.
 */

public class RetiofitUtils {
    public static final String BASE_URL ="http://120.27.23.105/";
    private final Retrofit mRetrofit;

    public static class SINGLE_HOLDER{
        public static final RetiofitUtils INSTANCE = new RetiofitUtils(BASE_URL);
    }

    public static RetiofitUtils getInstance(){
        return SINGLE_HOLDER.INSTANCE;
    }

    private RetiofitUtils(String baseUrl){
        mRetrofit = buildRetrofit();
    }

    private OkHttpClient buildOkHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .addInterceptor(new Intercept())
                .addInterceptor(logging)
                .build();
    }

    private Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .client(buildOkHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T create(Class<T> tClass){
        return mRetrofit.create(tClass);
    }
}
