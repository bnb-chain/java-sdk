package com.binance.dex.api.client.domain.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssertBalance {
    @JsonProperty("a")
    private String asset;

    public String getFree() {
        return free;
    }

    @Override
    public String toString() {
        return "AssertBalance{" +
                "asset='" + asset + '\'' +
                ", free='" + free + '\'' +
                ", locked='" + locked + '\'' +
                ", frozen='" + frozen + '\'' +
                '}';
    }

    public void setFree(String free) {
        this.free = free;
    }

    @JsonProperty("f")
    private String free;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    @JsonProperty("l")
    private String locked;

    @JsonProperty("r")
    private String frozen;
}
