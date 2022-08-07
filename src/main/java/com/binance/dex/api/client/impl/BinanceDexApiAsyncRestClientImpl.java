package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.request.ClosedOrdersRequest;
import com.binance.dex.api.client.domain.request.OpenOrdersRequest;
import com.binance.dex.api.client.domain.request.TradesRequest;
import com.binance.dex.api.client.domain.request.TransactionsRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceDexApiAsyncRestClientImpl implements BinanceDexApiAsyncRestClient {
    private BinanceDexApi binanceDexApi;
    private BinanceTransactionApi binanceTransactionApi;

    public BinanceDexApiAsyncRestClientImpl(String baseUrl) {
        this.binanceDexApi = BinanceDexApiClientGenerator.createService(BinanceDexApi.class, baseUrl);

        String transactionUrl = BinanceDexEnvironment.inferTransactionUrl(baseUrl);
        if (transactionUrl != null) {
            this.binanceTransactionApi = BinanceDexApiClientGenerator.createService(BinanceTransactionApi.class, transactionUrl);
        }
    }

    public BinanceDexApiAsyncRestClientImpl(String baseUrl, String apiKey){
        String transactionUrl = BinanceDexEnvironment.inferTransactionUrl(baseUrl);
        if(StringUtils.isBlank(apiKey)){
            this.binanceDexApi = BinanceDexApiClientGenerator.createService(BinanceDexApi.class, baseUrl);
            if (transactionUrl != null) {
                this.binanceTransactionApi = BinanceDexApiClientGenerator.createService(BinanceTransactionApi.class, transactionUrl);
            }
        }else{
            this.binanceDexApi = BinanceDexApiClientGenerator.createService(BinanceDexApi.class,apiKey,baseUrl + "/internal/");
            if (transactionUrl != null) {
                this.binanceTransactionApi = BinanceDexApiClientGenerator.createService(BinanceTransactionApi.class, transactionUrl + "/internal/");
            }
        }
    }

    @Override
    public void getTime(BinanceDexApiCallback<Time> callback) {
        binanceDexApi.getTime().enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getNodeInfo(BinanceDexApiCallback<Infos> callback) {
        binanceDexApi.getNodeInfo().enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getValidators(BinanceDexApiCallback<Validators> callback) {
        binanceDexApi.getValidators().enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getPeers(BinanceDexApiCallback<List<Peer>> callback) {
        binanceDexApi.getPeers().enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getMarkets(Integer limit, BinanceDexApiCallback<List<Market>> callback) {
        binanceDexApi.getMarkets(limit).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getAccount(String address, BinanceDexApiCallback<Account> callback) {
        binanceDexApi.getAccount(address).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getAccountSequence(String address, BinanceDexApiCallback<AccountSequence> callback) {
        binanceDexApi.getAccountSequence(address).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getTransactionMetadata(String hash, BinanceDexApiCallback<TransactionMetadata> callback) {
        binanceDexApi.getTransactionMetadata(hash).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getTokens(Integer limit, BinanceDexApiCallback<List<Token>> callback) {
        binanceDexApi.getTokens(limit).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOrderBook(String symbol, Integer limit, BinanceDexApiCallback<OrderBook> callback) {
        binanceDexApi.getOrderBook(symbol, limit).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getCandleStickBars(String symbol, CandlestickInterval interval,
                                   BinanceDexApiCallback<List<Candlestick>> callback) {
        getCandleStickBars(symbol, interval, null, null, null, callback);
    }

    @Override
    public void getCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime,
                                   Long endTime, BinanceDexApiCallback<List<Candlestick>> callback) {
        binanceDexApi.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime)
                .enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOpenOrders(String address, BinanceDexApiCallback<OrderList> callback) {
        OpenOrdersRequest request = new OpenOrdersRequest();
        request.setAddress(address);
        getOpenOrders(address, callback);
    }

    @Override
    public void getOpenOrders(OpenOrdersRequest request, BinanceDexApiCallback<OrderList> callback) {
        binanceDexApi.getOpenOrders(request.getAddress(), request.getLimit(),
                request.getOffset(), request.getSymbol(), request.getTotal()).enqueue(
                new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getClosedOrders(String address, BinanceDexApiCallback<OrderList> callback) {
        ClosedOrdersRequest request = new ClosedOrdersRequest();
        request.setAddress(address);
        getClosedOrders(request, callback);
    }

    @Override
    public void getClosedOrders(ClosedOrdersRequest request, BinanceDexApiCallback<OrderList> callback) {
        Integer side = request.getSide() == null ? null : (int)(request.getSide().toValue());
        List<String> statusStrList = null;
        if (request.getStatus() != null)
            statusStrList = request.getStatus().stream().map(s -> s.name()).collect(Collectors.toList());
        binanceDexApi.getClosedOrders(request.getAddress(), request.getEnd(), request.getLimit(),
                request.getLimit(), side, request.getStart(), statusStrList, request.getSymbol(),
                request.getTotal()).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOrder(String id, BinanceDexApiCallback<Order> callback) {
        binanceDexApi.getOrder(id).enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void get24HrPriceStatistics(BinanceDexApiCallback<List<TickerStatistics>> callback) {
        binanceDexApi.get24HrPriceStatistics().enqueue(new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getTrades(BinanceDexApiCallback<TradePage> callback) {
        TradesRequest request = new TradesRequest();
        getTrades(request, callback);
    }

    @Override
    public void getTrades(TradesRequest request, BinanceDexApiCallback<TradePage> callback) {
        Integer side = request.getSide() == null ? null : (int)(request.getSide().toValue());
        binanceDexApi.getTrades(
                request.getAddress(), request.getBuyerOrderId(),
                request.getEnd(), request.getHeight(), request.getLimit(), request.getOffset(),
                request.getQuoteAsset(), request.getSellerOrderId(), side,
                request.getStart(), request.getSymbol(), request.getTotal()).enqueue(
                new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getTransactions(String address, BinanceDexApiCallback<TransactionPageV2> callback) {
        TransactionsRequest request = new TransactionsRequest();
        request.setAddress(address);
        Long endTime = new Date().getTime();
        Long startTime = endTime - 86400000;
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        getTransactions(request, callback);
    }

    @Override
    public void getTransactions(TransactionsRequest request, BinanceDexApiCallback<TransactionPageV2> callback) {
        binanceTransactionApi.getTransactions(
                request.getStartTime(), request.getEndTime(),
                request.getType(), request.getAsset(),
                request.getAddress(), request.getAddressType(),
                request.getOffset(), request.getLimit()).enqueue(
                new BinanceDexApiCallbackAdapter<>(callback));
    }

    @Override
    public void getTransactionsInBlock(long blockHeight, BinanceDexApiCallback<TransactionPageV2> callback) {
        binanceTransactionApi.getTransactionsInBlock(
                blockHeight).enqueue(
                new BinanceDexApiCallbackAdapter<>(callback));
    }
}
