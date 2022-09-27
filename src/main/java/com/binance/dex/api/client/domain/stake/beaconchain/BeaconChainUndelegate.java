package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Francis.Liu
 **/
public class BeaconChainUndelegate {

    private String delegatorAddress;

    private String validatorAddress;

    private Token amount;


    public BeaconChainUndelegate() {
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

    public Token getAmount() {
        return amount;
    }

    public void setAmount(Token amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "SideChainUnBond{" +
                "delegatorAddress='" + delegatorAddress + '\'' +
                ", validatorAddress='" + validatorAddress + '\'' +
                ", amount=" + amount +
                '}';
    }
}
