package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.TransactionPage;
import com.binance.dex.api.client.domain.TransactionPageV2;
import com.binance.dex.api.client.domain.request.TransactionsRequest;
import com.binance.dex.api.client.utils.converter.TransactionConverterFactory;

public class TransactionExample {
    public static void main(String[] args) {
        BinanceDexApiRestClient client =
                BinanceDexApiClientFactory.newInstance().newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());

        //Get transactions in last 24 hours
        TransactionPageV2 transactions = client.getTransactions("tbnb135mqtf9gef879nmjlpwz6u2fzqcw4qlzrqwgvw");
        System.out.println(transactions);

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
        transactions = client.getTransactions(request);
        System.out.println(transactions);

        //Get transaction a block
        transactions = client.getTransactionsInBlock(15759101);
        System.out.println(transactions);

        //Convert transaction to previous models
        TransactionConverterFactory converterFactory = new TransactionConverterFactory();
        TransactionPage transactionPage = converterFactory.convert(transactions);
        System.out.println(transactionPage);
    }
}
