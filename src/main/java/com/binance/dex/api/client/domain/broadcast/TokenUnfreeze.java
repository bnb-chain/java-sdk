package com.binance.dex.api.client.domain.broadcast;

public class TokenUnfreeze {
    private String from;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "TokenUnfreeze{" +
                "from='" + from + '\'' +
                ", symbol='" + symbol + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
