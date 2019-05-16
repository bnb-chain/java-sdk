package com.binance.dex.api.client.domain.exception;

/**
 *
 * Created by fletcher on 2019/5/16.
 */
public class DexWSTimeoutException extends BinanceDexWSException {

    public DexWSTimeoutException(String id, String method, String message) {
        super(id, method, message);
    }

    public DexWSTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public DexWSTimeoutException(Throwable cause) {
        super(cause);
    }

    public DexWSTimeoutException(String message){
        super(message);
    }
}
