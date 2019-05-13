package com.binance.dex.api.client.websocket;


import java.util.UUID;

/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class IdGenerator {

    protected String getId(String method){
        return method + "-" + UUID.randomUUID().toString().replaceAll("-","");
    }

}
