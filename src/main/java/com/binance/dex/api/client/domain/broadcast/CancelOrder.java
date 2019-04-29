package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CancelOrder {
    private String symbol;

    private String sender;

    @JsonProperty("refid")
    private String refId;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "CancelOrder{" +
                "symbol='" + symbol + '\'' +
                ", sender='" + sender + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
}
