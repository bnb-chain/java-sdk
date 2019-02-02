package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Market {
    public String baseAssetSymbol;
    public String quoteAssetSymbol;
    public String price;
    public String tickSize;
    public String lotSize;

    @JsonProperty("base_asset_symbol")
    public String getBaseAssetSymbol() {
        return baseAssetSymbol;
    }

    public void setBaseAssetSymbol(String baseAssetSymbol) {
        this.baseAssetSymbol = baseAssetSymbol;
    }

    @JsonProperty("quote_asset_symbol")
    public String getQuoteAssetSymbol() {
        return quoteAssetSymbol;
    }

    public void setQuoteAssetSymbol(String quoteAssetSymbol) {
        this.quoteAssetSymbol = quoteAssetSymbol;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("tick_size")
    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    @JsonProperty("lot_size")
    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("baseAssetSymbol", baseAssetSymbol)
                .append("quoteAssetSymbol", quoteAssetSymbol)
                .append("price", price)
                .append("tickSize", tickSize)
                .append("lotSize", lotSize)
                .toString();
    }
}
