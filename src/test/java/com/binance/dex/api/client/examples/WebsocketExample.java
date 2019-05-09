package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.websocket.*;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class WebsocketExample {

    private BinanceDexWSApi binanceDexWSApi;
    private CountDownLatch countDownLatch;

    @Before
    public void before(){
        countDownLatch = new CountDownLatch(3);
        BinanceDexClientEndpoint endpoint = BinanceDexClientWSFactory.getInstance().newClientEndpoint(message -> {
            System.out.println(message);
            countDownLatch.countDown();
        });
        WebsocketLauncher.startUp(endpoint, BinanceDexEnvironment.TEST_NET_NODE.getWsBaseUrl());
        binanceDexWSApi = new BinanceDexWSApiImpl(endpoint);
    }

    @Test
    public void test(){
        binanceDexWSApi.netInfo("test_0");
        binanceDexWSApi.blockByHeight(13513018L,"test_1");
        binanceDexWSApi.txSearch(13513018L,"test_2");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
