package com.example.ijuin.demoproject;

/**
 * Created by ijuin on 11/21/2017.
 */

public class MessageModel {
    private String _senderId;
    private String _senderName;
    private String _content;
    private String _time;

    public String get_senderId() {
        return _senderId;
    }

    public String get_senderName() {
        return _senderName;
    }

    public String get_content() {
        return _content;
    }

    public String get_time() {
        return _time;
    }

    public void set_senderId(String senderId) {
        this._senderId = senderId;
    }

    public void set_senderName(String senderName) {
        this._senderName = senderName;
    }

    public void set_content(String content) {
        this._content = content;
    }

    public void set_time(String time) {
        this._time = time;
    }
}
