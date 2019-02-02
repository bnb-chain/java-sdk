package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Time {
    @JsonProperty("ap_time")
    private ZonedDateTime apTime;
    @JsonProperty("block_time")
    private ZonedDateTime blockTime;

    public ZonedDateTime getApTime() {
        return apTime;
    }

    public void setApTime(ZonedDateTime apTime) {
        this.apTime = apTime;
    }

    public ZonedDateTime getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(ZonedDateTime blockTime) {
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
