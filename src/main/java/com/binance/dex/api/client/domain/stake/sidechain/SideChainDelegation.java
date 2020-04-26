package com.binance.dex.api.client.domain.stake.sidechain;

import java.util.Arrays;

/**
 * @author Fitz.Lu
 **/
public class SideChainDelegation {

    private String delegatorAddress;

    private String validatorAddress;

    private long shares;

    private long height;

    public SideChainDelegation() {
    }

    public SideChainDelegation(String delegatorAddress, String validatorAddress, long shares, long height) {
        this.delegatorAddress = delegatorAddress;
        this.validatorAddress = validatorAddress;
        this.shares = shares;
        this.height = height;
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

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "SideChainDelegation{" +
                "delegatorAddress=" + delegatorAddress +
                ", validatorAddress=" + validatorAddress +
                ", shares=" + shares +
                ", height=" + height +
                '}';
    }
}
