package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Francis.Liu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class QueryRedelegationParams extends BaseQueryParams {

    @JsonProperty(value = "DelegatorAddr")
    private String delegatorAddr;

    @JsonProperty(value = "ValSrcAddr")
    private String validatorSrcAddr;

    @JsonProperty(value = "ValDstAddr")
    private String validatorDstAddr;

    public QueryRedelegationParams() {
    }

    public QueryRedelegationParams(String sideChainId, String delegatorAddr, String validatorSrcAddr, String validatorDstAddr) {
        super(sideChainId);
        this.delegatorAddr = delegatorAddr;
        this.validatorSrcAddr = validatorSrcAddr;
        this.validatorDstAddr = validatorDstAddr;
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

    public String getValidatorSrcAddr() {
        return validatorSrcAddr;
    }

    public void setValidatorSrcAddr(String validatorSrcAddr) {
        this.validatorSrcAddr = validatorSrcAddr;
    }

    public String getValidatorDstAddr() {
        return validatorDstAddr;
    }

    public void setValidatorDstAddr(String validatorDstAddr) {
        this.validatorDstAddr = validatorDstAddr;
    }

}

