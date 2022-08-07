package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.*;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BinanceDexApi {
    @GET("api/v1/time")
    Call<Time> getTime();

    @GET("api/v1/node-info")
    Call<Infos> getNodeInfo();

    @GET("api/v1/validators")
    Call<Validators> getValidators();

    @GET("api/v1/peers")
    Call<List<Peer>> getPeers();

    @GET("api/v1/fees")
    Call<List<Fees>> getFees();

    @GET("api/v1/account/{address}")
    Call<Account> getAccount(@Path("address") String address);

    @GET("api/v1/account/{address}/sequence")
    Call<AccountSequence> getAccountSequence(@Path("address") String address);

    @GET("api/v1/tx/{hash}")
    Call<TransactionMetadata> getTransactionMetadata(@Path("hash") String hash);

    @GET("api/v1/tokens")
    Call<List<Token>> getTokens(@Query("limit") Integer limit);

    @GET("api/v1/markets")
    Call<List<Market>> getMarkets(@Query("limit") Integer limit);


    @GET("api/v1/depth")
    Call<OrderBook> getOrderBook(@Query("symbol") String symbol, @Query("limit") Integer limit);

    @GET("api/v1/klines")
    Call<List<Candlestick>> getCandlestickBars(@Query("symbol") String symbol, @Query("interval") String interval,
                                               @Query("limit") Integer limit, @Query("startTime") Long startTime,
                                               @Query("endTime") Long endTime);

    @GET("api/v1/orders/open")
    Call<OrderList> getOpenOrders(@Query("address") String address, @Query("limit") Integer limit,
                                  @Query("offset") Integer offset, @Query("symbol") String symbol,
                                  @Query("total") Integer total);

    @GET("api/v1/orders/closed")
    Call<OrderList> getClosedOrders(@Query("address") String address, @Query("end") Long end,
                                    @Query("limit") Integer limit, @Query("offset") Integer offset,
                                    @Query("side") Integer side, @Query("start") Long start,
                                    @Query("status") List<String> status, @Query("symbol") String symbol,
                                    @Query("total") Integer total);

    @GET("api/v1/orders/{id}")
    Call<Order> getOrder(@Path("id") String id);

    @GET("api/v1/ticker/24hr")
    Call<List<TickerStatistics>> get24HrPriceStatistics();

    @GET("api/v1/ticker/24hr")
    Call<List<TickerStatistics>> get24HrPriceStatistics(@Query("symbol") String symbol);

    @GET("api/v1/trades")
    Call<TradePage> getTrades(@Query("address") String address,
                              @Query("buyerOrderId") String buyerOrderId, @Query("end") Long end,
                              @Query("height") Long height, @Query("limit") Integer limit,
                              @Query("offset") Integer offset, @Query("quoteAsset") String quoteAsset,
                              @Query("sellerOrderId") String sellerOrderId, @Query("side") Integer side,
                              @Query("start") Long start, @Query("symbol") String symbol, @Query("total") Integer total);

    @POST("api/v1/broadcast")
    Call<List<TransactionMetadata>> broadcast(@Query("sync") boolean sync, @Body RequestBody transaction);

    @GET("api/v1/mini/tokens")
    Call<List<MiniToken>> getMiniTokens(@Query("limit") Integer limit);

    @GET("api/v1/mini/markets")
    Call<List<Market>> getMiniMarkets(@Query("limit") Integer limit);

    @GET("api/v1/mini/klines")
    Call<List<Candlestick>> getMiniCandlestickBars(@Query("symbol") String symbol, @Query("interval") String interval,
                                               @Query("limit") Integer limit, @Query("startTime") Long startTime,
                                               @Query("endTime") Long endTime);

    @GET("api/v1/mini/orders/open")
    Call<OrderList> getMiniOpenOrders(@Query("address") String address, @Query("limit") Integer limit,
                                  @Query("offset") Integer offset, @Query("symbol") String symbol,
                                  @Query("total") Integer total);

    @GET("api/v1/mini/orders/closed")
    Call<OrderList> getMiniClosedOrders(@Query("address") String address, @Query("end") Long end,
                                    @Query("limit") Integer limit, @Query("offset") Integer offset,
                                    @Query("side") Integer side, @Query("start") Long start,
                                    @Query("status") List<String> status, @Query("symbol") String symbol,
                                    @Query("total") Integer total);

    @GET("api/v1/mini/orders/{id}")
    Call<Order> getMiniOrder(@Path("id") String id);

    @GET("api/v1/mini/ticker/24hr")
    Call<List<TickerStatistics>> getMini24HrPriceStatistics();

    @GET("api/v1/mini/ticker/24hr")
    Call<List<TickerStatistics>> getMini24HrPriceStatistics(@Query("symbol") String symbol);

    @GET("api/v1/mini/trades")
    Call<TradePage> getMiniTrades(@Query("address") String address,
                              @Query("buyerOrderId") String buyerOrderId, @Query("end") Long end,
                              @Query("height") Long height, @Query("limit") Integer limit,
                              @Query("offset") Integer offset, @Query("quoteAsset") String quoteAsset,
                              @Query("sellerOrderId") String sellerOrderId, @Query("side") Integer side,
                              @Query("start") Long start, @Query("symbol") String symbol, @Query("total") Integer total);
}
