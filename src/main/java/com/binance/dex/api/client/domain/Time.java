package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.joda.time.DateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Time {
    @JsonProperty("ap_time")
    private DateTime apTime;
    @JsonProperty("block_time")
    private DateTime blockTime;

    public DateTime getApTime() {
        return apTime;
    }

    public void setApTime(DateTime apTime) {
        this.apTime = apTime;
    }

    public DateTime getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(DateTime blockTime) {
        this.blockTime = blockTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("apTime", apTime)
                .append("blockTime", blockTime)
                .toString();
    }
}
