package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.broadcast.Transaction;

import java.util.List;

public interface BinanceDexWSApi {

    void netInfo(String id);

    void blockByHeight(Long height,String id);

    List<Transaction> txSearch(Long height, String id);
}
