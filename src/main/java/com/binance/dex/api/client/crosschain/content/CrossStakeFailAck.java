package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.content.crossStakeFailAckParamsBytes.*;
import com.binance.dex.api.client.crosschain.content.crossStakeSynParamsBytes.DelegateSynParamsBytes;
import com.binance.dex.api.client.crosschain.content.crossStakeSynParamsBytes.RedelegateSynParamsBytes;
import com.binance.dex.api.client.rlp.RlpDecodable;
import com.binance.dex.api.client.rlp.Decoder;
import com.binance.dex.api.client.encoding.ByteUtil;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CrossStakeFailAck extends Content implements RlpDecodable {

    private CrossStakeFailAckParamsBytes paramsBytes;

    @Override
    protected void setHrp(String hrp) {
        this.paramsBytes.setHrp(hrp);
    }

    @Override
    public void decode(byte[] raw, Object superInstance) throws Exception {
        if (raw.length < 16) {
            throw new RuntimeException("Failed to code: too less length for cross stake syn package");
        }

        Class<? extends CrossStakeFailAckParamsBytes> clazz;
        CrossStakeFailAckParamsBytes instance;
        if ((int) ByteUtil.pick(raw, 1, 1)[0] == 4) {
            clazz = RewardFailAckParamsBytes.class;
            instance = Decoder.decodeObject(raw, clazz);
        } else if ((int) ByteUtil.pick(raw, 1, 1)[0] == 5) {
            clazz = UndelegatedFailAckParamsBytes.class;
            instance = Decoder.decodeObject(raw, clazz);
        } else {
            throw new RuntimeException("unknown event type of cross stake fail syn package");
        }
        this.setParamsBytes(instance);
    }
}
