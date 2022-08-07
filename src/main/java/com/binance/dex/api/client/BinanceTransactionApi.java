package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.TransactionPageV2;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BinanceTransactionApi {
    @GET("api/v1/txs")
    Call<TransactionPageV2> getTransactions(@Query(value = "startTime") Long startTime,
                                            @Query(value = "endTime") Long endTime,
                                            @Query(value = "type") String type,
                                            @Query(value = "asset") String asset,
                                            @Query(value = "address") String address,
                                            @Query(value = "addressType") String addressType,
                                            @Query(value = "offset") Integer offset,
                                            @Query(value = "limit") Integer limit);

    @GET("api/v1/blocks/{blockHeight}/txs")
    Call<TransactionPageV2> getTransactionsInBlock(@Path("blockHeight") long blockHeight);
}
