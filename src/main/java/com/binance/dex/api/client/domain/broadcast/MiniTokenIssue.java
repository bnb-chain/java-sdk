package com.binance.dex.api.client.domain.broadcast;

public class MiniTokenIssue extends Issue {
    private Long maxTotalSupply;
    private String tokenURI;

    public Long getMaxTotalSupply() {
        return maxTotalSupply;
    }

    public void setMaxTotalSupply(Long maxTotalSupply) {
        this.maxTotalSupply = maxTotalSupply;
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
                ", maxTotalSupply=" + maxTotalSupply +
                ", totalSupply=" + super.getTotalSupply() +
                ", tokenURI=" + tokenURI +
                ", mintable=" + super.getMintable() +
                '}';
    }

}