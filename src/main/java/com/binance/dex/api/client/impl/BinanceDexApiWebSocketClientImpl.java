package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.BinanceDexApiWebSocketClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.WebSocketApiCallback;
import com.binance.dex.api.client.domain.ws.SocketEntity;
import com.google.common.collect.Maps;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class BinanceDexApiWebSocketClientImpl implements BinanceDexApiWebSocketClient {

    private OkHttpClient client;
    private Map<WebSocket, SocketEntity> sockets;

    //private String webSocketUrl = "wss://dex.binance.org/api";
    private String webSocketUrl = "wss://testnet-dex.binance.org/api";

    public BinanceDexApiWebSocketClientImpl() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        sockets = Maps.newHashMap();
    }

    public BinanceDexApiWebSocketClientImpl(String url) {
        this();
        this.webSocketUrl = url;
    }

    @Override
    public void onDepthEvent(String symbol, WebSocketApiCallback callback) {
        final String channel = String.format("ws/%s@marketDepth", symbol);
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onDepthEvent(List<String> symbols, WebSocketApiCallback callback) {
        StringJoiner sj = new StringJoiner("/");
        for (String symbol : symbols) {
            String channel = String.format("%s@marketDepth", symbol);
            sj.add(channel);
        }
        String combinedChannel = "stream?streams=" + sj.toString();
        createNewWebSocket(combinedChannel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onTradeEvent(String symbol, WebSocketApiCallback callback) {
        final String channel = String.format("ws/%s@trades", symbol);
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onTradeEvent(List<String> symbols, WebSocketApiCallback callback) {
        StringJoiner sj = new StringJoiner("/");
        for (String symbol : symbols) {
            String channel = String.format("%s@trades", symbol);
            sj.add(channel);
        }
        String combinedChannel = "stream?streams=" + sj.toString();
        createNewWebSocket(combinedChannel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onUserEvent(String address, WebSocketApiCallback callback) {
        final String channel = String.format("ws/%s", address);
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    private synchronized void createNewWebSocket(String channel, WebSocketListener listener) {
        String streamingUrl = String.format("%s/%s", webSocketUrl, channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        WebSocket ws = client.newWebSocket(request, listener);
        sockets.put(ws, new SocketEntity(request, listener));
    }
}
