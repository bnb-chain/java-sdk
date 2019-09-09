package com.binance.dex.api.client.domain.ws;

import com.google.common.collect.Lists;
import okhttp3.Request;
import okhttp3.WebSocketListener;

import java.util.List;

public class SocketEntity {

    Request request;

    WebSocketListener listener;

    List<String> message;

    Long lastUpdateTime = System.currentTimeMillis();

    public SocketEntity(Request request, WebSocketListener listener) {
        this.request = request;
        this.listener = listener;
        this.message = Lists.newArrayList();
    }

    public void addMessage(String msg) {
        message.add(msg);
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public Request getRequest() {
        return request;
    }

    public WebSocketListener getListener() {
        return listener;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setLastUpdateTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

}
