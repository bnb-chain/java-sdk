package com.binance.dex.api.client;

import com.binance.dex.api.client.impl.BinanceDexApiAsyncRestClientImpl;
import com.binance.dex.api.client.impl.BinanceDexApiNodeClientImpl;
import com.binance.dex.api.client.impl.BinanceDexApiRestClientImpl;
import com.binance.dex.api.client.impl.BinanceDexApiWebSocketClientImpl;

public class BinanceDexApiClientFactory {
    private BinanceDexApiClientFactory() {
    }

    public static BinanceDexApiClientFactory newInstance() {
        return new BinanceDexApiClientFactory();
    }

    public BinanceDexApiRestClient newRestClient() {
        return newRestClient(BinanceDexEnvironment.PROD.getBaseUrl());
    }

    public BinanceDexApiRestClient newRestClient(String baseUrl) {
        return new BinanceDexApiRestClientImpl(baseUrl);
    }

    public BinanceDexApiRestClient newRestClient(String baseUrl,String apiKey){
        return new BinanceDexApiRestClientImpl(baseUrl,apiKey);
    }

    public BinanceDexApiNodeClient newNodeRpcClient() {
        return newNodeRpcClient(BinanceDexEnvironment.PROD.getNodeUrl(), BinanceDexEnvironment.PROD.getHrp(), BinanceDexEnvironment.PROD.getValHrp());
    }

    public BinanceDexApiNodeClient newNodeRpcClient(String baseUrl, String hrp, String valHrp) {
        return new BinanceDexApiNodeClientImpl(baseUrl, hrp, valHrp);
    }

    public BinanceDexApiAsyncRestClient newAsyncRestClient() {
        return newAsyncRestClient(BinanceDexEnvironment.PROD.getBaseUrl());
    }

    public BinanceDexApiAsyncRestClient newAsyncRestClient(String baseUrl) {
        return new BinanceDexApiAsyncRestClientImpl(baseUrl);
    }

    public BinanceDexApiAsyncRestClient newAsyncRestClient(String baseUrl,String apiKey) {
        return new BinanceDexApiAsyncRestClientImpl(baseUrl,apiKey);
    }

    public BinanceDexApiWebSocketClient newWebSocketClient() {
        return new BinanceDexApiWebSocketClientImpl(BinanceDexEnvironment.PROD.getStreamUrl());
    }

    public BinanceDexApiWebSocketClient newWebSocketClient(String baseUrl) {
        return new BinanceDexApiWebSocketClientImpl(baseUrl);
    }

    public BinanceDexApiWebSocketClient newWebSocketClient(String baseUrl,String apiKey) {
        return new BinanceDexApiWebSocketClientImpl(baseUrl,apiKey);
    }

}
