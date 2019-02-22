package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.joda.time.DateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncInfo {
    @JsonProperty("latest_block_hash")
    private String latestBlockHash;
    @JsonProperty("latest_app_hash")
    private String latestAppHash;
    @JsonProperty("latest_block_height")
    private Long latestBlockHeight;
    @JsonProperty("latest_block_time")
    private DateTime latestBlockTime;
    @JsonProperty("catching_up")
    private Boolean catchingUp;

    public String getLatestBlockHash() {
        return latestBlockHash;
    }

    public void setLatestBlockHash(String latestBlockHash) {
        this.latestBlockHash = latestBlockHash;
    }

    public String getLatestAppHash() {
        return latestAppHash;
    }

    public void setLatestAppHash(String latestAppHash) {
        this.latestAppHash = latestAppHash;
    }

    public Long getLatestBlockHeight() {
        return latestBlockHeight;
    }

    public void setLatestBlockHeight(Long latestBlockHeight) {
        this.latestBlockHeight = latestBlockHeight;
    }

    public DateTime getLatestBlockTime() {
        return latestBlockTime;
    }

    public void setLatestBlockTime(DateTime latestBlockTime) {
        this.latestBlockTime = latestBlockTime;
    }

    public Boolean getCatchingUp() {
        return catchingUp;
    }

    public void setCatchingUp(Boolean catchingUp) {
        this.catchingUp = catchingUp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("latestBlockHash", latestBlockHash)
                .append("latestAppHash", latestAppHash)
                .append("latestBlockHeight", latestBlockHeight)
                .append("latestBlockTime", latestBlockTime)
                .append("catchingUp", catchingUp)
                .toString();
    }
}

