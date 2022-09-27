package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.domain.stake.sidechain.UnBondingDelegation;
import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Francis.Liu
 **/
public class BeaconChainUnBondingDelegation {

    private String delegatorAddress;

    private String validatorAddress;

    private long createHeight;

    private long minTimeInMs;

    private String minTime;

    private Token initialBalance;

    private Token balance;

    public BeaconChainUnBondingDelegation(String delegatorAddress, String validatorAddress, long createHeight, long minTimeInMs, String minTime, Token initialBalance, Token balance) {
        this.delegatorAddress = delegatorAddress;
        this.validatorAddress = validatorAddress;
        this.createHeight = createHeight;
        this.minTimeInMs = minTimeInMs;
        this.minTime = minTime;
        this.initialBalance = initialBalance;
        this.balance = balance;
    }

    public static BeaconChainUnBondingDelegation createByUnBondingDelegation(UnBondingDelegation unBondingDelegation) {
        BeaconChainUnBondingDelegation beaconChainRedelegation = new BeaconChainUnBondingDelegation(
                unBondingDelegation.getDelegatorAddress(),
                unBondingDelegation.getValidatorAddress(),
                unBondingDelegation.getCreateHeight(),
                unBondingDelegation.getMinTimeInMs(),
                unBondingDelegation.getMinTime(),
                unBondingDelegation.getInitialBalance(),
                unBondingDelegation.getBalance()

        );
        return beaconChainRedelegation;
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

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
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
        return "UnBondingDelegation{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", validatorAddress='" + validatorAddress + '\'' +
                ", createHeight=" + createHeight +
                ", minTimeInMs=" + minTimeInMs +
                ", minTime='" + minTime + '\'' +
                ", initialBalance=" + initialBalance +
                ", balance=" + balance +
                '}';
    }
}
