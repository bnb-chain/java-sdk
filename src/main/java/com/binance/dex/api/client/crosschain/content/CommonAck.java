package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedInt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonAck extends Content {

    private UnsignedInt code;

    @Override
    protected void setHrp(String hrp) {

    }
}
