package com.example.event;

/**
 * 功能描述：
 * Created by AsiaLYF on 2018/4/18.
 */

public class MainMessageEvent {
    private String message;

    public MainMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
