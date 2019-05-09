package com.binance.dex.api.client.websocket;

import javax.websocket.MessageHandler;
import java.util.HashMap;
import java.util.Map;

public class BinanceDexClientWSFactory {

    private static BinanceDexClientWSFactory instance = new BinanceDexClientWSFactory();

    private BinanceDexClientWSFactory(){}

    public static BinanceDexClientWSFactory getInstance(){
        return instance;
    }

    private final Map<String,DefaultMessageHandler> defaultMessageHandlerMap = new HashMap<>();

    public BinanceDexClientEndpoint newClientEndpoint(MessageHandler.Whole<String> messageHandler){
        return new BinanceDexClientEndpoint(messageHandler);
    }

    public BinanceDexClientEndpoint newDefaultClientEndpoint(String hrp){
        return new BinanceDexClientEndpoint(getDefaultMessageHandler(hrp));
    }

    private synchronized DefaultMessageHandler getDefaultMessageHandler(String hrp){
        if(defaultMessageHandlerMap.get(hrp) != null){
            return defaultMessageHandlerMap.get(hrp);
        }else{
            DefaultMessageHandler defaultMessageHandler = new DefaultMessageHandler(hrp);
            defaultMessageHandlerMap.put(hrp,defaultMessageHandler);
            return defaultMessageHandler;
        }
    }


}
