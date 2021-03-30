package com.binance.dex.api.client.encoding.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ListGrowthMarketMessage implements BinanceDexTransactionMessage {

    private String from;
    @JsonProperty("base_asset_symbol")
    private String baseAssetSymbol;
    @JsonProperty("quote_asset_symbol")
    private String quoteAssetSymbol;
    @JsonProperty("init_price")
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
