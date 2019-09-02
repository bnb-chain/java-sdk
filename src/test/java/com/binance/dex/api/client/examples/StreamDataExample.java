package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexApiWebSocketClient;
import com.binance.dex.api.client.BinanceDexEnvironment;

public class StreamDataExample {
    public static void main(String[] args) {
        BinanceDexApiWebSocketClient client =
                BinanceDexApiClientFactory.newInstance().newWebSocketClient();

        client.onDepthEvent("BNB_USDSB-1AC", System.out::println);

    }
}
