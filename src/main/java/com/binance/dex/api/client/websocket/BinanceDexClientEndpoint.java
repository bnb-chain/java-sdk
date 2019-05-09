package com.binance.dex.api.client.websocket;

import javax.websocket.*;

@ClientEndpoint
public class BinanceDexClientEndpoint {

    private Session userSession;

    private MessageHandler.Whole<String> messageHandler;

    public BinanceDexClientEndpoint(MessageHandler.Whole<String> messageHandler){
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
    public void onMessage(String response){
        if(messageHandler != null){
            messageHandler.onMessage(response);
        }
    }

    public void sendMessage(String message){
        if(null != userSession){
            userSession.getAsyncRemote().sendText(message);
        }
    }






}
