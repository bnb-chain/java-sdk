package com.binance.dex.api.client.encoding.message.beaconchain.query;


import com.binance.dex.api.client.domain.stake.beaconchain.BeaconChainRedelegation;
import com.binance.dex.api.client.domain.stake.sidechain.SideChainRedelegation;
import com.binance.dex.api.client.encoding.message.Token;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Francis.Liu
 **/
public class BeaconChainRedelegationMessage {

    //delegator address
    @JsonProperty(value = "delegator_addr")
    private String delegatorAddress;

    //validator redelegation source operator address
    @JsonProperty(value = "validator_src_addr")
    private String srcValidatorAddress;

    //validator redelegation destination operator address
    @JsonProperty(value = "validator_dst_addr")
    private String dstValidatorAddress;

    //height which the redelegation took place
    @JsonProperty(value = "creation_height")
    private long createHeight;

    //unix time for redelegation completion
    @JsonProperty(value = "min_time")
    private String minTime;

    //initial balance when redelegation started
    @JsonProperty(value = "initial_balance")
    private Token initialBalance;

    //current balance
    @JsonProperty(value = "balance")
    private Token balance;

    //amount of source shares redelegating
    @JsonProperty(value = "shares_src")
    private long srcShares;

    //amount of destination shares redelegating
    @JsonProperty(value = "shares_dst")
    private long dstShare;

    public BeaconChainRedelegationMessage() {
    }

    public BeaconChainRedelegation toBeaconChainRedelegation(){
        BeaconChainRedelegation redelegation = new BeaconChainRedelegation(this.getDelegatorAddress(),
                this.getSrcValidatorAddress(),
                this.getDstValidatorAddress(),
                this.getCreateHeight(),
                this.getMinTime(),
                this.getMinTimeInMs(),
                this.getInitialBalance(),
                this.getBalance(),
                this.getSrcShares(),
                this.getDstShare()
        );
        return redelegation;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(this.minTime);
        } catch (ParseException e) {
            return 0;
        }
        return d.getTime();
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
                ", initialBalance=" + initialBalance +
                ", balance=" + balance +
                ", srcShares=" + srcShares +
                ", dstShare=" + dstShare +
                '}';
    }
}
