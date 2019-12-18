package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.ws.AccountUpdateEvent;
import com.binance.dex.api.client.domain.ws.OrdersUpdateEvent;

import java.util.List;

public interface BinanceDexApiWebSocketClient {

    void onDepthEvent(String symbol, WebSocketApiCallback callback);

    void onDepthEvent(List<String> symbols, WebSocketApiCallback callback);

    void onTradeEvent(String symbol, WebSocketApiCallback callback);

    void onTradeEvent(List<String> symbols, WebSocketApiCallback callback);

    void onUserEvent(String address, WebSocketApiCallback callback);

    void onAccountUpdateEvent(String address, WebSocketApiCallback<AccountUpdateEvent> callback);

    void onOrderUpdateEvent(String address, WebSocketApiCallback<OrdersUpdateEvent> callback);
}