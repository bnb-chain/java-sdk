package com.binance.dex.api.client.domain.exception;

import com.binance.dex.api.client.domain.jsonrpc.Error;

/**
 *
 * Created by fletcher on 2019/5/13.
 */
public class BinanceDexWSException extends RuntimeException {

    private String id;
    private String method;
    private Error error;

    public BinanceDexWSException(String id,String method,String message){
        super(String.format("id=%s,method=%s,error:%s",id,method,message));
        this.id = id;
        this.method = method;
    }

    public BinanceDexWSException(String id,String method,Error error){
        super(String.format("id=%s,method=%s,error:%s",id,method,error.toString()));
        this.id = id;
        this.method = method;
        this.error = error;
    }

    public BinanceDexWSException(String message,Throwable cause){
        super(message,cause);
    }

    public BinanceDexWSException(Throwable cause){
        super(cause);
    }

    public BinanceDexWSException(String message){
        super(message);
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
