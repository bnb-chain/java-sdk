package com.binance.dex.api.client.crosschain;

public class UnsignedLong extends UnsignedNumber {

    public UnsignedLong(){
        super();
    }

    public long getValue(){
        return super.getNumber().longValue();
    }

}
