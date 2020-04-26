package com.binance.dex.api.client.domain.stake.sidechain;


import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class SideChainRedelegation {

    //delegator address
    private String delegatorAddress;

    //validator redelegation source operator address
    private String srcValidatorAddress;

    //validator redelegation destination operator address
    private String dstValidatorAddress;

    //height which the redelegation took place
    private long createHeight;

    //unix time for redelegation completion
    private String minTime;

    private long minTimeInMs;

    //initial balance when redelegation started
    private Token initialBalance;

    //current balance
    private Token balance;

    //amount of source shares redelegating
    private long srcShares;

    //amount of destination shares redelegating
    private long dstShare;

    public SideChainRedelegation() {
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

    public long getMinTimeInMs() {
        return minTimeInMs;
    }

    public void setMinTimeInMs(long minTimeInMs) {
        this.minTimeInMs = minTimeInMs;
    }

    public Token getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Token initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Token getBalance() {
        return balance;
    }

    public void setBalance(Token balance) {
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

    @Override
    public String toString() {
        return "SideChainRedelegation{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", srcValidatorAddress='" + srcValidatorAddress + '\'' +
                ", dstValidatorAddress='" + dstValidatorAddress + '\'' +
                ", createHeight=" + createHeight +
                ", minTime='" + minTime + '\'' +
                ", minTimeInMs=" + minTimeInMs +
                ", initialBalance=" + initialBalance +
                ", balance=" + balance +
                ", srcShares=" + srcShares +
                ", dstShare=" + dstShare +
                '}';
    }
}
