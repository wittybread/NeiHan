package com.example.rxdemo;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by jim on 2018/1/18.
 */

public interface ApiService {
    @GET("product/getCatagory")
    Flowable<Bean> get();
}
