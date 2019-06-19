package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.websocket.BinanceDexClientEndpoint;
import com.binance.dex.api.client.websocket.BinanceDexClientWSFactory;
import com.binance.dex.api.client.websocket.BinanceDexMessageHandler;
import com.binance.dex.api.client.websocket.WebsocketLauncher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;
import java.util.concurrent.CountDownLatch;

public class WebsocketExample {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    BinanceDexClientEndpoint endpoint;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    @Before
    public void before(){
        endpoint = BinanceDexClientWSFactory.newClientEndpoint(new BinanceDexMessageHandler<String>() {
            @Override
            public String send(Session userSession, String id, String message) {
                userSession.getAsyncRemote().sendText(message);
                return null;
            }

            @Override
            public void onMessage(JsonRpcResponse response) {
                try {
                    System.out.println(objectMapper.writeValueAsString(response));
                    countDownLatch.countDown();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        },BinanceDexEnvironment.TEST_NET.getWsBaseUrl());
        WebsocketLauncher.startUp(endpoint);
    }

    @Test
    public void test(){
        endpoint.sendMessage("test_1","{\"id\":\"test_1\",\"method\":\"net_info\",\"jsonrpc\":\"2.0\"}");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
