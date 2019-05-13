package com.binance.dex.api.client.domain.exception;

/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class BinanceDexWSException extends RuntimeException {

    private String id;
    private String method;

    public BinanceDexWSException(String id,String method,String message){
        super(String.format("id=%s,method=%s,error:%s",id,method,message));
        this.id = id;
        this.method = method;
    }

    public BinanceDexWSException(String message,Throwable cause){
        super(message,cause);
    }

    public BinanceDexWSException(Throwable cause){
        super(cause);
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }
}
