package com.binance.dex.api.client.domain.broadcast;

public class Mint {
    private String from;
    private String symbol;
    private Long amount;

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Mint{" +
                "from='" + from + '\'' +
                ", symbol='" + symbol + '\'' +
                ", amount=" + amount +
                '}';
    }
}
