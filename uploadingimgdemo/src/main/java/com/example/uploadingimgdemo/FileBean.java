package com.example.uploadingimgdemo;

import android.text.TextUtils;

/**
 * author:Created by Conan on 2018/1/12.
 */

public class FileBean {

    /**
     * msg : 文件上传成功
     * code : 0
     */

    private String msg;
    private String code;

    public String getMsg() {
        if (TextUtils.isEmpty(msg))
            return "数据打印";
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
