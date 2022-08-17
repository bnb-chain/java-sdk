package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedInt;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrossStakeRefund extends Content {

    private UnsignedInt eventType;
    private UnsignedNumber amount;
    private EthAddressValue recipient;
    private UnsignedInt errorCode;

    @Override
    protected void setHrp(String hrp) {

    }
}
