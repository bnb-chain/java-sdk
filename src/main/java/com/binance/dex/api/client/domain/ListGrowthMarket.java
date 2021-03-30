package com.binance.dex.api.client.domain;


public class ListGrowthMarket {
    private String from;
    private String baseAssetSymbol;
    private String quoteAssetSymbol;
    private Long initPrice;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBaseAssetSymbol() {
        return baseAssetSymbol;
    }

    public void setBaseAssetSymbol(String baseAssetSymbol) {
        this.baseAssetSymbol = baseAssetSymbol;
    }

    public String getQuoteAssetSymbol() {
        return quoteAssetSymbol;
    }

    public void setQuoteAssetSymbol(String quoteAssetSymbol) {
        this.quoteAssetSymbol = quoteAssetSymbol;
    }

    public Long getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Long initPrice) {
        this.initPrice = initPrice;
    }
}
