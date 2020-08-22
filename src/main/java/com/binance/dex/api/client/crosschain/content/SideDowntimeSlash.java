package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedInt;
import com.binance.dex.api.client.crosschain.UnsignedLong;
import com.binance.dex.api.client.encoding.serializer.BytesToPrefixedHexStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SideDowntimeSlash extends Content {

    @JsonSerialize(using = BytesToPrefixedHexStringSerializer.class)
    private byte[] sideConsAddr;
    private UnsignedLong sideHeight;
    private UnsignedInt sideChainId;
    private UnsignedLong sideTimestamp;

    @Override
    protected void setHrp(String hrp) {}
}
