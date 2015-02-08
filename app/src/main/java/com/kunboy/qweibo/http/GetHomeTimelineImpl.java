package com.kunboy.qweibo.http;

import java.util.HashMap;

/**
 * Created by sunhongkun on 2015/2/8.
 */
public class GetHomeTimelineImpl extends AbstrackOpenAPIRequest {

    /**
     * contenttype	必须	string	获取的微博的内容类型。
     * 0: 所有类型；
     * 1：带文本的微博；
     * 2：带链接的微博；
     * 4：带图片的微博；
     * 8：带视频的微博；
     * 0x10：带音频的微博。
     * 如果要获取只有文本的微博，建议contenttype的值使用0x80，不建议使用0x1。如果使用0x1，只要微博中带文本，将都会被获取到。
     */
    private String contentType;
    /**
     * reqnum	必须	string	每次请求记录的条数。取值为1-70条。
     */
    private int reqNum;
    /**
     * pageflag	必须	string	分页标识（0：第一页，1：向下翻页，2：向上翻页
     */
    private int pagetFlag;
    /**
     * pagetime	必须	string	本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
     */
    private String pageTime;

    /**
     * type	必须	string	获取微博的类型（需填写十进制数字）
     * 0：所有类型，0x1：原创发表，0x2：转播。
     * 如果需要拉取多种类型的微博，请使用“|”，如要拉取原创发表和转播，(0x1|0x2)得到3，此时type=3即可。
     */
    private String type;

    private HashMap<String, String> additionParams;

    /**
     *
     * @param contentType
     * 必须	string	获取的微博的内容类型。
     * 0: 所有类型；
     * 1：带文本的微博；
     * 2：带链接的微博；
     * 4：带图片的微博；
     * 8：带视频的微博；
     * 0x10：带音频的微博。
     * 如果要获取只有文本的微博，建议contenttype的值使用0x80，不建议使用0x1。如果使用0x1，只要微博中带文本，将都会被获取到。
     *
     * @param reqNum
     * 必须	string	每次请求记录的条数。取值为1-70条。
     *
     * @param pagetFlag
     * 必须	string	分页标识（0：第一页，1：向下翻页，2：向上翻页
     *
     * @param pageTime
     * 必须	string	本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
     *
     * @param type
     * 必须	string	获取微博的类型（需填写十进制数字）
     * 0：所有类型，0x1：原创发表，0x2：转播。
     * 如果需要拉取多种类型的微博，请使用“|”，如要拉取原创发表和转播，(0x1|0x2)得到3，此时type=3即可。
     */
    public GetHomeTimelineImpl(String contentType, int reqNum, int pagetFlag, String pageTime, String type) {
        this.contentType = contentType;
        this.reqNum = reqNum;
        this.pagetFlag = pagetFlag;
        this.pageTime = pageTime;
        this.type = type;
        additionParams = new HashMap<String,String>();
    }

    @Override
    String getScriptName() {
        return "/v3/status/get_home_timeline";
    }

    @Override
    String getMethod() {
        return "post";
    }

    @Override
    HashMap<String, String> getSpecialParams() {
        additionParams.put("contenttype",contentType);
        additionParams.put("reqnum",""+reqNum);
        additionParams.put("pageflag",pagetFlag+"");
        additionParams.put("pagetime",pageTime+"");
        additionParams.put("type",type);
        return additionParams;
    }
}
