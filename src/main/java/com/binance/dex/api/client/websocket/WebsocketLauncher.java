package com.binance.dex.api.client.websocket;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class WebsocketLauncher {

    public static void startUp(BinanceDexClientEndpoint endpoint,String uri){
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(endpoint,new URI(uri));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
