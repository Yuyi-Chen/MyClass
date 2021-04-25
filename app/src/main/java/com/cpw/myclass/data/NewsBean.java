package com.cpw.myclass.data;

public class NewsBean {
    int type;
    String message;
    String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
