package com.binance.dex.api.client.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderSide {
    BUY(1L), SELL(2L);

    private long value;

    OrderSide(long value) {
        this.value = value;
    }

    @JsonCreator
    public static OrderSide fromValue(long value) {
        for (OrderSide os : OrderSide.values()) {
            if (os.value == value) {
                return os;
            }
        }
        return null;
    }

    @JsonValue
    public long toValue() {
        return this.value;
    }

}
