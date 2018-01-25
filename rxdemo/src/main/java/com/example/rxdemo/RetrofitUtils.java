package com.example.rxdemo;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jim on 2018/1/22.
 */

public class RetrofitUtils {

    private static Retrofit retrofit;
    private static ApiService apiService;

    /**
     * 初始化Retrofit
     *
     * @return
     */
    private static Retrofit getRetrofit() {

        if (retrofit == null) synchronized (RetrofitUtils.class) {
            if (retrofit == null) {
                //
//                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                //更改日志级别
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient okHttpClient = new OkHttpClient()
                        .newBuilder()
                        .addInterceptor(new MyInterceptor())
//                        .addNetworkInterceptor() //网络拦截器
                        .addInterceptor(loggingInterceptor)
                        .build();

                //创建Retrofit对象
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://120.27.23.105/")
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
        }


        return retrofit;
    }

    /**
     * 请求方法类
     *
     * @return
     */

    public static ApiService apiService() {

        if (apiService == null) {
            synchronized (ApiService.class) {
                if (apiService == null) {
                    apiService = getRetrofit().create(ApiService.class);

                }
            }
        }

        return apiService;
    }

    /**
     * 拦截器
     */

    static class MyInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            HttpUrl httpUrl = request
                    .url()
                    .newBuilder()
                    .addQueryParameter("source", "android")
                    .build();

            Request build = request
                    .newBuilder()
                    .url(httpUrl)
                    .build();

            return chain.proceed(build);
        }
    }

}
