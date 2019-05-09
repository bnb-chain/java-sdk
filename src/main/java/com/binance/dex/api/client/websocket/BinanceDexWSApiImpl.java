package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.websocket.CommonRequest;
import com.binance.dex.api.client.domain.websocket.TxSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BinanceDexWSApiImpl implements BinanceDexWSApi {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private BinanceDexClientEndpoint endpoint;

    public BinanceDexWSApiImpl(BinanceDexClientEndpoint endpoint){
        this.endpoint = endpoint;
    }

    @Override
    public void netInfo(String id) {
        endpoint.sendMessage(buildWSRequest("net_info",id,null));
    }

    @Override
    public void blockByHeight(Long height,String id) {
        Object params = objectMapper.createObjectNode().putPOJO("height",String.valueOf(height));
        endpoint.sendMessage(buildWSRequest("block",id,params));
    }

    @Override
    public void txSearch(Long height,String id){
        TxSearchRequest txSearchRequest = new TxSearchRequest();
        txSearchRequest.setPage("1");
        txSearchRequest.setPerPage("10000");
        txSearchRequest.setProve(false);
        txSearchRequest.setQuery("tx.height="+height);
        endpoint.sendMessage(buildWSRequest("tx_search",id,txSearchRequest));
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
