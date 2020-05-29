package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    private String name;
    private String symbol;
    @JsonProperty("original_symbol")
    private String originalSymbol;
    @JsonProperty("total_supply")
    private Long totalSupply;
    private String owner;
    private boolean mintable;

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

    public String getOriginalSymbol() {
        return originalSymbol;
    }

    public void setOriginalSymbol(String originalSymbol) {
        this.originalSymbol = originalSymbol;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isMintable() {
        return mintable;
    }

    public void setMintable(boolean mintable) {
        this.mintable = mintable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("name", name)
                .append("symbol", symbol)
                .append("originalSymbol", originalSymbol)
                .append("totalSupply", totalSupply)
                .append("owner", owner)
                .append("mintable", mintable)
                .toString();
    }

    public void setTotalSupply(Long totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Long getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply){
        if (totalSupply != null) {
            if (totalSupply.indexOf('.') > 0) {
                totalSupply = totalSupply.substring(0, totalSupply.indexOf('.'));
                this.totalSupply = Long.parseLong(totalSupply) * 100000000L;
            }
        }
    }
}
