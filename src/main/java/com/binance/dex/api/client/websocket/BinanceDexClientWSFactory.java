package com.binance.dex.api.client.websocket;


import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import com.google.common.collect.Maps;
import java.util.Map;

public class BinanceDexClientWSFactory {

    private final static Object lock = new Object();

    public static BinanceDexClientEndpoint newClientEndpoint(BinanceDexMessageHandler messageHandler,String url){
        return new BinanceDexClientEndpoint(messageHandler,url);
    }

    public static BinanceDexClientEndpoint<JsonRpcResponse> newDefaultClientEndpoint(String url){
        return new BinanceDexClientEndpoint<>(new DefaultMessageHandler(),url);
    }

    /**
     * One environment has only one instance
     */
    private static Map<String,BinanceDexWSApiImpl> binanceDexWSApiImplMap = Maps.newConcurrentMap();
    public static BinanceDexWSApiImpl getWSApiImpl(BinanceDexEnvironment env){
        synchronized (lock){
            binanceDexWSApiImplMap.computeIfAbsent(env.getWsBaseUrl(),(k)->{
                BinanceDexClientEndpoint<JsonRpcResponse> endpoint = newDefaultClientEndpoint(env.getWsBaseUrl());
                WebsocketLauncher.startUp(endpoint);
                return new BinanceDexWSApiImpl(env,endpoint);
            });
        }
        return binanceDexWSApiImplMap.get(env.getWsBaseUrl());
    }
    public static BinanceDexWSApiImpl getWSApiImpl(String url,String hrp, String valHrp){
        synchronized (lock){
            binanceDexWSApiImplMap.computeIfAbsent(url,(k)->{
                BinanceDexClientEndpoint<JsonRpcResponse> endpoint = newDefaultClientEndpoint(url);
                WebsocketLauncher.startUp(endpoint);
                return new BinanceDexWSApiImpl(hrp, valHrp, endpoint);
            });
        }
        return binanceDexWSApiImplMap.get(url);
    }




}
