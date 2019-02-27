package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DexFeeField {
    @JsonProperty("fee_name")
    private String feeName;
    @JsonProperty("fee_value")
    private long feeValue;

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public long getFeeValue() {
        return feeValue;
    }

    public void setFeeValue(long feeValue) {
        this.feeValue = feeValue;
    }
}
