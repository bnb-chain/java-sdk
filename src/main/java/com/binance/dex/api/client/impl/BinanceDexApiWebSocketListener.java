package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.WebSocketApiCallback;
import com.binance.dex.api.client.domain.ws.SocketEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.Map;

@Slf4j
public class BinanceDexApiWebSocketListener<T> extends WebSocketListener {

    private WebSocketApiCallback callback;
    private String Topic = null;
    private OkHttpClient client;
    private volatile Map<WebSocket, SocketEntity> sockets;
    private static final ObjectMapper mapper = new ObjectMapper();
    private ObjectReader objectReader = null;

    private volatile long retryInterval = 3000;

    public BinanceDexApiWebSocketListener(OkHttpClient client, Map<WebSocket, SocketEntity> sockets, WebSocketApiCallback callback){
        this.callback = callback;
        this.client = client;
        this.sockets = sockets;
    }

    public BinanceDexApiWebSocketListener(OkHttpClient client, Map<WebSocket, SocketEntity> sockets, Class<T> eventClass, WebSocketApiCallback callback) {
        this(client, sockets,callback);
        this.objectReader = mapper.readerFor(eventClass);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            if (this.objectReader!=null){
                T event = this.objectReader.readValue(text);
                callback.onResponse(event);
            }else{
                callback.onResponse(text);
            }
            sockets.get(webSocket).setLastUpdateTime();
        } catch (Exception e) {
            //no need to handle exception, since may receive something unexpected
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        log.error("API failed {}", webSocket.request().url(),t);
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
        try {
            Thread.sleep(nextInterval());
            log.warn("API reconnect {}", webSocket.request().url());
            SocketEntity entity = sockets.get(webSocket);
            if (entity != null) {
                if (System.currentTimeMillis() - entity.getLastUpdateTime() < 1000) {
                    retryInterval = 3000;
                }
                WebSocket ws = client.newWebSocket(entity.getRequest(), entity.getListener());
                entity.setLastUpdateTime();
                for (String msg : entity.getMessage()) {
                    log.warn("API resend message {}", msg);
                    ws.send(msg);
                }
                sockets.remove(webSocket);
                sockets.put(ws, entity);
            }
        } catch (Exception e) {
            log.error("API reconnect error ", e);
        } finally {
            webSocket.close(1000, "close");
        }
    }

    private synchronized long nextInterval() {
        if (retryInterval < 30_000L) {
            retryInterval *= 2;
            return retryInterval;
        } else {
            retryInterval = 60_000L;
            return retryInterval;
        }
    }
}
