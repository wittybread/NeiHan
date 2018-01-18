package com.example.jim.neihan.fragments.sy.sy_vp;

import android.content.Context;

import com.example.jim.neihan.fragments.sy.sybean.SyChildBean;
import com.example.jim.neihan.fragments.sy.sybean.SyTabData;

/**
 * Created by jim on 2018/1/10.
 */

public class SyVpPresenter {

    private SyVpViewAPI syVpViewAPI;
    private Context context;
    private SyVpModel syVpModel;

    public SyVpPresenter(SyVpViewAPI syTabViewAPI, Context context) {
        this.syVpViewAPI = syTabViewAPI;
        this.context = context;
        syVpModel = new SyVpModel();
    }

    public void getTabData(String baseUrl, String url) {
        syVpModel.getTabData(baseUrl, url, new SyVpPresenterAPI() {
            @Override
            public void Success(SyChildBean syChildBean) {
                syVpViewAPI.onSuccess(syChildBean);
            }

            @Override
            public void Failed(String err) {
                syVpViewAPI.onFailed(err);
            }
        });
    }

}
