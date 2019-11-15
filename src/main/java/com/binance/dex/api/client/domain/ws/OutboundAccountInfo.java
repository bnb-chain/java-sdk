package com.binance.dex.api.client.domain.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutboundAccountInfo {
    @JsonProperty("e")
    private String eventType;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "OutboundAccountInfo{" +
                "eventType='" + eventType + '\'' +
                ", eventHeight=" + eventHeight +
                ", balances=" + balances +
                '}';
    }

    public Long getEventHeight() {
        return eventHeight;
    }

    public void setEventHeight(Long eventHeight) {
        this.eventHeight = eventHeight;
    }

    public List<AssertBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<AssertBalance> balances) {
        this.balances = balances;
    }

    @JsonProperty("E")
    private Long eventHeight;

    @JsonProperty("B")
    private List<AssertBalance> balances;
}