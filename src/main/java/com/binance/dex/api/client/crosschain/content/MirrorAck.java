package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.TokenSymbol;
import com.binance.dex.api.client.crosschain.UnsignedInt;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MirrorAck extends Content {

    private EthAddressValue mirrorSender;
    private EthAddressValue contractAddr;
    private UnsignedInt decimals;
    private TokenSymbol symbol;
    private UnsignedNumber mirrorFee;
    private UnsignedInt errorCode;

    @Override
    protected void setHrp(String hrp) {

    }
}
