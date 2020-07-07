package com.binance.dex.api.client.crosschain.content;

import com.binance.dex.api.client.crosschain.Content;
import com.binance.dex.api.client.crosschain.UnsignedInt;
import com.binance.dex.api.client.crosschain.ValidatorSet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IbcValidatorSet extends Content {

    private UnsignedInt type;
    private List<ValidatorSet> ibcValidator;

    @Override
    protected void setHrp(String hrp) {

    }
}
