package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiAsyncRestClient;
import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.TransactionPageV2;
import com.binance.dex.api.client.domain.request.TransactionsRequest;

public class TransactionExampleAsync {
    public static void main(String[] args) {
        BinanceDexApiAsyncRestClient client =
                BinanceDexApiClientFactory.newInstance().newAsyncRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        //Get transactions in last 24 hours
        client.getTransactions("tbnb135mqtf9gef879nmjlpwz6u2fzqcw4qlzrqwgvw", response -> System.out.println(response));

        //Get transactions by criteria
        //Refer to this for more: https://docs.binance.org/api-reference/dex-api/block-service.html#apiv1txs
        TransactionsRequest request = new TransactionsRequest();
        request.setStartTime(1629272621945L);
        request.setEndTime(1629359021945L);
        request.setType("ORACLE_CLAIM");
        request.setAddress("tbnb135mqtf9gef879nmjlpwz6u2fzqcw4qlzrqwgvw");
        request.setAddressType("FROM");
        request.setOffset(5);
        request.setLimit(1);
        client.getTransactions(request, response -> System.out.println(response));

        //Get transaction a block
        client.getTransactionsInBlock(15759101, response -> System.out.println(response));
    }
}
