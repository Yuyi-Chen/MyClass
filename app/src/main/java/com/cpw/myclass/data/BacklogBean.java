package com.cpw.myclass.data;

import java.io.Serializable;

public class BacklogBean implements Serializable {
    String title;
    String initiator;
    String initiator_type;
    String release_time;
    String content;

    public String getContent() {
        return content;
    }

    public String getInitiator() {
        return initiator;
    }

    public String getInitiator_type() {
        return initiator_type;
    }

    public String getRelease_time() {
        return release_time;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public void setInitiator_type(String initiator_type) {
        this.initiator_type = initiator_type;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
