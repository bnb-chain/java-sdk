package com.binance.dex.api.client.impl;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.request.ClosedOrdersRequest;
import com.binance.dex.api.client.domain.request.OpenOrdersRequest;
import com.binance.dex.api.client.domain.request.TradesRequest;
import com.binance.dex.api.client.domain.request.TransactionsRequest;
import com.binance.dex.api.client.encoding.message.TransactionRequestAssembler;
import okhttp3.RequestBody;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Binance dex API rest client, supporting synchronous/blocking access Binance dex's REST API.
 */
public class BinanceDexApiRestClientImpl implements BinanceDexApiRestClient {
    private BinanceDexApi binanceDexApi;

    public BinanceDexApiRestClientImpl(String baseUrl) {
        this.binanceDexApi = BinanceDexApiClientGenerator.createService(BinanceDexApi.class, baseUrl);
    }

    public Time getTime() {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getTime());
    }

    public Infos getNodeInfo() {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getNodeInfo());
    }

    public Validators getValidators() {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getValidators());
    }

    public List<Peer> getPeers() {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getPeers());
    }

    public List<Market> getMarkets() {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getMarkets());
    }

    public Account getAccount(String address) {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getAccount(address));
    }

    public AccountSequence getAccountSequence(String address) {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getAccountSequence(address));
    }

    public TransactionMetadata getTransactionMetadata(String hash) {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getTransactionMetadata(hash));
    }

    public List<Token> getTokens() {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getTokens());
    }

    public OrderBook getOrderBook(String symbol, Integer limit) {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getOrderBook(symbol, limit));
    }

    public List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval) {
        return getCandleStickBars(symbol, interval, null, null, null);
    }

    public List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime));
    }

    public OrderList getOpenOrders(String address) {
        OpenOrdersRequest request = new OpenOrdersRequest();
        request.setAddress(address);
        return getOpenOrders(request);
    }

    public OrderList getOpenOrders(OpenOrdersRequest request) {
        return BinanceDexApiClientGenerator.executeSync(
                binanceDexApi.getOpenOrders(request.getAddress(), request.getLimit(),
                        request.getOffset(), request.getSymbol(), request.getTotal()));
    }

    public OrderList getClosedOrders(String address) {
        ClosedOrdersRequest request = new ClosedOrdersRequest();
        request.setAddress(address);
        return getClosedOrders(request);
    }

    public OrderList getClosedOrders(ClosedOrdersRequest request) {
        String sidStr = request.getSide() == null ? null : request.getSide().name();
        List<String> statusStrList = null;
        if (request.getStatus() != null)
            statusStrList = request.getStatus().stream().map(s -> s.name()).collect(Collectors.toList());
        return BinanceDexApiClientGenerator.executeSync(
                binanceDexApi.getClosedOrders(request.getAddress(), request.getEnd(), request.getLimit(),
                        request.getLimit(), sidStr, request.getStart(), statusStrList, request.getSymbol(),
                        request.getTotal()));
    }

    public Order getOrder(String id) {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.getOrder(id));
    }

    public List<TickerStatistics> get24HrPriceStatistics() {
        return BinanceDexApiClientGenerator.executeSync(binanceDexApi.get24HrPriceStatistics());
    }

    @Override
    public TradePage getTrades() {
        TradesRequest request = new TradesRequest();
        return getTrades(request);
    }

    @Override
    public TradePage getTrades(TradesRequest request) {
        String sideStr = request.getSide() == null ? null : request.getSide().name();
        return BinanceDexApiClientGenerator.executeSync(
                binanceDexApi.getTrades(
                        request.getAddress(), request.getBuyerOrderId(),
                        request.getEnd(), request.getHeight(), request.getLimit(), request.getOffset(),
                        request.getQuoteAsset(), request.getSellerOrderId(), sideStr,
                        request.getStart(), request.getSymbol(), request.getTotal()));
    }

    @Override
    public TransactionPage getTransactions(String address) {
        TransactionsRequest request = new TransactionsRequest();
        request.setAddress(address);
        return getTransactions(request);
    }

    @Override
    public TransactionPage getTransactions(TransactionsRequest request) {
        String sideStr = request.getSide() != null ? request.getSide().name() : null;
        String txTypeStr = request.getTxType() != null ? request.getTxType().name() : null;
        return BinanceDexApiClientGenerator.executeSync(
                binanceDexApi.getTransactions(
                        request.getAddress(), request.getBlockHeight(), request.getEndTime(),
                        request.getLimit(), request.getOffset(), sideStr,
                        request.getStartTime(), request.getTxAsset(), txTypeStr));
    }

    // Broadcast and handle account sequence
    private List<TransactionMetadata> broadcast(RequestBody requestBody, boolean sync, Wallet wallet) {
        try {
            List<TransactionMetadata> metadatas =
                    BinanceDexApiClientGenerator.executeSync(binanceDexApi.broadcast(sync, requestBody));
            if (!metadatas.isEmpty() && metadatas.get(0).isOk()) {
                wallet.increaseAccountSequence();
            }
            return metadatas;
        } catch (BinanceDexApiException e) {
            wallet.invalidAccountSequence();
            throw e;
        }
    }

    public List<TransactionMetadata> newOrder(NewOrder newOrder, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        wallet.ensureWalletIsReady(this);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
        RequestBody requestBody = assembler.buildNewOrder(newOrder);
        return broadcast(requestBody, sync, wallet);
    }

    public List<TransactionMetadata> cancelOrder(CancelOrder cancelOrder, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        wallet.ensureWalletIsReady(this);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
        RequestBody requestBody = assembler.buildCancelOrder(cancelOrder);
        return broadcast(requestBody, sync, wallet);
    }

    public List<TransactionMetadata> transfer(Transfer transfer, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        wallet.ensureWalletIsReady(this);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
        RequestBody requestBody = assembler.buildTransfer(transfer);
        return broadcast(requestBody, sync, wallet);
    }

    public List<TransactionMetadata> freeze(TokenFreeze freeze, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        wallet.ensureWalletIsReady(this);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
        RequestBody requestBody = assembler.buildTokenFreeze(freeze);
        return broadcast(requestBody, sync, wallet);
    }

    public List<TransactionMetadata> unfreeze(TokenUnfreeze unfreeze, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException {
        wallet.ensureWalletIsReady(this);
        TransactionRequestAssembler assembler = new TransactionRequestAssembler(wallet, options);
        RequestBody requestBody = assembler.buildTokenUnfreeze(unfreeze);
        return broadcast(requestBody, sync, wallet);
    }
}
