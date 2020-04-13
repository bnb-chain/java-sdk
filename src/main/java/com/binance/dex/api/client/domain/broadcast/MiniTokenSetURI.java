package com.binance.dex.api.client.domain.broadcast;

public class MiniTokenSetURI {

    private String from;
    private String symbol;
    private String tokenURI;

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

    public String getTokenURI() {
        return tokenURI;
    }

    public void setTokenURI(String tokenURI) {
        this.tokenURI = tokenURI;
    }

    @Override
    public String toString() {
        return "MiniTokenSetURI{" +
                "from='" + from + '\'' +
                ", symbol='" + symbol + '\'' +
                ", tokenURI='" + tokenURI + '\'' +
                '}';
    }
}
