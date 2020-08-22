package com.binance.dex.api.client.domain.bridge;

public class Unbind {

    private String from;

    private String symbol;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "UnBind{" +
                "from='" + from + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
