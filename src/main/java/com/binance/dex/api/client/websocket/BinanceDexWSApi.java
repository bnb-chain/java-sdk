package com.binance.dex.api.client.websocket;

public interface BinanceDexWSApi {

    void netInfo(String id);

    void blockByHeight(Long height,String id);

    void txSearch(Long height,String id);
}
