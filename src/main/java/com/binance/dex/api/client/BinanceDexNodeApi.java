package com.binance.dex.api.client;

import com.binance.dex.api.client.domain.BlockMeta;
import com.binance.dex.api.client.domain.jsonrpc.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BinanceDexNodeApi {

    @GET("/abci_query")
    Call<JsonRpcResponse<AccountResult>> getAccount(@Query("path") String pathWithAddress);

    @GET("/abci_query?path=%22/store/acc/key%22")
    Call<JsonRpcResponse<AccountResult>> getCommittedAccount(@Query("data") String address);

    @GET("/abci_query")
    Call<JsonRpcResponse<ABCIQueryResult>> getTokenInfo(@Query("path") String pathWithSymbol);

    @GET("/abci_query?path=%22/param/fees%22")
    Call<JsonRpcResponse<ABCIQueryResult>> getFees();

    @GET("abci_query?path=%22custom/atomicSwap/swapid%22")
    Call<JsonRpcResponse<ABCIQueryResult>> getSwapByID(@Query("data") String data);

    @GET("/abci_query?path=%22custom/stake/validators%22")
    Call<JsonRpcResponse<ABCIQueryResult>> getStakeValidators();

    @GET("/abci_query?path=%22custom/gov/proposal%22")
    Call<JsonRpcResponse<ABCIQueryResult>> getProposalById(@Query("data") String data);

    @GET("/tx_search")
    Call<JsonRpcResponse<BlockInfoResult>> getBlockTransactions(@Query("query") String height,@Query("page") int page,@Query("per_page") int perPage);

    @GET("/broadcast_tx_commit")
    Call<JsonRpcResponse<CommitBroadcastResult>> commitBroadcast(@Query("tx") String tx);

    @GET("/broadcast_tx_sync")
    Call<JsonRpcResponse<AsyncBroadcastResult>> asyncBroadcast(@Query("tx") String tx);

    @GET("/status")
    Call<JsonRpcResponse<NodeInfos>> getNodeStatus();

    @GET("/tx")
    Call<JsonRpcResponse<TransactionResult>> getTransaction(@Query("hash") String hash);

    @GET("/block")
    Call<JsonRpcResponse<BlockMeta.BlockMetaResult>> getBlock(@Query("height") Long height);

    @GET("/block_by_hash")
    Call<JsonRpcResponse<BlockMeta.BlockMetaResult>> getBlock(@Query("hash") String hash);

    @GET("/abci_query?height=0&prove=false")
    Call<JsonRpcResponse<ABCIQueryResult>> abciQuery(@Query("path") String path, @Query("data") String data);

    @GET("/abci_query")
    Call<JsonRpcResponse<ABCIQueryResult>> abciQueryWithOptions(@Query("path") String path, @Query("data") String data, @Query("height") long height, @Query("prove") boolean prove);

}
