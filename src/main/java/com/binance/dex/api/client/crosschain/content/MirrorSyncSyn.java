package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.TokenSymbol;
import com.binance.dex.api.client.crosschain.UnsignedLong;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MirrorSyncSyn extends Content {

    private EthAddressValue syncSender;
    private EthAddressValue contractAddr;
    private TokenSymbol symbol;
    private UnsignedNumber totalSupply;
    private UnsignedNumber syncFee;
    private UnsignedLong expireTime;

    @Override
    protected void setHrp(String hrp) {

    }
}
