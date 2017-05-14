package com.hsfcompany.tzcs.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER_INFO.
 */
public class UserInfo {

    private Long id;
    private String nickname;
    private String sex;
    private java.util.Date ctime;
    private Integer tanshizhi;
    private Integer yangxuzhi;
    private Integer yinxuzhi;
    private Integer qiyuzhi;
    private Integer shirezhi;
    private Integer qixuzhi;
    private Integer xueyuzhi;
    private Integer tebingzhi;

    public UserInfo() {
    }

    public UserInfo(Long id) {
        this.id = id;
    }

    public UserInfo(Long id, String nickname, String sex, java.util.Date ctime, Integer tanshizhi, Integer yangxuzhi, Integer yinxuzhi, Integer qiyuzhi, Integer shirezhi, Integer qixuzhi, Integer xueyuzhi, Integer tebingzhi) {
        this.id = id;
        this.nickname = nickname;
        this.sex = sex;
        this.ctime = ctime;
        this.tanshizhi = tanshizhi;
        this.yangxuzhi = yangxuzhi;
        this.yinxuzhi = yinxuzhi;
        this.qiyuzhi = qiyuzhi;
        this.shirezhi = shirezhi;
        this.qixuzhi = qixuzhi;
        this.xueyuzhi = xueyuzhi;
        this.tebingzhi = tebingzhi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public java.util.Date getCtime() {
        return ctime;
    }

    public void setCtime(java.util.Date ctime) {
        this.ctime = ctime;
    }

    public Integer getTanshizhi() {
        return tanshizhi;
    }

    public void setTanshizhi(Integer tanshizhi) {
        this.tanshizhi = tanshizhi;
    }

    public Integer getYangxuzhi() {
        return yangxuzhi;
    }

    public void setYangxuzhi(Integer yangxuzhi) {
        this.yangxuzhi = yangxuzhi;
    }

    public Integer getYinxuzhi() {
        return yinxuzhi;
    }

    public void setYinxuzhi(Integer yinxuzhi) {
        this.yinxuzhi = yinxuzhi;
    }

    public Integer getQiyuzhi() {
        return qiyuzhi;
    }

    public void setQiyuzhi(Integer qiyuzhi) {
        this.qiyuzhi = qiyuzhi;
    }

    public Integer getShirezhi() {
        return shirezhi;
    }

    public void setShirezhi(Integer shirezhi) {
        this.shirezhi = shirezhi;
    }

    public Integer getQixuzhi() {
        return qixuzhi;
    }

    public void setQixuzhi(Integer qixuzhi) {
        this.qixuzhi = qixuzhi;
    }

    public Integer getXueyuzhi() {
        return xueyuzhi;
    }

    public void setXueyuzhi(Integer xueyuzhi) {
        this.xueyuzhi = xueyuzhi;
    }

    public Integer getTebingzhi() {
        return tebingzhi;
    }

    public void setTebingzhi(Integer tebingzhi) {
        this.tebingzhi = tebingzhi;
    }

}
