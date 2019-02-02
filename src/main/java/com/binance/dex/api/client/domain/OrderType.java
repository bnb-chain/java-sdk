package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {
    LIMIT(2L);

    private long value;

    OrderType(long value) {
        this.value = value;
    }

    @JsonCreator
    public static OrderType fromValue(long value) {
        for (OrderType ot : OrderType.values()) {
            if (ot.value == value) {
                return ot;
            }
        }
        return null;
    }

    @JsonValue
    public long toValue() {
        return this.value;
    }
}
