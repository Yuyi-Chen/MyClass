package com.cpw.myclass.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NoticeBean implements Serializable {
    @SerializedName("notice_author")
    String author;
    @SerializedName("notice_class")
    String class_id;
    @SerializedName("notice_content")
    String content;
    @SerializedName("notice_create_time")
    String create_time;
    @SerializedName("notice_id")
    String notice_id;
    @SerializedName("notice_read")
    String notice_read;
    @SerializedName("notice_title")
    String title;
    @SerializedName("notice_type")
    int notice_type;

    public int getNotice_type() {
        return notice_type;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getNotice_id() {
        return notice_id;
    }

    public String getNotice_read() {
        return notice_read;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public void setNotice_read(String notice_read) {
        this.notice_read = notice_read;
    }

    public void setNotice_type(int notice_type) {
        this.notice_type = notice_type;
    }
}
