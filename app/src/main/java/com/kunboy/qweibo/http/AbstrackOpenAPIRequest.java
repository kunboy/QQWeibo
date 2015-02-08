package com.kunboy.qweibo.http;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kunboy.qweibo.constant.QWeiboTencent;
import com.kunboy.qweibo.constant.TencentConstant;
import com.kunboy.qweibo.debug.DebugLog;
import com.kunboy.qweibo.util.SharedPreferencesFactory;
import com.qq.open.ErrorCode;
import com.qq.open.OpensnsException;
import com.qq.open.SnsSigCheck;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.spdy.HeadersMode;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sunhongkun on 2015/1/29.
 */
public abstract class AbstrackOpenAPIRequest {


    private Request mRequest;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * 返回OpenApi Cgi名字
     * 例子："/user/get_other_info" 微博用户信息
     *
     * @return 指定OpenApi Cgi名字
     */
    abstract String getScriptName();

    private String getUrlString() throws OpensnsException {
        // 指定OpenApi Cgi名字
        String scriptName = getScriptName();
        // 指定HTTP请求协议类型
        String protocol = "http";
        StringBuilder sb = new StringBuilder(64);
        sb.append(protocol).append("://").append(TencentConstant.SERVERNAME).append(scriptName);
        String url = sb.toString();
        return url;
    }

    private HashMap<String, String> getBaseParams() throws OpensnsException {

        // 填充URL请求参数
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("openid", QWeiboTencent.getTencent().getOpenId());
        params.put("openkey", QWeiboTencent.getTencent().getAccessToken());
        params.put("pf", TencentConstant.PF);
        // 添加固定参数
        params.put("appid", TencentConstant.APP_ID);

        return params;
    }


    /**
     * 请求方法，"get" 或者 "post"
     *
     * @return
     */
    abstract String getMethod();

    /**
     * 验证openid是否合法
     */
    private boolean isOpenid(String openid) {
        return (openid.length() == 32) && openid.matches("^[0-9A-Fa-f]+$");
    }

    /**
     * 辅助函数，打印出完整的请求串内容
     *
     * @param url    请求cgi的url
     * @param method 请求的方式 get/post
     * @param params OpenApi的参数列表
     */
    private void printRequest(String url, String method, HashMap<String, String> params) throws OpensnsException {
        if (DebugLog.isDebug()) {
            System.out.println("==========Request Info==========\n");
            System.out.println("method:  " + method);
            System.out.println("url:  " + url);
            System.out.println("params:");
            System.out.println(params);
            System.out.println("querystring:");
            StringBuilder buffer = new StringBuilder(128);
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                try {
                    buffer.append(URLEncoder.encode((String) entry.getKey(), "UTF-8").replace("+", "%20").replace("*", "%2A")).append("=").append(URLEncoder.encode((String) entry.getValue(), "UTF-8").replace("+", "%20").replace("*", "%2A")).append("&");
                } catch (UnsupportedEncodingException e) {
                    throw new OpensnsException(ErrorCode.MAKE_SIGNATURE_ERROR, e);
                }
            }
            String tmp = buffer.toString();
            tmp = tmp.substring(0, tmp.length() - 1);
            System.out.println(tmp);
            System.out.println();
        }
    }

    abstract HashMap<String, String> getSpecialParams();

    /**
     * 生成步骤：
     *  1.获取基本参数
     *  2.获取不同OpenApi特殊参数
     *  3.通过所有参数获取签名秘钥并加入到参数列表中
     *  4.将参数添加到post或者get请求参数中
     * @return Request
     * @throws OpensnsException
     */
    public Request buildRequest() throws OpensnsException {
        HashMap<String, String> params = getBaseParams();
        params.putAll(getSpecialParams());
        // 签名密钥
        String secret = TencentConstant.APP_KEY + "&";
        String sig = SnsSigCheck.makeSig(getMethod(), getScriptName(), params, secret);
        params.put("sig", sig);

        printRequest(getUrlString(),getMethod(),params);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Iterator it = params.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry e = (Map.Entry) it.next();
            builder.add((String)e.getKey(),(String)e.getValue());
        }
        RequestBody body = builder.build();
        try {
            //这里设置了Tag（getScriptName），便于后面的call操作的key
            mRequest = new Request.Builder().url(getUrlString()).header("User-Agent", "Java OpenApiV3 SDK Client").post(body).tag(getScriptName()).
                    build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mRequest;
    }

    /**
     * 把数据源HashMap转换成json
     *
     * @param map
     */
    private static String hashMapToJsonString(HashMap map) {
        StringBuffer sb = new StringBuffer("{");
        for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry e = (Map.Entry) it.next();
            sb.append("'").append(e.getKey()).append("':").append("'").append(e.getValue()).append("',");
        }
        String jsonString = sb.substring(0, sb.lastIndexOf(","));
        return jsonString + "}";
    }

}
