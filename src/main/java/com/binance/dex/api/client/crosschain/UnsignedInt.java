package com.binance.dex.api.client.crosschain;

public class UnsignedInt extends UnsignedNumber {

    public UnsignedInt(){
        super();
    }

    public int getValue(){
        return super.getNumber().intValue();
    }

}
