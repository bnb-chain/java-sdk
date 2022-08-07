package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.broadcast.*;
import com.binance.dex.api.client.domain.request.ClosedOrdersRequest;
import com.binance.dex.api.client.domain.request.OpenOrdersRequest;
import com.binance.dex.api.client.domain.request.TradesRequest;
import com.binance.dex.api.client.domain.request.TransactionsRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface BinanceDexApiRestClient {
    Time getTime();

    Infos getNodeInfo();

    Validators getValidators();

    List<Peer> getPeers();

    List<Fees> getFees();

    List<Market> getMarkets(Integer limit);

    Account getAccount(String address);

    AccountSequence getAccountSequence(String address);

    TransactionMetadata getTransactionMetadata(String hash);

    List<Token> getTokens(Integer limit);

    OrderBook getOrderBook(String symbol, Integer limit);

    List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval);

    List<Candlestick> getCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime);

    OrderList getOpenOrders(String address);

    OrderList getOpenOrders(OpenOrdersRequest request);

    OrderList getClosedOrders(String address);

    OrderList getClosedOrders(ClosedOrdersRequest request);

    Order getOrder(String id);

    List<TickerStatistics> get24HrPriceStatistics();

    List<TickerStatistics> get24HrPriceStatistics(String symbol);

    TradePage getTrades();

    TradePage getTrades(TradesRequest request);

    TransactionPageV2 getTransactions(String address);

    TransactionPageV2 getTransactions(TransactionsRequest request);

    TransactionPageV2 getTransactionsInBlock(long blockHeight);

    List<MiniToken> getMiniTokens(Integer limit);

    List<Market> getMiniMarkets(Integer limit);

    List<Candlestick> getMiniCandleStickBars(String symbol, CandlestickInterval interval);

    List<Candlestick> getMiniCandleStickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime);

    OrderList getMiniOpenOrders(String address);

    OrderList getMiniOpenOrders(OpenOrdersRequest request);

    OrderList getMiniClosedOrders(String address);

    OrderList getMiniClosedOrders(ClosedOrdersRequest request);

    Order getMiniOrder(String id);

    List<TickerStatistics> getMini24HrPriceStatistics();

    List<TickerStatistics> getMini24HrPriceStatistics(String symbol);

    TradePage getMiniTrades();

    TradePage getMiniTrades(TradesRequest request);

    List<TransactionMetadata> newOrder(NewOrder newOrder, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> vote(Vote vote,Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> sideVote(SideVote vote,Wallet wallet, TransactionOption options, boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> cancelOrder(CancelOrder cancelOrder, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> transfer(Transfer transfer, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> multiTransfer(MultiTransfer multiTransfer, Wallet wallet, TransactionOption options,
                                            boolean sync) throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> freeze(TokenFreeze freeze, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> unfreeze(TokenUnfreeze unfreeze, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> htlt(HtltReq htltReq, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> depositHtlt(String swapId, List<com.binance.dex.api.client.encoding.message.Token> amount, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> claimHtlt(String swapId,byte[] randomNumber,Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> refundHtlt(String swapId,Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> transferTokenOwnership(String symbol, String newOwner, Wallet wallet, TransactionOption options, boolean sync)
            throws IOException, NoSuchAlgorithmException;

    List<TransactionMetadata> broadcast(String payload,boolean sync);
}
