package com.kunboy.qweibo.model;

/**
 * Created by sunhongkun on 2015/1/30.
 */
public class WeiboUserInfo {

    //    birth_day	出生天
    //    birth_month	出生月
    //    birth_year	出生年
    private String birthDay, birthMonth, birthYear;
    //    fansnum	听众数
    private int fansNum;
    //    favnum	收藏数
    private int fanNum;
    //    head	头像url
    private String head;
    //    homepage	个人主页
    private String homePage;
    //    idolnum	收听的人数
    private int idolNum;
    //    introduction	个人介绍
    private String introduction;
    //    location	所在地
    private String location;
    //    name	用户帐户名
    private String name;
    //    nick	用户昵称
    private String nick;
    //    openid	发表微博的用户的唯一ID，与name相对应。
    private String openid;
    //    sex	用户性别，1-男，2-女，0-未填写
    private String sex;
    //    tweetnum	发表的微博数
    private int tweetNum;
    //    level	微博等级
    private int level;

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getFanNum() {
        return fanNum;
    }

    public void setFanNum(int fanNum) {
        this.fanNum = fanNum;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public int getIdolNum() {
        return idolNum;
    }

    public void setIdolNum(int idolNum) {
        this.idolNum = idolNum;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTweetNum() {
        return tweetNum;
    }

    public void setTweetNum(int tweetNum) {
        this.tweetNum = tweetNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "WeiboUserInfo{" +
                "birthDay='" + birthDay + '\'' +
                ", birthMonth='" + birthMonth + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", fansNum=" + fansNum +
                ", fanNum=" + fanNum +
                ", head='" + head + '\'' +
                ", homePage='" + homePage + '\'' +
                ", idolNum=" + idolNum +
                ", introduction='" + introduction + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", openid='" + openid + '\'' +
                ", sex='" + sex + '\'' +
                ", tweetNum=" + tweetNum +
                ", level=" + level +
                '}';
    }
}
