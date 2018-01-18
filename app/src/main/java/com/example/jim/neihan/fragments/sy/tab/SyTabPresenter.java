package com.example.jim.neihan.fragments.sy.tab;

import android.content.Context;

import com.example.jim.neihan.fragments.sy.sybean.SyTabData;

/**
 * Created by jim on 2018/1/10.
 */

public class SyTabPresenter {

    private SyTabViewAPI syTabViewAPI;
    private Context context;
    private SyTabModel syTabModel;

    public SyTabPresenter(SyTabViewAPI syTabViewAPI, Context context) {
        this.syTabViewAPI = syTabViewAPI;
        this.context = context;
        syTabModel = new SyTabModel();
    }

    public void getTabData(String baseUrl) {
        syTabModel.getTabData(baseUrl, new SyTabPresenterAPI() {
            @Override
            public void Success(SyTabData syTabData) {
                syTabViewAPI.onSuccess(syTabData);
            }

            @Override
            public void Failed(String err) {
                syTabViewAPI.onFailed(err);
            }
        });
    }

}
