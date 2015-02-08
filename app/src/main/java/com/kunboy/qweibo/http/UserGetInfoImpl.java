package com.kunboy.qweibo.http;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * 获取用户信息接口
 * 获取自己的信息 name传null或者""
 * 获取他人的信息 name传他人的name
 * Created by sunhongkun on 2015/1/29.
 */
public class UserGetInfoImpl extends AbstrackOpenAPIRequest {

    private String mName ;
    private HashMap<String, String> additionParams;

    /**
     * @param name 获取自己的信息 name传null或者"";获取他人的信息 name传他人的name
     */
    public UserGetInfoImpl(String name) {
        this.mName = name;
        additionParams = new HashMap<String, String>();
    }

    @Override
    String getScriptName() {
        return "/user/get_other_info";
    }

    @Override
    String getMethod() {
        return "post";
    }

    @Override
    HashMap<String, String> getSpecialParams() {
        if (!TextUtils.isEmpty(mName)){
            additionParams.put("name",mName);
        }
        return additionParams;
    }
}
