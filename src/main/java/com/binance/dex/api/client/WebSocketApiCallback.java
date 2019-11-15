package com.binance.dex.api.client;

public interface WebSocketApiCallback<T> {

    void onResponse(T response) throws Exception;

}
