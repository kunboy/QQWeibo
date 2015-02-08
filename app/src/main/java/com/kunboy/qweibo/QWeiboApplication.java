package com.kunboy.qweibo;

import android.app.Application;

import com.kunboy.qweibo.config.ProjectConfig;
import com.kunboy.qweibo.constant.GlobalConstant;
import com.kunboy.qweibo.constant.QWeiboTencent;
import com.kunboy.qweibo.debug.DebugLog;

public class QWeiboApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
        GlobalConstant.sApplicationContext = this;
		//设置debug开关
		DebugLog.setIsDebug(ProjectConfig.isDebug);
		//初始化Tencent实例
		QWeiboTencent.init(this);
		
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
}
