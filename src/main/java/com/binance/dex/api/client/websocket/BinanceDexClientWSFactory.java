package com.binance.dex.api.client.websocket;


import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;

public class BinanceDexClientWSFactory {

    public static BinanceDexClientEndpoint newClientEndpoint(BinanceDexMessageHandler messageHandler){
        return new BinanceDexClientEndpoint(messageHandler);
    }

    public static BinanceDexClientEndpoint<JsonRpcResponse> newDefaultClientEndpoint(){
        return new BinanceDexClientEndpoint<>(new DefaultMessageHandler());
    }



}
