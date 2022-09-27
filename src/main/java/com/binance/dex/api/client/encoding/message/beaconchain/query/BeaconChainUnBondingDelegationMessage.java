package com.binance.dex.api.client.encoding.message.beaconchain.query;

import com.binance.dex.api.client.domain.stake.beaconchain.BeaconChainUnBondingDelegation;
import com.binance.dex.api.client.domain.stake.sidechain.UnBondingDelegation;
import com.binance.dex.api.client.encoding.message.Token;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Francis.Liu
 **/
public class BeaconChainUnBondingDelegationMessage {
    @JsonProperty(value = "delegator_addr")
    private String delegatorAddress;
    @JsonProperty(value = "validator_addr")
    private String validatorAddress;
    @JsonProperty(value = "creation_height")
    private long createHeight;
    @JsonProperty(value = "min_time")
    private String minTime;
    @JsonProperty(value = "initial_balance")
    private Token initialBalance;
    @JsonProperty(value = "balance")
    private Token balance;

    @JsonProperty(value = "cross_stake")
    private boolean crossStake;

    public BeaconChainUnBondingDelegation toBeaconChainUnBondingDelegation() {
        BeaconChainUnBondingDelegation beaconChainRedelegation = new BeaconChainUnBondingDelegation(
                this.getDelegatorAddress(),
                this.getValidatorAddress(),
                this.getCreateHeight(),
                this.getMinTimeInMs(),
                this.getMinTime(),
                this.getInitialBalance(),
                this.getBalance()

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(this.minTime);
        } catch (ParseException e) {
            return 0;
        }
        return d.getTime();
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

    public boolean isCrossStake() {
        return crossStake;
    }

    public void setCrossStake(boolean crossStake) {
        this.crossStake = crossStake;
    }

    @Override
    public String toString() {
        return "UnBondingDelegation{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", validatorAddress='" + validatorAddress + '\'' +
                ", createHeight=" + createHeight +
                ", minTime='" + minTime + '\'' +
                ", initialBalance=" + initialBalance +
                ", balance=" + balance +
                '}';
    }
}
