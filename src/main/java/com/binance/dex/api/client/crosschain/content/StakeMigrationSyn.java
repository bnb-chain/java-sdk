package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.*;
import com.binance.dex.api.client.crosschain.content.crossStakeSynParamsBytes.DelegateSynParamsBytes;
import com.binance.dex.api.client.crosschain.content.crossStakeSynParamsBytes.RedelegateSynParamsBytes;
import com.binance.dex.api.client.crosschain.content.crossStakeSynParamsBytes.UndelegateSynParamsBytes;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.binance.dex.api.client.rlp.Decoder;
import com.binance.dex.api.client.rlp.RlpDecodable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class StakeMigrationSyn extends Content {

    private EthAddressValue operatorAddress;
    private EthAddressValue delegatorAddress;
    private UnsignedNumber amount;
    private Bech32AddressValue refundAddress;
    @Override
    protected void setHrp(String hrp) {
        refundAddress.setHrp(hrp);
    }
}
