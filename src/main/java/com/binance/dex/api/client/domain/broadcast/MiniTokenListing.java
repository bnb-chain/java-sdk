package com.binance.dex.api.client.domain.broadcast;

public class MiniTokenListing {

    private String fromAddr;
    private String baseAssetSymbol;
    private String quoteAssetSymbol;
    private Long initPrice;

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
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

    @Override
    public String toString() {
        return "MiniTokenListing{" +
                "fromAddr='" + fromAddr + '\'' +
                ", baseAssetSymbol='" + baseAssetSymbol + '\'' +
                ", quoteAssetSymbol='" + quoteAssetSymbol + '\'' +
                ", initPrice=" + initPrice +
                '}';
    }
}
