package com.example.jim.neihan.fragments.sy;

import com.example.jim.neihan.fragments.sy.bean.FeedData;
import com.example.jim.neihan.fragments.sy.bean.GroupData;
import com.example.jim.neihan.fragments.sy.bean.HttpResult;
import com.example.jim.neihan.fragments.sy.sybean.SyTabData;
import com.example.jim.neihan.fragments.sy.sybean.SyChildBean;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by jim on 2018/1/10.
 */

public interface SyAPI {
    //http://lf.snssdk.com/neihan/service/tabs/
    @GET("neihan/service/tabs/")
    Call<SyTabData> getTab();

    @GET("neihan/service/tabs/")
    Call<ResponseBody> getTab2();

    //http://lf.snssdk.com/neihan/stream/mix/v1/?content_type=-101
    @GET("neihan/stream/mix/v1/")
    Call<SyChildBean> getChild(@Query("content_type") String type);

    @GET
    Call<SyChildBean> getChild2(@Url String url);

    //http://lf.snssdk.com/neihan/stream/mix/v1/?content_type=-101
    @GET("neihan/stream/mix/v1/")
    Call<HttpResult<FeedData<GroupData>>> getChild_101(@Query("content_type") String type);

    @GET
    Flowable<SyChildBean> getChile3(@Url String url);

}
