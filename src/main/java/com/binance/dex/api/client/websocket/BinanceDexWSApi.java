package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.BlockMeta;
import com.binance.dex.api.client.domain.Proposal;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;

import java.util.List;

public interface BinanceDexWSApi {

    JsonRpcResponse netInfo();

    BlockMeta.BlockMetaResult blockByHeight(Long height);

    List<Transaction> txSearch(Long height);

    Transaction txByHash(String hash);

    Proposal getProposalByID(String proposalId);

}
