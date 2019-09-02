package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.WebSocketApiCallback;
import com.binance.dex.api.client.domain.ws.SocketEntity;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.Map;

@Slf4j
public class BinanceDexApiWebSocketListener extends WebSocketListener {

    protected WebSocketApiCallback callback;
    protected OkHttpClient client;
    protected Map<WebSocket, SocketEntity> sockets;

    public BinanceDexApiWebSocketListener(OkHttpClient client, Map<WebSocket, SocketEntity> sockets, WebSocketApiCallback callback) {
        this.callback = callback;
        this.client = client;
        this.sockets = sockets;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            callback.onResponse(text);
            sockets.get(webSocket).setLastUpdateTime();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        log.error("API failed {}", webSocket.request().url());
        if (response != null) {
            ResponseBody body = response.body();
            if (body != null) {
                body.close();
            }
        }
        reconnect(webSocket);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.error("API closing {}", webSocket.request().url());
        //reconnect(webSocket);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.error("API closed {}", webSocket.request().url());
        //reconnect(webSocket);
    }

    private synchronized void reconnect(WebSocket webSocket) {
        log.warn("API reconnect {}", webSocket.request().url());
        SocketEntity entity = sockets.get(webSocket);
        if (entity != null) {
            WebSocket ws = client.newWebSocket(entity.getRequest(), entity.getListener());
            entity.setLastUpdateTime();
            for (String msg : entity.getMessage()) {
                log.warn("API resend message {}", msg);
                ws.send(msg);
            }
            sockets.remove(webSocket);
            sockets.put(ws, entity);
        }
        webSocket.close(1000, "close");
    }
}
