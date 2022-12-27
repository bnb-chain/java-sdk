package com.binance.dex.api.client.domain.stake.beaconchain;

import com.binance.dex.api.client.domain.stake.Description;
import com.binance.dex.api.client.encoding.amino.types.PubKeyEd25519;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Francis.Liu
 **/
public class EditBeaconChainValidator {

    private Description description;

    private String validatorAddress;

    private long commissionRate;

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    private String pubKey;

    public EditBeaconChainValidator() {
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(String validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    public long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(long commissionRate) {
        this.commissionRate = commissionRate;
    }


    @Override
    public String toString() {
        return "EditSideChainValidator{" +
                "description=" + description + "\n" +
                ", validatorAddress='" + validatorAddress + '\'' + "\n" +
                ", commissionRate=" + commissionRate + "\n" +
                '}';
    }
}
