package com.example.jim.neihan.fragments.sy.tab;

import com.example.jim.neihan.fragments.sy.sybean.SyTabData;

/**
 * Created by jim on 2018/1/10.
 */

public interface SyTabViewAPI {

    void onSuccess(SyTabData syTabData);

    void onFailed(String err);

}
