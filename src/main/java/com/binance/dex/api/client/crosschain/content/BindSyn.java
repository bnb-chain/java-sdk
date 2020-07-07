package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.*;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.binance.dex.api.client.encoding.serializer.Bep2TokenSymbolSerializer;
import com.binance.dex.api.client.encoding.serializer.BytesToPrefixedHexStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class BindSyn extends Content {

    private UnsignedInt packageType;
    private Bep2TokenSymbol symbol;
    private EthAddressValue contractAddr;
    private UnsignedNumber totalSupply;
    private UnsignedNumber peggyAmount;
    private UnsignedInt decimals;
    private UnsignedLong expireTime;

    @Override
    protected void setHrp(String hrp) {
    }
}
