package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.*;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MirrorSyn extends Content {

    private EthAddressValue mirrorSender;
    private EthAddressValue contractAddr;
    private TokenSymbol name;
    private TokenSymbol symbol;
    private UnsignedNumber totalSupply;
    private UnsignedInt decimals;
    private UnsignedNumber mirrorFee;
    private UnsignedLong expireTime;

    @Override
    protected void setHrp(String hrp) {

    }
}
