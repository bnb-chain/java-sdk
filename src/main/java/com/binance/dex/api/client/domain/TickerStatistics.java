package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerStatistics {
    private String symbol;
    private String priceChange;
    private String priceChangePercent;
    private String prevClosePrice;
    private String lastPrice;
    private String lastQuantity;
    private String openPrice;
    private String highPrice;
    private String lowPrice;
    private Long openTime;
    private Long closeTime;
    private String firstId;
    private String lastId;
    private String bidPrice;
    private String bidQuantity;
    private String askPrice;
    private String askQuantity;
    private String weightedAvgPrice;
    private String volume;
    private String quoteVolume;
    private Long count;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(String priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public String getPrevClosePrice() {
        return prevClosePrice;
    }

    public void setPrevClosePrice(String prevClosePrice) {
        this.prevClosePrice = prevClosePrice;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getLastQuantity() {
        return lastQuantity;
    }

    public void setLastQuantity(String lastQuantity) {
        this.lastQuantity = lastQuantity;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(String bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public String getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(String askPrice) {
        this.askPrice = askPrice;
    }

    public String getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(String askQuantity) {
        this.askQuantity = askQuantity;
    }

    public String getWeightedAvgPrice() {
        return weightedAvgPrice;
    }

    public void setWeightedAvgPrice(String weightedAvgPrice) {
        this.weightedAvgPrice = weightedAvgPrice;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(String quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("symbol", symbol)
                .append("priceChange", priceChange)
                .append("priceChangePercent", priceChangePercent)
                .append("prevClosePrice", prevClosePrice)
                .append("lastPrice", lastPrice)
                .append("lastQuantity", lastQuantity)
                .append("openPrice", openPrice)
                .append("highPrice", highPrice)
                .append("lowPrice", lowPrice)
                .append("openTime", openTime)
                .append("closeTime", closeTime)
                .append("firstId", firstId)
                .append("lastId", lastId)
                .append("bidPrice", bidPrice)
                .append("bidQuantity", bidQuantity)
                .append("askPrice", askPrice)
                .append("askQuantity", askQuantity)
                .append("weightedAvgPrice", weightedAvgPrice)
                .append("volume", volume)
                .append("quoteVolume", quoteVolume)
                .append("count", count)
                .toString();
    }
}
