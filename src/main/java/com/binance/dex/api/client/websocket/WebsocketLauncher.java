package com.binance.dex.api.client.websocket;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class WebsocketLauncher {

    public static boolean startUp(BinanceDexClientEndpoint endpoint){
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(endpoint,new URI(endpoint.getUrl()));
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
