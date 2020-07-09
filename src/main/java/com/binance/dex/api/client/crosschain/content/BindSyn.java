package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.*;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BindSyn extends Content {

    private UnsignedInt packageType;
    private TokenSymbol symbol;
    private EthAddressValue contractAddr;
    private UnsignedNumber totalSupply;
    private UnsignedNumber peggyAmount;
    private UnsignedInt decimals;
    private UnsignedLong expireTime;

    @Override
    protected void setHrp(String hrp) {
    }
}
