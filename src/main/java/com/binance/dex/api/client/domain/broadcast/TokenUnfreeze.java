package com.binance.dex.api.client.domain.broadcast;

import com.binance.dex.api.client.BinanceDexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TokenUnfreeze {
    private String symbol;
    private String amount;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("symbol", symbol)
                .append("amount", amount)
                .toString();
    }
}
