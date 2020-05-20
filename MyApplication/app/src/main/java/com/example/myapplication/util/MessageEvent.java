package com.example.myapplication.util;

public class MessageEvent {
    private String type;
    private String contect;

    public MessageEvent(String type, String contect) {
        this.type = type;
        this.contect = contect;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContect() {
        return contect;
    }

    public void setContect(String contect) {
        this.contect = contect;
    }
}
