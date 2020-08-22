package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.encoding.serializer.BytesToPrefixedHexStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CSCParamChange extends Content {

    private String key;
    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] value;
    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] target;

    @Override
    protected void setHrp(String hrp) {

    }
}
