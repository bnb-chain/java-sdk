package com.binance.dex.api.client.domain.sidechain;

import java.util.Arrays;

/**
 * @author Fitz.Lu
 **/
public class SideChainDelegation {

    private byte[] delegatorAddress;

    private byte[] validatorAddress;

    private long shares;

    private long height;

    public SideChainDelegation() {
    }

    public SideChainDelegation(byte[] delegatorAddress, byte[] validatorAddress, long shares, long height) {
        this.delegatorAddress = delegatorAddress;
        this.validatorAddress = validatorAddress;
        this.shares = shares;
        this.height = height;
    }

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
                "delegatorAddress=" + Arrays.toString(delegatorAddress) +
                ", validatorAddress=" + Arrays.toString(validatorAddress) +
                ", shares=" + shares +
                ", height=" + height +
                '}';
    }
}
