package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.domain.stake.Commission;
import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.encoding.message.Token;

/**
 * @author Francis.Liu
 **/
public class CreateBeaconChainValidator {

    private Description description;

    private Commission commission;

    private String delegatorAddr;

    private String validatorAddr;

    private Token delegation;

    private String pubKey;


    public CreateBeaconChainValidator() {
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public String getDelegatorAddr() {
        return delegatorAddr;
    }

    public void setDelegatorAddr(String delegatorAddr) {
        this.delegatorAddr = delegatorAddr;
    }

    public Token getDelegation() {
        return delegation;
    }

    public void setDelegation(Token delegation) {
        this.delegation = delegation;
    }
    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getValidatorAddr() {
        return validatorAddr;
    }

    public void setValidatorAddr(String validatorAddr) {
        this.validatorAddr = validatorAddr;
    }

    @Override
    public String toString() {
        return "CreateSideChainValidator{" + '\n' +
                "description=" + description + '\n' +
                ", commission=" + commission + '\n' +
                ", delegatorAddr='" + delegatorAddr + '\'' + '\n' +
                ", validatorAddr='" + validatorAddr + '\'' + '\n' +
                ", delegation=" + delegation + '\n' +
                ", pubKey='" + pubKey + '\'' + '\n' +
                '}';
    }
}
