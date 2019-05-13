package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.BlockMeta;
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
        binanceDexWSApi = BinanceDexClientWSFactory.getWSApiImpl(BinanceDexEnvironment.TEST_NET_NODE);
    }

    @Test
    public void test(){
        binanceDexWSApi.netInfo();
        try {
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.blockByHeight(13513018000L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13513018L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14386667L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14353547L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13080127L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13028378L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14338593L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13712964L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13666599L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14366979L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14322134L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13565567L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(14324052L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txSearch(13712675L)));
            System.out.println(objectMapper.writeValueAsString(binanceDexWSApi.txByHash("1A3C0D696371455B2B57154D4CED21FE30C93B349EFA5270CDB044ED217D7B27")));
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
