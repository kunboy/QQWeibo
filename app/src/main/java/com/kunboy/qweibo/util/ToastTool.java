package com.kunboy.qweibo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 全局公用一个Toast实例
 * 弹Toast之前，如果有一个Toast，那么先取消上一次的Toast
 * Created by sunhongkun on 2015/1/31.
 */
public class ToastTool {
    private static Toast mToast;

    public static void showToast(Context context,int messageID,int time){
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(context,messageID,time);
        mToast.show();
    }

}
