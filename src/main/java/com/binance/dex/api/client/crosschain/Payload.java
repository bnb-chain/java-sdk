package com.binance.dex.api.client.crosschain;

import com.binance.dex.api.client.encoding.ByteUtil;
import com.binance.dex.api.client.rlp.Decoder;
import com.binance.dex.api.client.rlp.RlpDecodable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class Payload implements RlpDecodable {

    private Integer packageType;
    private BigInteger crossChainFee;
    private Content content;

    public Payload(){}

    @Override
    public void decode(byte[] raw, Object superInstance) throws Exception {
        if (raw.length < 33) {
            throw new RuntimeException("Failed to code: too less length for payload");
        }
        this.setPackageType((int) ByteUtil.pick(raw, 0, 1)[0]);
        this.setCrossChainFee(new BigInteger(ByteUtil.pick(raw, 1, 32)));

        Integer channelId;
        if (superInstance instanceof Package) {
            Package pack = (Package) superInstance;
            channelId = pack.getChannelId().getValue();
        } else {
            throw new RuntimeException("Failed to code: superInstance's class should be Package");
        }

        Class<? extends Content> clazz = ContentEnum.getClass(channelId, this.getPackageType());
        if (clazz == null) {
            throw new RuntimeException(String.format("unknown content of channel id = %s, package type = %s", channelId, this.getPackageType()));
        }
        Content instance = Decoder.decodeObject(ByteUtil.cut(raw, 33), clazz);
        this.setContent(instance);
    }
}
