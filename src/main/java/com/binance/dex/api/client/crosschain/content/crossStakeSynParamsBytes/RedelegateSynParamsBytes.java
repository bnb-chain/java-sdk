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
public class RedelegateSynParamsBytes extends CrossStakeSynParamsBytes implements RlpDecodable {

    private EthAddressValue delAddr;
    private Bech32AddressValue valSrc;
    private Bech32AddressValue valDst;
    private UnsignedNumber amount;

    @Override
    protected void setHrp(String hrp) {
        this.valSrc.setHrp(hrp);
        this.valDst.setHrp(hrp);
    }

    @Override
    public void decode(byte[] raw, Object superInstance) throws Exception {
        RedelegateSynParamsBytes vs = Decoder.decodeObject(raw, RedelegateSynParamsBytes.class);
        this.delAddr = vs.delAddr;
        this.valSrc = vs.valSrc;
        this.valDst = vs.valDst;
        this.amount = vs.amount;
    }
}
