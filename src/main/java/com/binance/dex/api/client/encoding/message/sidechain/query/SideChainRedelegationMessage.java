package com.binance.dex.api.client.encoding.message.sidechain.query;

import com.binance.dex.api.client.encoding.message.common.CoinValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Fitz.Lu
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class SideChainRedelegationMessage {

    @JsonProperty(value = "delegator_addr")
    private String delegatorAddress;

    @JsonProperty(value = "validator_src_addr")
    private String srcValidatorAddress;

    @JsonProperty(value = "validator_dst_addr")
    private String dstValidatorAddress;

    @JsonProperty(value = "creation_height")
    private long createHeight;

    @JsonProperty(value = "min_time")
    private String minTime;

    @JsonProperty(value = "initial_balance")
    private CoinValue initialBalance;

    @JsonProperty(value = "balance")
    private CoinValue balance;

    @JsonProperty(value = "shares_src")
    private long srcShares;

    @JsonProperty(value = "shares_dst")
    private long dstShare;

    public SideChainRedelegationMessage() {
    }

    public String getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(String delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public String getSrcValidatorAddress() {
        return srcValidatorAddress;
    }

    public void setSrcValidatorAddress(String srcValidatorAddress) {
        this.srcValidatorAddress = srcValidatorAddress;
    }

    public String getDstValidatorAddress() {
        return dstValidatorAddress;
    }

    public void setDstValidatorAddress(String dstValidatorAddress) {
        this.dstValidatorAddress = dstValidatorAddress;
    }

    public long getCreateHeight() {
        return createHeight;
    }

    public void setCreateHeight(long createHeight) {
        this.createHeight = createHeight;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public CoinValue getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(CoinValue initialBalance) {
        this.initialBalance = initialBalance;
    }

    public CoinValue getBalance() {
        return balance;
    }

    public void setBalance(CoinValue balance) {
        this.balance = balance;
    }

    public long getSrcShares() {
        return srcShares;
    }

    public void setSrcShares(long srcShares) {
        this.srcShares = srcShares;
    }

    public long getDstShare() {
        return dstShare;
    }

    public void setDstShare(long dstShare) {
        this.dstShare = dstShare;
    }
}
