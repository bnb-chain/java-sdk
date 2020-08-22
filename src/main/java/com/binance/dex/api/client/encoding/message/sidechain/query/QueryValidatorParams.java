package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Fitz.Lu
 **/
public class QueryValidatorParams extends BaseQueryParams {

    @JsonProperty(value = "ValidatorAddr")
    private Bech32AddressValue ValidatorAddress;

    public QueryValidatorParams() {
    }

    public QueryValidatorParams(String sideChainId, Bech32AddressValue validatorAddress) {
        this.sideChainId = sideChainId;
        ValidatorAddress = validatorAddress;
    }

    public Bech32AddressValue getValidatorAddress() {
        return ValidatorAddress;
    }

    public void setValidatorAddress(Bech32AddressValue validatorAddress) {
        ValidatorAddress = validatorAddress;
    }
}
