package com.cpw.myclass.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserBean implements Serializable {
    @SerializedName("user_class")
    String class_id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("user_name")
    String user_name;
    @SerializedName("user_phone")
    String user_phone;
    @SerializedName("user_pic")
    String user_pic;
    @SerializedName("user_role")
    int user_role;
    public char firstLetter;

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public int getUser_role() {
        return user_role;
    }

    public String getClass_id() {
        return class_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }
}
