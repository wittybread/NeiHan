package com.example.jim.neihan.fragments.sy.bean;

import java.util.List;

/**
 * Created by liqy on 2018/1/9.
 */

public class FeedData<T> {
    public boolean has_more;
    public String tip;
    public boolean has_new_message;
    public double max_time;
    public int min_time;
    public List<T> data;

    @Override
    public String toString() {
        return "FeedData{" +
                "has_more=" + has_more +
                ", tip='" + tip + '\'' +
                ", has_new_message=" + has_new_message +
                ", max_time=" + max_time +
                ", min_time=" + min_time +
                ", data=" + data +
                '}';
    }
}
