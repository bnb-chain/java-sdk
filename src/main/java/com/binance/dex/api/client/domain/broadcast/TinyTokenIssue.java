package com.binance.dex.api.client.domain.broadcast;

public class TinyTokenIssue extends Issue {
    private String tokenURI;

    public String getTokenURI() {
        return tokenURI;
    }

    public void setTokenURI(String tokenURI) {
        this.tokenURI = tokenURI;
    }

    @Override
    public String toString() {
        return "TinyTokenIssue{" +
                "from='" + super.getFrom() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", symbol='" + super.getSymbol() + '\'' +
                ", totalSupply=" + super.getTotalSupply() +
                ", tokenURI=" + tokenURI +
                ", mintable=" + super.getMintable() +
                '}';
    }

}