package com.binance.dex.api.client;


public class BNBDexApiException extends RuntimeException {
    private static final long serialVersionUID = 3788669840036201041L;
    private BNBDexApiError error;

    public BNBDexApiException(BNBDexApiError error) {
        this.error = error;
    }

    public BNBDexApiException(Throwable cause) {
        super(cause);
    }

    public BNBDexApiException(int code,String message){
        BNBDexApiError apiError = new BNBDexApiError();
        apiError.setCode(code);
        apiError.setMessage(message);
        this.error = apiError;
    }

    public BNBDexApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BNBDexApiError getError() {
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
