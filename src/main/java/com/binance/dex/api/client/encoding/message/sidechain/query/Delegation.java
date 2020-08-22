package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Delegation {

    @JsonProperty(value = "delegator_addr")
    private String delegatorAddress;

    @JsonProperty(value = "validator_addr")
    private String validatorAddress;

    @JsonProperty(value = "shares")
    private long shares;

    public Delegation() {
    }

    public Delegation(String delegatorAddress, String validatorAddress, long shares) {
        this.delegatorAddress = delegatorAddress;
        this.validatorAddress = validatorAddress;
        this.shares = shares;
    }

    public String getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(String delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public String getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(String validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }
}
