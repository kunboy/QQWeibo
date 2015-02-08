package com.kunboy.qweibo.listener;

import com.kunboy.qweibo.debug.DebugLog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * 所有IUlistener的父类
 * 
 * @author sunhongkun
 * 
 */
public class BaseUiListener implements IUiListener {

	private final String TAG = getClass().getSimpleName();

	@Override
	public void onCancel() {

	}

	@Override
	public void onComplete(Object arg0) {

	}

	@Override
	public void onError(UiError arg0) {
		DebugLog.e(TAG, "onError", arg0.errorMessage);
	}

}
