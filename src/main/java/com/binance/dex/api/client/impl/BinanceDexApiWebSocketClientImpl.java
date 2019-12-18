package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.BinanceDexApiWebSocketClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.WebSocketApiCallback;
import com.binance.dex.api.client.domain.ws.AccountUpdateEvent;
import com.binance.dex.api.client.domain.ws.OrdersUpdateEvent;
import com.binance.dex.api.client.domain.ws.SocketEntity;
import com.google.common.collect.Maps;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class BinanceDexApiWebSocketClientImpl implements BinanceDexApiWebSocketClient {

    private OkHttpClient client;
    private Map<WebSocket, SocketEntity> sockets;
    private String apiKey;

    private String webSocketUrl = BinanceDexEnvironment.PROD.getStreamUrl();

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

    public BinanceDexApiWebSocketClientImpl(String url,String apiKey) {
        this();
        this.apiKey = apiKey;
        this.webSocketUrl = url;
    }

    @Override
    public void onDepthEvent(String symbol, WebSocketApiCallback callback) {
        final String channel = String.format("ws/%s@marketDepth", symbol);
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onDepthEvent(List<String> symbols, WebSocketApiCallback callback) {
        StringJoiner sj = new StringJoiner("/", "stream?streams=", "");
        for (String symbol : symbols) {
            String channel = String.format("%s@marketDepth", symbol);
            sj.add(channel);
        }
        createNewWebSocket(sj.toString(), new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onTradeEvent(String symbol, WebSocketApiCallback callback) {
        final String channel = String.format("ws/%s@trades", symbol);
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onTradeEvent(List<String> symbols, WebSocketApiCallback callback) {
        StringJoiner sj = new StringJoiner("/", "stream?streams=", "");
        for (String symbol : symbols) {
            String channel = String.format("%s@trades", symbol);
            sj.add(channel);
        }
        createNewWebSocket(sj.toString(), new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onUserEvent(String address, WebSocketApiCallback callback) {
        String channel;
        if(StringUtils.isNotBlank(this.apiKey)){
            channel = String.format("ws/%s?apikey=%s",address,apiKey);
        } else {
            channel = String.format("ws/%s", address);
        }
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, callback));
    }

    @Override
    public void onAccountUpdateEvent(String address, WebSocketApiCallback<AccountUpdateEvent> callback) {
        String channel;
        if(StringUtils.isNotBlank(this.apiKey)){
            channel = String.format("ws/%s?apikey=%s",address,apiKey);
        } else {
            channel = String.format("ws/%s", address);
        }
        WebSocketApiCallback<AccountUpdateEvent> onAccountCallBack = response -> {
            if (response.getStream().equals("accounts")){
                callback.onResponse(response);
            }
        };
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, AccountUpdateEvent.class, onAccountCallBack));
    }

    @Override
     public void onOrderUpdateEvent(String address, WebSocketApiCallback<OrdersUpdateEvent> callback) {
        String channel;
        if(StringUtils.isNotBlank(this.apiKey)){
            channel = String.format("ws/%s?apikey=%s",address,apiKey);
        } else {
            channel = String.format("ws/%s", address);
        }
        WebSocketApiCallback<OrdersUpdateEvent> onOrderCallBack = response -> {
            if (response.getStream().equals("orders")){
                callback.onResponse(response);
            }
        };
        createNewWebSocket(channel, new BinanceDexApiWebSocketListener(client, sockets, OrdersUpdateEvent.class, onOrderCallBack));
    }

    private synchronized void createNewWebSocket(String channel, WebSocketListener listener) {
        String streamingUrl = String.format("%s/%s", webSocketUrl, channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        WebSocket ws = client.newWebSocket(request, listener);
        sockets.put(ws, new SocketEntity(request, listener));
    }
}
