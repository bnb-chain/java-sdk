package com.binance.dex.api.client.websocket;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class WebsocketLauncher {

    public static boolean startUp(BinanceDexClientEndpoint endpoint){
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxBinaryMessageBufferSize(128000000);
            container.setDefaultMaxTextMessageBufferSize(128000000);
            container.connectToServer(endpoint,new URI(endpoint.getUrl()));
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
