package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.TokenSymbol;
import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedInt;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApproveBindSyn extends Content {

    private UnsignedInt status;

    private TokenSymbol symbol;

    @Override
    protected void setHrp(String hrp) {

    }
}
