package com.binance.dex.api.client.domain.stake.sidechain;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Fitz.Lu
 **/
public class SideChainDelegate {

    private String delegatorAddress;

    private String validatorAddress;

    private Token delegation;

    private String sideChainId;

    public SideChainDelegate() {
        sideChainId = "";
    }

    public SideChainDelegate(String delegatorAddress, String validatorAddress, Token delegation, String sideChainId) {
        this.delegatorAddress = delegatorAddress;
        this.validatorAddress = validatorAddress;
        this.delegation = delegation;
        this.sideChainId = sideChainId;
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

    public Token getDelegation() {
        return delegation;
    }

    public void setDelegation(Token delegation) {
        this.delegation = delegation;
    }

    public String getSideChainId() {
        return sideChainId;
    }

    public void setSideChainId(String sideChainId) {
        this.sideChainId = sideChainId;
    }

    @Override
    public String toString() {
        return "SideChainDelegate{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", validatorAddress='" + validatorAddress + '\'' +
                ", delegation=" + delegation +
                ", sideChainId='" + sideChainId + '\'' +
                '}';
    }
}
