package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.*;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BinanceDexApi {
    @GET("/api/v1/time")
    Call<Time> getTime();

    @GET("/api/v1/node-info")
    Call<Infos> getNodeInfo();

    @GET("/api/v1/validators")
    Call<Validators> getValidators();

    @GET("/api/v1/peers")
    Call<List<Peer>> getPeers();

    @GET("/api/v1/fees")
    Call<List<Fees>> getFees();

    @GET("/api/v1/account/{address}")
    Call<Account> getAccount(@Path("address") String address);

    @GET("/api/v1/account/{address}/sequence")
    Call<AccountSequence> getAccountSequence(@Path("address") String address);

    @GET("/api/v1/tx/{hash}")
    Call<TransactionMetadata> getTransactionMetadata(@Path("hash") String hash);

    @GET("/api/v1/tokens")
    Call<List<Token>> getTokens(@Query("limit") Integer limit);

    @GET("/api/v1/markets")
    Call<List<Market>> getMarkets(@Query("limit") Integer limit);


    @GET("/api/v1/depth")
    Call<OrderBook> getOrderBook(@Query("symbol") String symbol, @Query("limit") Integer limit);

    @GET("/api/v1/klines")
    Call<List<Candlestick>> getCandlestickBars(@Query("symbol") String symbol, @Query("interval") String interval,
                                               @Query("limit") Integer limit, @Query("startTime") Long startTime,
                                               @Query("endTime") Long endTime);

    @GET("/api/v1/orders/open")
    Call<OrderList> getOpenOrders(@Query("address") String address, @Query("limit") Integer limit,
                                  @Query("offset") Integer offset, @Query("symbol") String symbol,
                                  @Query("total") Integer total);

    @GET("/api/v1/orders/closed")
    Call<OrderList> getClosedOrders(@Query("address") String address, @Query("end") Long end,
                                    @Query("limit") Integer limit, @Query("offset") Integer offset,
                                    @Query("side") String side, @Query("start") Long start,
                                    @Query("status") List<String> status, @Query("symbol") String symbol,
                                    @Query("total") Integer total);

    @GET("/api/v1/orders/{id}")
    Call<Order> getOrder(@Path("id") String id);

    @GET("/api/v1/ticker/24hr")
    Call<List<TickerStatistics>> get24HrPriceStatistics();

    @GET("/api/v1/trades")
    Call<TradePage> getTrades(@Query("address") String address,
                              @Query("buyerOrderId") String buyerOrderId, @Query("end") Long end,
                              @Query("height") Long height, @Query("limit") Integer limit,
                              @Query("offset") Integer offset, @Query("quoteAsset") String quoteAsset,
                              @Query("sellerOrderId") String sellerOrderId, @Query("side") String side,
                              @Query("start") Long start, @Query("symbol") String symbol, @Query("total") Integer total);

    @GET("/api/v1/transactions")
    Call<TransactionPage> getTransactions(@Query("address") String address, @Query("blockHeight") Long blockHeight,
                                          @Query("endTime") Long endTime, @Query("limit") Integer limit,
                                          @Query("offset") Integer offset, @Query("side") String side,
                                          @Query("startTime") Long startTime, @Query("txAsset") String txAsset,
                                          @Query("txType") String txType);

    @POST("/api/v1/broadcast")
    Call<List<TransactionMetadata>> broadcast(@Query("sync") boolean sync, @Body RequestBody transaction);
}
