package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.request.ClosedOrdersRequest;
import com.binance.dex.api.client.domain.request.OpenOrdersRequest;
import com.binance.dex.api.client.domain.request.TradesRequest;
import com.binance.dex.api.client.domain.request.TransactionsRequest;

import java.util.List;

public interface BinanceDexApiAsyncRestClient {
    void getTime(BinanceDexApiCallback<Time> callback);

    void getNodeInfo(BinanceDexApiCallback<Infos> callback);

    void getValidators(BinanceDexApiCallback<Validators> callback);

    void getPeers(BinanceDexApiCallback<List<Peer>> callback);

    void getMarkets(Integer limit, BinanceDexApiCallback<List<Market>> callback);

    void getAccount(String address, BinanceDexApiCallback<Account> callback);

    void getAccountSequence(String address, BinanceDexApiCallback<AccountSequence> callback);

    void getTransactionMetadata(String hash, BinanceDexApiCallback<TransactionMetadata> callback);

    void getTokens(Integer limit, BinanceDexApiCallback<List<Token>> callback);

    void getOrderBook(String symbol, Integer limit, BinanceDexApiCallback<OrderBook> callback);

    void getCandleStickBars(String symbol, CandlestickInterval interval,
                            BinanceDexApiCallback<List<Candlestick>> callback);

    void getCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime,
                            BinanceDexApiCallback<List<Candlestick>> callback);

    void getOpenOrders(String address, BinanceDexApiCallback<OrderList> callback);

    void getOpenOrders(OpenOrdersRequest request, BinanceDexApiCallback<OrderList> callback);

    void getClosedOrders(String address, BinanceDexApiCallback<OrderList> callback);

    void getClosedOrders(ClosedOrdersRequest request, BinanceDexApiCallback<OrderList> callback);

    void getOrder(String id, BinanceDexApiCallback<Order> callback);

    void get24HrPriceStatistics(BinanceDexApiCallback<List<TickerStatistics>> callback);

    void getTrades(BinanceDexApiCallback<TradePage> callback);

    void getTrades(TradesRequest request, BinanceDexApiCallback<TradePage> callback);

    void getTransactions(String address, BinanceDexApiCallback<TransactionPageV2> callback);

    void getTransactions(TransactionsRequest request, BinanceDexApiCallback<TransactionPageV2> callback);

    void getTransactionsInBlock(long blockHeight, BinanceDexApiCallback<TransactionPageV2> callback);

    // Do not support async commitBroadcast due to account sequence
}
