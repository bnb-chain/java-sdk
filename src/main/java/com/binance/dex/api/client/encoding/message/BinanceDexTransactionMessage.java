package com.binance.dex.api.client.encoding.message;

public interface BinanceDexTransactionMessage {

    default boolean useAminoJson(){
        return false;
    }

    default void validateBasic(){}

}
