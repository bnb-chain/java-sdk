package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;

@ClientEndpoint(decoders = {MessageDecoder.class})
public class BinanceDexClientEndpoint<T> {

    private Logger logger = LoggerFactory.getLogger(BinanceDexClientEndpoint.class);

    private Session userSession;

    private BinanceDexMessageHandler<T> messageHandler;

    private String url;

    public BinanceDexClientEndpoint(BinanceDexMessageHandler<T> messageHandler,String url){
        this.messageHandler = messageHandler;
        this.url = url;
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession){
        logger.info("===>Successfully establish websocket connection...");
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
        logger.warn("WebSocket connection closed!,reason = {}.try reconnecting...",reason.toString());
        this.userSession = null;
        reconnect();
    }
    private void reconnect(){
        boolean success;
        int count = 1;
        do {
            try{
                logger.info("reconnecting round {}...",count);
                WebsocketLauncher.startUp(this);
                success = true;
                logger.info("WebSocket reconnect successfully!");
            }catch (Exception e){
                count++;
                success = false;
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        }while (!success);

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


    public String getUrl() {
        return url;
    }
}
