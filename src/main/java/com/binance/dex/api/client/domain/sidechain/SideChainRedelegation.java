package com.binance.dex.api.client.domain.sidechain;


import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class SideChainRedelegation {

    //delegator address
    private byte[] delegatorAddress;

    //validator redelegation source operator address
    private byte[] srcValidatorAddress;

    //validator redelegation destination operator address
    private byte[] dstValidatorAddress;

    //height which the redelegation took place
    private long createHeight;

    //unix time for redelegation completion
    private long minTimeInMs;

    //initial balance when redelegation started
    private Token initialBalance;

    //current balance
    private Token balance;

    //amount of source shares redelegating
    private long srcShares;

    //amount of destination shares redelegating
    private long dstShare;

    public byte[] getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(byte[] delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public byte[] getSrcValidatorAddress() {
        return srcValidatorAddress;
    }

    public void setSrcValidatorAddress(byte[] srcValidatorAddress) {
        this.srcValidatorAddress = srcValidatorAddress;
    }

    public byte[] getDstValidatorAddress() {
        return dstValidatorAddress;
    }

    public void setDstValidatorAddress(byte[] dstValidatorAddress) {
        this.dstValidatorAddress = dstValidatorAddress;
    }

    public long getCreateHeight() {
        return createHeight;
    }

    public void setCreateHeight(long createHeight) {
        this.createHeight = createHeight;
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
}
