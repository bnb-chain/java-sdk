package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.message.common.Bech32AddressValue;
import com.binance.dex.api.client.encoding.serializer.Bech32AddressValueToStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Fitz.Lu
 **/
public class QueryValidatorParams {

    @JsonProperty(value = "SideChainId")
    private String sideChainId;

    @JsonProperty(value = "ValidatorAddr")
    @JsonSerialize(using = Bech32AddressValueToStringSerializer.class)
    private Bech32AddressValue ValidatorAddress;

    public QueryValidatorParams() {
    }

    public QueryValidatorParams(String sideChainId, Bech32AddressValue validatorAddress) {
        this.sideChainId = sideChainId;
        ValidatorAddress = validatorAddress;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public Bech32AddressValue getValidatorAddress() {
        return ValidatorAddress;
    }

    public void setValidatorAddress(Bech32AddressValue validatorAddress) {
        ValidatorAddress = validatorAddress;
    }
}
