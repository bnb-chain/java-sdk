package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.*;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StakeMigration extends Content {

    private EthAddressValue operatorAddress;
    private EthAddressValue delegatorAddress;
    private UnsignedNumber amount;
    private Bech32AddressValue refundAddress;
    @Override
    protected void setHrp(String hrp) {
        refundAddress.setHrp(hrp);
    }
}
