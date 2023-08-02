package com.binance.dex.api.client;


public class BinanceDexApiException extends RuntimeException {
    private static final long serialVersionUID = 3788669840036201041L;
    private BinanceDexApiError error;

    public BinanceDexApiException(BinanceDexApiError error) {
        this.error = error;
    }

    public BinanceDexApiException(Throwable cause) {
        super(cause);
    }

    public BinanceDexApiException(int code,String message){
        BinanceDexApiError apiError = new BinanceDexApiError();
        apiError.setCode(code);
        apiError.setMessage(message);
        this.error = apiError;
    }

    public BinanceDexApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BinanceDexApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        if (error != null) {
            return error.toString();
        }
        return super.getMessage();
    }
}
