package com.binance.dex.api.client.examples;

import com.binance.dex.api.client.*;
import com.binance.dex.api.client.domain.ws.AccountUpdateEvent;
import com.binance.dex.api.client.domain.ws.OrdersUpdateEvent;

public class StreamDataExample {
    public static void main(String[] args) {
        BinanceDexApiWebSocketClient client =
                BinanceDexApiClientFactory.newInstance().newWebSocketClient("wss://testnet-dex.binance.org/api");

        client.onOrderUpdateEvent("tbnb1l6vgk5yyxcalm06gdsg55ay4pjkfueazkvwh58", new WebSocketApiCallback<OrdersUpdateEvent>() {
            @Override
            public void onResponse(OrdersUpdateEvent response) throws Exception {
                System.out.println(response);
            }
        });

    }
}
