package com.binance.dex.api.client.domain.broadcast;

public class MiniTokenIssue extends Issue {
    private int tokenType;
    private String tokenURI;

    public int getTokenType() {
        return tokenType;
    }

    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenURI() {
        return tokenURI;
    }

    public void setTokenURI(String tokenURI) {
        this.tokenURI = tokenURI;
    }

    @Override
    public String toString() {
        return "MiniTokenIssue{" +
                "from='" + super.getFrom() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", symbol='" + super.getSymbol() + '\'' +
                ", tokenType=" + tokenType +
                ", totalSupply=" + super.getTotalSupply() +
                ", tokenURI=" + tokenURI +
                ", mintable=" + super.getMintable() +
                '}';
    }

}