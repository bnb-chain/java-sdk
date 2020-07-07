package com.binance.dex.api.client.crosschain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Package {
    private UnsignedInt channelId;
    private UnsignedLong sequence;
    private Payload payload;

    public void setHrp(String hrp) {
        payload.getContent().setHrp(hrp);
    }
}
