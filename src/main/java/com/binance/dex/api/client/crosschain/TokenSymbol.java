package com.binance.dex.api.client.crosschain;

import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.serializer.TokenSymbolSerializer;
import com.binance.dex.api.client.rlp.RlpDecodable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize(using = TokenSymbolSerializer.class)
public class TokenSymbol implements RlpDecodable {

    private byte[] raw;
    private String symbol;

    @Override
    public void decode(byte[] raw, Object superInstance) {
        this.setRaw(raw);
        this.setSymbol(new String(ByteUtil.trim(raw,(byte)0)));
    }
}
