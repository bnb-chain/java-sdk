package com.binance.dex.api.client.crosschain.content.crossStakeSynParamsBytes;

import com.binance.dex.api.client.crosschain.content.CrossStakeSynParamsBytes;
import com.binance.dex.api.client.encoding.message.common.EthAddressValue;
import com.binance.dex.api.client.crosschain.UnsignedNumber;
import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.rlp.Decoder;
import com.binance.dex.api.client.rlp.RlpDecodable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DelegateSynParamsBytes extends CrossStakeSynParamsBytes implements RlpDecodable {

    private EthAddressValue delAddr;
    private Bech32AddressValue validator;
    private UnsignedNumber amount;

    @Override
    protected void setHrp(String hrp) {
        this.validator.setHrp(hrp);
    }

    @Override
    public void decode(byte[] raw, Object superInstance) throws Exception {
        DelegateSynParamsBytes vs = Decoder.decodeObject(raw, DelegateSynParamsBytes.class);
        this.delAddr = vs.delAddr;
        this.validator = vs.validator;
        this.amount = vs.amount;
    }
}
