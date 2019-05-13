package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.TransactionConverter;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.domain.websocket.CommonRequest;
import com.binance.dex.api.client.domain.websocket.TxSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Binance Dex Websocket API implementation.
 *
 */
public class BinanceDexWSApiImpl implements BinanceDexWSApi {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private BinanceDexClientEndpoint<JsonRpcResponse> endpoint;
    private TransactionConverter transactionConverter;

    BinanceDexWSApiImpl(BinanceDexEnvironment env,BinanceDexClientEndpoint<JsonRpcResponse> endpoint){
        this.endpoint = endpoint;
        this.transactionConverter = new TransactionConverter(env.getHrp());
    }

    BinanceDexWSApiImpl(String hrp,BinanceDexClientEndpoint<JsonRpcResponse> endpoint){
        this.endpoint = endpoint;
        this.transactionConverter = new TransactionConverter(hrp);
    }


    @Override
    public void netInfo(String id) {
        endpoint.sendMessage(id,buildWSRequest("net_info",id,null));
    }

    @Override
    public void blockByHeight(Long height,String id) {
        Object params = objectMapper.createObjectNode().putPOJO("height",String.valueOf(height));
        endpoint.sendMessage(id,buildWSRequest("block",id,params));
    }

    @Override
    public List<Transaction> txSearch(Long height,String id){
        List<Transaction> transactions = null;
        TxSearchRequest txSearchRequest = new TxSearchRequest();
        txSearchRequest.setPage("1");
        txSearchRequest.setPerPage("10000");
        txSearchRequest.setProve(false);
        txSearchRequest.setQuery("tx.height="+height);
        JsonRpcResponse response = endpoint.sendMessage(id,buildWSRequest("tx_search",id,txSearchRequest));
        try {
            if(response != null){
                BlockInfoResult blockInfoResult =  objectMapper.readValue(objectMapper.writeValueAsString(response.getResult()),BlockInfoResult.class);
                transactions = blockInfoResult.getTxs().stream()
                        .map(transactionConverter::convert)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }


    private String buildWSRequest(String method,String id,Object params){
        CommonRequest request = new CommonRequest();
        request.setId(id);
        request.setJsonRpc("2.0");
        request.setMethod(method);
        request.setParams(params);
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
