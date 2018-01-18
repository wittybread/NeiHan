package com.example.jim.neihan.fragments.sy.tab;

import com.example.jim.neihan.fragments.sy.SyAPI;
import com.example.jim.neihan.fragments.sy.sybean.SyChildBean;
import com.example.jim.neihan.fragments.sy.sybean.SyTabData;
import com.example.jim.neihan.utils.RetrofitUtils;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jim on 2018/1/10.
 */

public class SyTabModel {
    /**
     * "http://lf.snssdk.com/"
     *
     * @param baseUrl
     */
    public void getTabData(String baseUrl, final SyTabPresenterAPI syTabPresenterAPI) {
        Retrofit getretrofit = RetrofitUtils.getInstance(baseUrl).getretrofit();
        SyAPI syAPI = getretrofit.create(SyAPI.class);
        Call<SyTabData> tab2 = syAPI.getTab();
        tab2.enqueue(new Callback<SyTabData>() {

            @Override
            public void onResponse(Call<SyTabData> call, Response<SyTabData> response) {
                SyTabData message = response.body();
                syTabPresenterAPI.Success(message);
            }

            @Override
            public void onFailure(Call<SyTabData> call, Throwable t) {
                String s = t.toString();
                syTabPresenterAPI.Failed(s);

            }
        });
    }

    public void getData() {
        Retrofit getretrofit = RetrofitUtils.getInstance("").getretrofit();
        SyAPI syAPI = getretrofit.create(SyAPI.class);
        Flowable<SyChildBean> chile3 = syAPI.getChile3("");
        chile3.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<SyChildBean>() {
                    @Override
                    public void onNext(SyChildBean syChildBean) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
