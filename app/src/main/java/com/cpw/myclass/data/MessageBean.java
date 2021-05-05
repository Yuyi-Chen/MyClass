package com.cpw.myclass.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageBean implements Serializable {
    @SerializedName("message_content")
    String message;
    @SerializedName("message_create_time")
    String time;
    @SerializedName("message_from")
    String from;
    @SerializedName("message_to")
    String to;
    @SerializedName("message_read")
    String read;
    @SerializedName("message_id")
    String id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public String getId() {
        return id;
    }

    public String getRead() {
        return read;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
