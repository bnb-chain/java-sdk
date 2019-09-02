package com.binance.dex.api.client;

public interface WebSocketApiCallback {

    void onResponse(String response) throws Exception;

}
