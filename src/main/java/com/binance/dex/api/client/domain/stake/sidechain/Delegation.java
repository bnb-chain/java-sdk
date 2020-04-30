package com.binance.dex.api.client.domain.stake.sidechain;

/**
 * @author Fitz.Lu
 **/
public class Delegation {

    private String delegatorAddress;

    private String validatorAddress;

    private long shares;

    public Delegation() {
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
