package com.binance.dex.api.client.crosschain;

import com.binance.dex.api.client.encoding.serializer.BytesToPrefixedHexStringSerializer;
import com.binance.dex.api.client.rlp.Decoder;
import com.binance.dex.api.client.rlp.RlpDecodable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatorSet implements RlpDecodable {

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] sideConsAddr;
    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] feeAddr;
    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] distAddr;
    private Long power;

    @Override
    public void decode(byte[] raw, Object superInstance) throws Exception {
        ValidatorSet vs = Decoder.decodeObject(raw, ValidatorSet.class);
        this.sideConsAddr = vs.sideConsAddr;
        this.feeAddr = vs.feeAddr;
        this.distAddr = vs.distAddr;
        this.power = vs.power;
    }
}
