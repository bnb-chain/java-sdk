package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.websocket.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BinanceDexWSApiExample {

    private BinanceDexWSApi binanceDexWSApi;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before(){
        BinanceDexClientEndpoint<JsonRpcResponse> endpoint = BinanceDexClientWSFactory.newDefaultClientEndpoint();
        WebsocketLauncher.startUp(endpoint, BinanceDexEnvironment.TEST_NET_NODE.getWsBaseUrl());
        binanceDexWSApi = new BinanceDexWSApiImpl(endpoint,BinanceDexEnvironment.TEST_NET_NODE.getHrp());
    }

    @Test
    public void test(){
        binanceDexWSApi.netInfo("test_0");
        binanceDexWSApi.blockByHeight(13513018L,"test_1");
        List<Transaction> transactionList = binanceDexWSApi.txSearch(13513018L,"test_2");
        try {
            System.out.println(objectMapper.writeValueAsString(transactionList));
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
