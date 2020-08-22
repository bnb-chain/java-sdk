package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.TransactionConverter;
import com.binance.dex.api.client.domain.BlockMeta;
import com.binance.dex.api.client.domain.Proposal;
import com.binance.dex.api.client.domain.WSMethod;
import com.binance.dex.api.client.domain.broadcast.Transaction;
import com.binance.dex.api.client.domain.exception.BinanceDexWSException;
import com.binance.dex.api.client.domain.jsonrpc.ABCIQueryResult;
import com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.binance.dex.api.client.domain.websocket.AbciQueryRequest;
import com.binance.dex.api.client.domain.websocket.CommonRequest;
import com.binance.dex.api.client.domain.websocket.TxSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Binance Dex Websocket API implementation.
 *
 */
public class BinanceDexWSApiImpl extends IdGenerator implements BinanceDexWSApi {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String JSON_RPC_VERSION = "2.0";
    private BinanceDexClientEndpoint<JsonRpcResponse> endpoint;
    private TransactionConverter transactionConverter;

    BinanceDexWSApiImpl(BinanceDexEnvironment env,BinanceDexClientEndpoint<JsonRpcResponse> endpoint){
        this.endpoint = endpoint;
        this.transactionConverter = new TransactionConverter(env.getHrp(), env.getValHrp());
    }

    BinanceDexWSApiImpl(String hrp, String valHrp, BinanceDexClientEndpoint<JsonRpcResponse> endpoint){
        this.endpoint = endpoint;
        this.transactionConverter = new TransactionConverter(hrp, valHrp);
    }


    @Override
    public JsonRpcResponse netInfo() {
        String id = getId(WSMethod.net_info.name());
        return endpoint.sendMessage(id,buildWSRequest(WSMethod.net_info.name(),id,null));
    }

    @Override
    public BlockMeta.BlockMetaResult blockByHeight(Long height) {
        String id = getId(WSMethod.block.name());
        BlockMeta.BlockMetaResult block;
        Map.Entry params = Maps.immutableEntry("height",String.valueOf(height));
        String request = buildWSRequest(WSMethod.block.name(),id,params);
        JsonRpcResponse response = endpoint.sendMessage(id,request);
        if(response.getError() != null){
            throw new BinanceDexWSException(id,WSMethod.block.name(),response.getError());
        }
        try {
            block = objectMapper.readValue(objectMapper.writeValueAsBytes(response.getResult()),BlockMeta.BlockMetaResult.class);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return block;
    }

    @Override
    public List<Transaction> txSearch(Long height){
        String id = getId(WSMethod.tx_search.name());
        List<Transaction> transactions;
        TxSearchRequest txSearchRequest = new TxSearchRequest();
        txSearchRequest.setPage("1");
        txSearchRequest.setPerPage("10000");
        txSearchRequest.setProve(false);
        txSearchRequest.setQuery("tx.height="+height);
        JsonRpcResponse response = endpoint.sendMessage(id,buildWSRequest(WSMethod.tx_search.name(),id,txSearchRequest));
        if(response.getError() != null){
            throw new BinanceDexWSException(id,WSMethod.tx_search.name(),response.getError());
        }
        try {
            BlockInfoResult blockInfoResult =  objectMapper.readValue(objectMapper.writeValueAsString(response.getResult()),BlockInfoResult.class);
            transactions = blockInfoResult.getTxs().stream()
                    .map(transactionConverter::convert)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public Transaction txByHash(String hash) {
        try {
            String id = getId(WSMethod.tx.name());
            Map.Entry params = null;
            try {
                params = Maps.immutableEntry("hash", Hex.decodeHex(hash.toCharArray()));
            } catch (DecoderException e) {
                throw new BinanceDexWSException(id,WSMethod.tx.name(),"invalid tx hash:"+hash);
            }
            JsonRpcResponse response = endpoint.sendMessage(id,buildWSRequest(WSMethod.tx.name(),id,params));
            if(response.getError() != null){
                throw new BinanceDexWSException(id,WSMethod.tx.name(),response.getError());
            }
            com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult.Transaction transaction = objectMapper.readValue(objectMapper.writeValueAsString(response.getResult()), com.binance.dex.api.client.domain.jsonrpc.BlockInfoResult.Transaction.class);

            List<Transaction> transactionList = transactionConverter.convert(transaction);
            if(null != transactionList){
                return transactionList.get(0);
            }
            return null;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Proposal getProposalByID(String proposalId) {
        try {
            String id = getId(WSMethod.abci_query.name());
            Map.Entry proposalIdEntry = Maps.immutableEntry("ProposalID",proposalId);
            AbciQueryRequest abciQueryRequest = new AbciQueryRequest();
            abciQueryRequest.setPath("custom/gov/proposal");
            abciQueryRequest.setData(Hex.encodeHexString(objectMapper.writeValueAsString(proposalIdEntry).getBytes()));

            JsonRpcResponse response = endpoint.sendMessage(id,buildWSRequest(WSMethod.abci_query.name(),id,abciQueryRequest));
            if(response.getError() != null){
                throw new BinanceDexWSException(id,WSMethod.abci_query.name(),response.getError());
            }
            ABCIQueryResult result = objectMapper.readValue(objectMapper.writeValueAsString(response.getResult()),ABCIQueryResult.class);
            if(null != result.getResponse().getCode() || null == result.getResponse().getValue()){
                throw new BinanceDexWSException(id,WSMethod.abci_query.name(),result.getResponse().getLog());
            }
            String proposalJson = new String(result.getResponse().getValue());
            return objectMapper.readValue(proposalJson, Proposal.class);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    private String buildWSRequest(String method,String id,Object params){
        CommonRequest request = new CommonRequest();
        request.setId(id);
        request.setJsonRpc(JSON_RPC_VERSION);
        request.setMethod(method);
        request.setParams(params);
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
