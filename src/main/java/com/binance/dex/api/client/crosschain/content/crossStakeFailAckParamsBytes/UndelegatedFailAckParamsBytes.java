package com.binance.dex.api.client.crosschain.content.crossStakeFailAckParamsBytes;

import com.binance.dex.api.client.crosschain.content.CrossStakeFailAckParamsBytes;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UndelegatedFailAckParamsBytes extends CrossStakeFailAckParamsBytes {

    private Integer eventType;
    private UnsignedNumber amount;
    private EthAddressValue recipient;
    private Bech32AddressValue validator;

    @Override
    protected void setHrp(String hrp) {
        this.validator.setHrp(hrp);
    }
}
