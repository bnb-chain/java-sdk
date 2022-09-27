package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Francis.Liu
 **/
public class BeaconChainRedelegate {

    private String delegatorAddress;

    private String srcValidatorAddress;

    private String dstValidatorAddress;

    private Token amount;


    public BeaconChainRedelegate() {
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

    public Token getAmount() {
        return amount;
    }

    public void setAmount(Token amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SideChainRedelegate{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", srcValidatorAddress='" + srcValidatorAddress + '\'' +
                ", dstValidatorAddress='" + dstValidatorAddress + '\'' +
                ", amount=" + amount +
                '}';
    }
}
