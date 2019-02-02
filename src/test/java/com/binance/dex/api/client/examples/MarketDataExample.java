package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.*;

public class MarketDataExample {
    public static void main(String[] args) {
        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());
        Time time = client.getTime();
        System.out.println(time);

        Infos nodeInfo = client.getNodeInfo();
        System.out.println(nodeInfo);

        OrderBook orderBook = client.getOrderBook("ADA.B-B63_BNB", 5);
        System.out.println(orderBook);

        Account account = client.getAccount("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2");
        System.out.println(account);

        AccountSequence accountSequence =
                client.getAccountSequence("tbnb16hywxpvvkaz6cecjz89mf2w0da3vfeg6z6yky2");
        System.out.println(accountSequence);
    }
}
