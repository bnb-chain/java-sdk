package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.exception.DexWSTimeoutException;
import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import org.apache.commons.lang3.StringUtils;

import javax.websocket.Session;

/**
 * the default implementation of {@link BinanceDexMessageHandler},it is must when {@link BinanceDexWSApiImpl} is needed.
 *
 */
public class DefaultMessageHandler implements BinanceDexMessageHandler<JsonRpcResponse> {

    private long callBackTimeout;
    private static final long DEFAULT_CALLBACK_TIMEOUT = 30_000L;
    private volatile WSResponseCache cache = WSResponseCache.instance();
    DefaultMessageHandler(long callBackTimeout){
        this.callBackTimeout = callBackTimeout;
    }
    DefaultMessageHandler(){
        this.callBackTimeout = DEFAULT_CALLBACK_TIMEOUT;
    }


    /**
     * send a message to websocket server,then wait synchronously for the result to be returned
     * @param userSession session of a websocket connection
     * @param id unique id of a message
     * @param message body of message
     * @return response by websocket server
     */
    @Override
    public JsonRpcResponse send(Session userSession,String id,String message) {
        if(null != userSession){
            userSession.getAsyncRemote().sendText(message);
        }
        JsonRpcResponse response;
        boolean isTimeout = false;
        long time = System.currentTimeMillis();
        long waitTime = 0L;
        while ( null == (response = cache.get(id)) && !(isTimeout = waitTime > callBackTimeout)){
            waitTime = System.currentTimeMillis() - time;
        }
        if(isTimeout){
            throw new DexWSTimeoutException("timeout,request body: " + message );
        }
        return response;
    }

    /**
     * receive response from websocket server,and put it to cache {@link WSResponseCache}
     * @param response response from websocket server
     */
    @Override
    public void onMessage(JsonRpcResponse response){
        if(response == null || StringUtils.isEmpty(response.getId())){
            throw new RuntimeException("invalid response");
        }
        cache.add(response);
    }

}
