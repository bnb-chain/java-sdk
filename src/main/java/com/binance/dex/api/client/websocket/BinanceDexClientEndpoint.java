package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;

import javax.websocket.*;

@ClientEndpoint(decoders = {MessageDecoder.class})
public class BinanceDexClientEndpoint<T> {

    private Session userSession;

    private BinanceDexMessageHandler<T> messageHandler;

    public BinanceDexClientEndpoint(BinanceDexMessageHandler<T> messageHandler){
        this.messageHandler = messageHandler;
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession){
        System.out.println("open session");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason){
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(JsonRpcResponse response){
        if(messageHandler != null){
            messageHandler.onMessage(response);
        }
    }

    public T sendMessage(String id,String message){
        return messageHandler.send(userSession,id,message);
    }


}
