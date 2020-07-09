package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.TokenSymbol;
import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedInt;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransferOutRefund extends Content {

    private TokenSymbol symbol;
    private UnsignedNumber refundAmount;
    private Bech32AddressValue refundAddr;
    private UnsignedInt refundReason;


    @Override
    protected void setHrp(String hrp) {
        this.refundAddr.setHrp(hrp);
    }
}
