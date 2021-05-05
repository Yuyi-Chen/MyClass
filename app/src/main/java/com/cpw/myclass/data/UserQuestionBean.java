package com.cpw.myclass.data;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class UserQuestionBean {
    @SerializedName("code")
    String code;
    @SerializedName("msg")
    String message;
    @SerializedName("user_question")
    String question;

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
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
