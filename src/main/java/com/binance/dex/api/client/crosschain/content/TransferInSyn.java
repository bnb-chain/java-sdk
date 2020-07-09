package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.TokenSymbol;
import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedLong;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransferInSyn extends Content {

    private TokenSymbol symbol;
    private EthAddressValue contractAddress;
    private List<UnsignedNumber> amounts;
    private List<Bech32AddressValue> receiverAddresses;
    private List<EthAddressValue> refundAddresses;
    private UnsignedLong expireTime;

    @Override
    protected void setHrp(String hrp) {
        if (receiverAddresses != null) {
            receiverAddresses.forEach(addr -> addr.setHrp(hrp));
        }
    }
}
