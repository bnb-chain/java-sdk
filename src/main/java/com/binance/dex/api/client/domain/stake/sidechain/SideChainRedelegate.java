package com.binance.dex.api.client.domain.stake.sidechain;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class SideChainRedelegate {

    private String delegatorAddress;

    private String srcValidatorAddress;

    private String dstValidatorAddress;

    private Token amount;

    private String sideChainId;

    public SideChainRedelegate() {
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

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    @Override
    public String toString() {
        return "SideChainRedelegate{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", srcValidatorAddress='" + srcValidatorAddress + '\'' +
                ", dstValidatorAddress='" + dstValidatorAddress + '\'' +
                ", amount=" + amount +
                ", sideChainId='" + sideChainId + '\'' +
                '}';
    }
}
