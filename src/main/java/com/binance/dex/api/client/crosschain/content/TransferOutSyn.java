package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.TokenSymbol;
import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedLong;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransferOutSyn extends Content {

    private TokenSymbol symbol;
    private Bech32AddressValue contractAddress;
    private UnsignedNumber amount;
    private Bech32AddressValue recipient;
    private Bech32AddressValue refundAddress;
    private UnsignedLong expireTime;

    @Override
    protected void setHrp(String hrp) {
        this.refundAddress.setHrp(hrp);
    }
}
