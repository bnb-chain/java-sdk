package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Francis.Liu
 **/
public class BeaconChainDelegate {

    private String delegatorAddress;

    private String validatorAddress;

    private Token delegation;


    public BeaconChainDelegate() {
    }

    public BeaconChainDelegate(String delegatorAddress, String validatorAddress, Token delegation, String sideChainId) {
        this.delegatorAddress = delegatorAddress;
        this.validatorAddress = validatorAddress;
        this.delegation = delegation;
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


    @Override
    public String toString() {
        return "SideChainDelegate{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", validatorAddress='" + validatorAddress + '\'' +
                ", delegation=" + delegation +
                '}';
    }
}
