package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;

import javax.websocket.Session;

public interface BinanceDexMessageHandler<T> {

    T send(Session userSession, String id, String message);

    void onMessage(JsonRpcResponse response);

}
