package com.binance.dex.api.client.domain.broadcast;

public class Issue {

    private String from;
    private String name;
    private String symbol;
    private Long totalSupply;
    private Boolean mintable;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Long totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Boolean getMintable() {
        return mintable;
    }

    public void setMintable(Boolean mintable) {
        this.mintable = mintable;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "from='" + from + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", totalSupply=" + totalSupply +
                ", mintable=" + mintable +
                '}';
    }
}
