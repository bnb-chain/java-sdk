package com.binance.dex.api.client.crosschain;

import com.binance.dex.api.client.encoding.serializer.UnsignedNumberSerializer;
import com.binance.dex.api.client.rlp.RlpDecodable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigInteger;

@JsonSerialize(using = UnsignedNumberSerializer.class)
public class UnsignedNumber implements RlpDecodable {

    private BigInteger value;

    public UnsignedNumber() {
        this.value = BigInteger.ZERO;
    }

    public UnsignedNumber(BigInteger value) {
        this.value = value;
    }

    @Override
    public void decode(byte[] raw, Object superInstance) {
        this.value = new BigInteger(1, raw);
    }

    public BigInteger getNumber() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }
}
