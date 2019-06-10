package com.binance.dex.api.client.websocket;

import com.binance.dex.api.client.domain.jsonrpc.JsonRpcResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WSResponseCache extends LinkedHashMap<String, JsonRpcResponse> {

    private static final WSResponseCache instance = new WSResponseCache();
    public static WSResponseCache instance(){
        return instance;
    }

    private static final long DEFAULT_MAX_ENTRIES = 10000L;
    private final long max_entries;
    private final Lock addLock = new ReentrantLock();

    private WSResponseCache(){
        super();
        max_entries = DEFAULT_MAX_ENTRIES;
    }

    public void add(JsonRpcResponse response){
        if(response == null || StringUtils.isEmpty(response.getId())){
            return;
        }
        addLock.lock();
        try{
            this.put(response.getId(),response);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            addLock.unlock();
        }
    }

    /**
     * remove entry once fetched by client
     * @param id
     * @return result
     */
    public JsonRpcResponse get(String id){
        JsonRpcResponse response = super.get(id);
        if(response != null){
            super.remove(id);
        }
        return response;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<String,JsonRpcResponse> eldest) {
        return this.size() > max_entries;
    }

}
