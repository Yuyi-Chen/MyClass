package com.cpw.myclass.data;

import java.io.Serializable;

public class MessageFragmentBean implements Serializable {
    String from_name;
    String from_id;
    int from_role;
    String time;
    String message;
    String no_read;

    public String getFrom_id() {
        return from_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getFrom_role() {
        return from_role;
    }

    public String getNo_read() {
        return no_read;
    }

    public void setFrom_role(int from_role) {
        this.from_role = from_role;
    }

    public void setNo_read(String no_read) {
        this.no_read = no_read;
    }
}
