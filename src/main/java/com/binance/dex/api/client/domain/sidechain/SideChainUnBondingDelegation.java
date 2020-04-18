package com.binance.dex.api.client.domain.sidechain;

import com.binance.dex.api.client.encoding.message.Token;

import java.util.Arrays;

/**
 * @author Fitz.Lu
 **/
public class SideChainUnBondingDelegation {

    private byte[] delegatorAddress;

    private byte[] validatorAddress;

    private long createHeight;

    private long minTimeInMs;

    private Token initialBalance;

    private Token balance;

    public byte[] getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(byte[] delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public byte[] getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(byte[] validatorAddress) {
        this.validatorAddress = validatorAddress;
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

    @Override
    public String toString() {
        return "SideChainUnBondingDelegation{" +
                "delegatorAddress=" + Arrays.toString(delegatorAddress) +
                ", validatorAddress=" + Arrays.toString(validatorAddress) +
                ", createHeight=" + createHeight +
                ", minTimeInMs=" + minTimeInMs +
                ", initialBalance=" + initialBalance +
                ", balance=" + balance +
                '}';
    }
}
