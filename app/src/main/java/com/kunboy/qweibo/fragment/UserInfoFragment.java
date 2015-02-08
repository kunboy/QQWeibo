package com.kunboy.qweibo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kunboy.qweibo.R;
import com.kunboy.qweibo.constant.GlobalConstant;
import com.kunboy.qweibo.debug.DebugLog;
import com.kunboy.qweibo.http.AbstractOkHttpCallBack;
import com.kunboy.qweibo.http.UserGetInfoImpl;
import com.kunboy.qweibo.model.WeiboUserInfo;
import com.kunboy.qweibo.util.ToastTool;
import com.qq.open.OpensnsException;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserInfoFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private OkHttpClient mOkHttpClient;
    private TextView mTextInfoLocation;
    private TextView mTextInfoLevel;
    private TextView mTextInfoNick;

    public UserInfoFragment() {

    }

    public static UserInfoFragment getNewInstance() {
        UserInfoFragment instance = new UserInfoFragment();
        Bundle args = new Bundle();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_info,
                container, false);
        initViews(rootView);
        return rootView;

    }

    private void initViews(View rootView){
        mTextInfoNick = (TextView) rootView.findViewById(R.id.info_nick);
        mTextInfoLevel = (TextView) rootView.findViewById(R.id.info_level);
        mTextInfoLocation = (TextView) rootView.findViewById(R.id.info_location);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 获取用户的微博信息
     */
    private void getUserInfo() {

        try {
            Request request = new UserGetInfoImpl("").buildRequest();
            mOkHttpClient.cancel(request.tag());

            mOkHttpClient.newCall(request).enqueue(new UserGetInfoCallBack());
        } catch (OpensnsException e) {
            e.printStackTrace();
        }

    }

    private void updateUserInfoUI(WeiboUserInfo user) {

        mTextInfoLocation.setText(user.getLocation());
        mTextInfoLevel.setText("Lv "+user.getLevel());
        mTextInfoNick.setText(user.getNick());

    }

    private void onUpdateUserInfoFailure() {
        ToastTool.showToast(GlobalConstant.sApplicationContext,R.string.info_get_failure, Toast.LENGTH_SHORT);
    }

    class UserGetInfoCallBack extends AbstractOkHttpCallBack {
        @Override
        public Object parse(Response response) {
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                DebugLog.i(TAG, "parse", responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            WeiboUserInfo user = new WeiboUserInfo();
            try {
                String jsonString = response.body().string();
                DebugLog.i(TAG, "parse", jsonString);
                JSONObject object = new JSONObject(jsonString).getJSONObject("data");
                user.setBirthDay(object.getString("birth_day"));
                user.setBirthMonth(object.getString("birth_month"));
                user.setBirthYear(object.getString("birth_year"));
                user.setFansNum(object.getInt("favnum"));
                user.setHead(object.getString("head"));
                user.setHomePage(object.getString("homepage"));
                user.setIdolNum(object.getInt("idolnum"));
                user.setIntroduction(object.getString("introduction"));
                user.setLocation(object.getString("location"));
                user.setName(object.getString("name"));
                user.setNick(object.getString("nick"));
                user.setOpenid(object.getString("openid"));
                user.setTweetNum(object.getInt("tweetnum"));
                user.setLevel(object.getInt("level"));

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return user;
        }

        @Override
        public void doFailure(Request request, IOException ioexception) {
            onUpdateUserInfoFailure();
        }

        @Override
        public void doResponse(Object object) {
            updateUserInfoUI((WeiboUserInfo) object);
        }
    }


}
