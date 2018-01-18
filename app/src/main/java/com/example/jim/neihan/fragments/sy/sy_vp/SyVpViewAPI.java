package com.example.jim.neihan.fragments.sy.sy_vp;

import com.example.jim.neihan.fragments.sy.sybean.SyChildBean;
import com.example.jim.neihan.fragments.sy.sybean.SyTabData;

/**
 * Created by jim on 2018/1/10.
 */

public interface SyVpViewAPI {

    void onSuccess(SyChildBean syTabData);

    void onFailed(String err);

}
