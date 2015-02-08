package com.kunboy.qweibo.constant;

import android.content.Context;

import com.tencent.tauth.Tencent;

/**
 * 全局的Tencent实例,使用之前需要确认已经调用过init方法
 * 
 * @author sunhongkun
 * 
 */
public class QWeiboTencent {

	private static Tencent mTencent;

	/**
	 * 在application初始化的时候调用
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		mTencent = Tencent.createInstance(TencentConstant.APP_ID, context);
	}

	public static Tencent getTencent() {
		return mTencent;
	}

}
