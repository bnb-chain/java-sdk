package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiAsyncRestClient;
import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexEnvironment;

public class MarketDataExampleAsync {
    public static void main(String[] args) {
        BinanceDexApiAsyncRestClient client =
                BinanceDexApiClientFactory.newInstance().newAsyncRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());
        client.getTime(response -> System.out.println(response));

        client.getNodeInfo(response -> System.out.println(response));

        client.getOrderBook("ADA.B-B63_BNB", 5, response -> System.out.println(response));

        client.getAccount("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2",
                response -> System.out.println(response));

        client.getAccountSequence("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2",
                response -> System.out.println(response));
    }
}
