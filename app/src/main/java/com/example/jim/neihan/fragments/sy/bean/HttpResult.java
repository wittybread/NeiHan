package com.example.jim.neihan.fragments.sy.bean;

/**
 * Created by liqy on 2018/1/9.
 */

public class HttpResult<T> {
    public String message;
    public FeedData<T> data;

    @Override
    public String toString() {
        return "HttpResult{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
