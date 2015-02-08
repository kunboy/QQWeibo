package com.kunboy.qweibo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.kunboy.qweibo.R;
import com.kunboy.qweibo.constant.QWeiboTencent;
import com.kunboy.qweibo.debug.DebugLog;
import com.kunboy.qweibo.listener.BaseUiListener;
import com.kunboy.qweibo.util.SharedPreferencesFactory;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 欢迎页面
 *
 * @author sunhongkun
 */
public class WelcomeActivity extends Activity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String openID = sp.getString(Constants.PARAM_OPEN_ID, "");
        String accessToken = sp.getString(Constants.PARAM_ACCESS_TOKEN, "");
        String expires = sp.getString(Constants.PARAM_EXPIRES_IN, "");
        if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(expires)) {
            QWeiboTencent.getTencent().setOpenId(openID);
            QWeiboTencent.getTencent().setAccessToken(accessToken, expires);
        }
        login();
    }

    /**
     * 授权验证
     */
    protected void login() {
        if (!QWeiboTencent.getTencent().isSessionValid()) {
            QWeiboTencent.getTencent().login(this, "all", new BaseUiListener() {

                @Override
                public void onCancel() {
                    super.onCancel();
                    Toast.makeText(WelcomeActivity.this, R.string.login_cancel,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete(Object arg0) {
                    super.onComplete(arg0);
                    DebugLog.d(TAG, "login_onComplete", arg0.toString());
                    Toast.makeText(WelcomeActivity.this, R.string.login_success,
                            Toast.LENGTH_SHORT).show();
                    initOpenidAndToken((JSONObject) arg0);
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(UiError arg0) {
                    super.onError(arg0);
                    Toast.makeText(WelcomeActivity.this, R.string.login_error,
                            Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            Toast.makeText(WelcomeActivity.this, R.string.login_already, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                QWeiboTencent.getTencent().setAccessToken(token, expires);
                QWeiboTencent.getTencent().setOpenId(openId);
                // 备份相应信息，便于下次启动获取相关信息
                SharedPreferencesFactory.putStringValue( Constants.PARAM_ACCESS_TOKEN, token);
                SharedPreferencesFactory.putStringValue( Constants.PARAM_EXPIRES_IN, expires);
                SharedPreferencesFactory.putStringValue( Constants.PARAM_OPEN_ID, openId);
            }
        } catch (JSONException e) {
            DebugLog.e(TAG, "initOpenidAndToken", e.getMessage());
        }
    }
}
