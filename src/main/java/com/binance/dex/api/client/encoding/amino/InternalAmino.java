package com.binance.dex.api.client.encoding.amino;

/**
 * @author Fitz.Lu
 **/
class InternalAmino {

    static final Amino ins = new Amino();

    static Amino get(){
        return ins;
    }

}