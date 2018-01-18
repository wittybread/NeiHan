package com.example.jim.neihan.fragments.sy.sy_vp;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.jim.neihan.fragments.sy.SyAPI;
import com.example.jim.neihan.fragments.sy.sybean.SyChildBean;
import com.example.jim.neihan.fragments.sy.sybean.SyTabData;
import com.example.jim.neihan.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jim on 2018/1/10.
 */

public class SyVpModel {
    /**
     * "http://lf.snssdk.com"
     *
     * @param baseUrl
     */
    public void getTabData(String baseUrl, String type, final SyVpPresenterAPI syVpPresenterAPI) {
        Retrofit getretrofit = RetrofitUtils.getInstance(baseUrl).getretrofit();
        SyAPI syAPI = getretrofit.create(SyAPI.class);

//        Call<SyChildBean> child2 = syAPI.getChild2(url);
//        child2.enqueue(new Callback<SyChildBean>() {
//            @Override
//            public void onResponse(Call<SyChildBean> call, Response<SyChildBean> response) {
//                String message = response.body().getMessage();
//                Log.d("WXW", "aaa" + message);
//                if (message.equals("success")) {
//                    syVpPresenterAPI.Success(response.body());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SyChildBean> call, Throwable t) {
//                syVpPresenterAPI.Failed(t.toString());
//            }
//        });
        Call<SyChildBean> child = syAPI.getChild(type);
        child.enqueue(new Callback<SyChildBean>() {
            @Override
            public void onResponse(Call<SyChildBean> call, Response<SyChildBean> response) {
                String message = response.body().getMessage();
                Log.d("WXW", "aaa" + message);
                if (message.equals("success")) {
                    syVpPresenterAPI.Success(response.body());
                }
            }

            @Override
            public void onFailure(Call<SyChildBean> call, Throwable t) {
                syVpPresenterAPI.Failed(t.toString());
            }
        });

    }
}
