package com.cpw.myclass.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultNoticeBean {
    @SerializedName("code")
    String code;
    @SerializedName("msg")
    String message;
    @SerializedName("notice_data")
    ArrayList<NoticeBean> notice;

    public ArrayList<NoticeBean> getNotice() {
        return notice;
    }

    public void setNotice(ArrayList<NoticeBean> notice) {
        this.notice = notice;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
