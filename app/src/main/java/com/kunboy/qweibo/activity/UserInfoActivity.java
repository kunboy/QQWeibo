package com.kunboy.qweibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.kunboy.qweibo.R;
import com.kunboy.qweibo.constant.QWeiboTencent;
import com.kunboy.qweibo.debug.DebugLog;
import com.kunboy.qweibo.fragment.UserInfoFragment;

/**
 * 个人中心Activity
 * 
 * @author sunhongkun
 * 
 */
public class UserInfoActivity extends FragmentActivity {

	private final String TAG = getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_userinfo);
		DebugLog.d(TAG, "onCreate");
		if (getSupportFragmentManager().findFragmentByTag(UserInfoFragment.class.getName()) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, UserInfoFragment.getNewInstance(), UserInfoFragment.class.getName());
            ft.commit();
        }
	}

	@Override
	protected void onResume() {
		DebugLog.d(TAG, "onResume");
		super.onResume();
	}
	

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		DebugLog.d(TAG, "onActivityResult");
		QWeiboTencent.getTencent().onActivityResult(requestCode, resultCode, data);
	}

}
