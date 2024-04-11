package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.*;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StakeMigrationRefund extends Content {

    private EthAddressValue operatorAddress;
    private EthAddressValue delegatorAddress;
    private Bech32AddressValue refundAddress;
    private UnsignedNumber amount;

    @Override
    protected void setHrp(String hrp) {
        this.refundAddress.setHrp(hrp);
    }
}
