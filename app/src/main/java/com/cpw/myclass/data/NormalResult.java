package com.cpw.myclass.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NormalResult {
    @SerializedName("code")
    String code;
    @SerializedName("msg")
    String message;

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

