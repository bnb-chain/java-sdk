package com.binance.dex.api.client.encoding.amino;

/**
 * @author Fitz.Lu
 **/
public class AminoDecodeException extends RuntimeException {

    public AminoDecodeException() {
    }

    public AminoDecodeException(String message) {
        super(message);
    }

    public AminoDecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AminoDecodeException(Throwable cause) {
        super(cause);
    }

    public AminoDecodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
