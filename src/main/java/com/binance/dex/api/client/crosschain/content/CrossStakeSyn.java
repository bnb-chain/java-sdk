package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.content.crossStakeSynParamsBytes.*;
import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.rlp.Decoder;
import com.binance.dex.api.client.rlp.RlpDecodable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CrossStakeSyn extends Content implements RlpDecodable {

    private Integer eventType;
    private CrossStakeSynParamsBytes paramsBytes;

    public CrossStakeSyn(){}

    @Override
    protected void setHrp(String hrp) {
        this.paramsBytes.setHrp(hrp);
    }

    @Override
    public void decode(byte[] raw, Object superInstance) throws Exception {
        if (raw.length < 27) {
            throw new RuntimeException("Failed to code: too less length for cross stake syn package");
        }

        Class<? extends CrossStakeSynParamsBytes> clazz;
        CrossStakeSynParamsBytes instance;
        if ((int) ByteUtil.pick(raw, 1, 1)[0] == 1) {
            this.setEventType((int) ByteUtil.pick(raw, 1, 1)[0]);
            clazz = DelegateSynParamsBytes.class;
            instance = Decoder.decodeObject(ByteUtil.cut(raw, 3), clazz);
        } else if ((int) ByteUtil.pick(raw, 1, 1)[0] == 2) {
            this.setEventType((int) ByteUtil.pick(raw, 1, 1)[0]);
            clazz = UndelegateSynParamsBytes.class;
            instance = Decoder.decodeObject(ByteUtil.cut(raw, 3), clazz);
        } else if ((int) ByteUtil.pick(raw, 2, 1)[0] == 3) {
            this.setEventType((int) ByteUtil.pick(raw, 2, 1)[0]);
            clazz = RedelegateSynParamsBytes.class;
            instance = Decoder.decodeObject(ByteUtil.cut(raw, 5), clazz);
        } else {
            throw new RuntimeException(String.format("unknown event type of cross stake syn package, package type = %s",
                    this.getEventType()));
        }
        this.setParamsBytes(instance);
    }
}
