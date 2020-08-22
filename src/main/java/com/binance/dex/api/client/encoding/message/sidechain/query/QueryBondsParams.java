package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class QueryBondsParams extends BaseQueryParams {

    @JsonProperty(value = "DelegatorAddr")
    private String delegatorAddr;

    @JsonProperty(value = "ValidatorAddr")
    private String validatorAddr;

    public QueryBondsParams() {
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    public String getDelegatorAddr() {
        return delegatorAddr;
    }

    public void setDelegatorAddr(String delegatorAddr) {
        this.delegatorAddr = delegatorAddr;
    }

    public String getValidatorAddr() {
        return validatorAddr;
    }

    public void setValidatorAddr(String validatorAddr) {
        this.validatorAddr = validatorAddr;
    }
}
