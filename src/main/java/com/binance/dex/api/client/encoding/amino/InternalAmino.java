package com.binance.dex.api.client.encoding.amino;

/**
 * @author Fitz.Lu
 **/
public class InternalAmino {

    private static final Amino ins = new Amino();

    public static Amino get(){
        return ins;
    }

}