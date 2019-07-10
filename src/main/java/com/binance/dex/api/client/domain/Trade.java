package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {
    private String baseAsset;
    private Long blockHeight;
    private String buyFee;
    private String buyerId;
    private String buyerOrderId;
    private String price;
    private String quantity;
    private String quoteAsset;
    private String sellFee;
    private String sellerId;
    private String sellerOrderId;
    private String symbol;
    private Long time;
    private String tradeId;
    private String buySingleFee;
    private String sellSingleFee;
    private String tickType;

    public String getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getBuyFee() {
        return buyFee;
    }

    public void setBuyFee(String buyFee) {
        this.buyFee = buyFee;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerOrderId() {
        return buyerOrderId;
    }

    public void setBuyerOrderId(String buyerOrderId) {
        this.buyerOrderId = buyerOrderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public String getSellFee() {
        return sellFee;
    }

    public void setSellFee(String sellFee) {
        this.sellFee = sellFee;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerOrderId() {
        return sellerOrderId;
    }

    public void setSellerOrderId(String sellerOrderId) {
        this.sellerOrderId = sellerOrderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("baseAsset", baseAsset)
                .append("blockHeight", blockHeight)
                .append("buyFee", buyFee)
                .append("buyerId", buyerId)
                .append("buyerOrderId", buyerOrderId)
                .append("price", price)
                .append("quantity", quantity)
                .append("quoteAsset", quoteAsset)
                .append("sellFee", sellFee)
                .append("sellerId", sellerId)
                .append("sellerOrderId", sellerOrderId)
                .append("symbol", symbol)
                .append("time", time)
                .append("tradeId", tradeId)
                .append("buySingleFee",buySingleFee)
                .append("sellSingleFee",sellSingleFee)
                .append("tickType",tickType)
                .toString();
    }

    public String getBuySingleFee() {
        return buySingleFee;
    }

    public void setBuySingleFee(String buySingleFee) {
        this.buySingleFee = buySingleFee;
    }

    public String getSellSingleFee() {
        return sellSingleFee;
    }

    public void setSellSingleFee(String sellSingleFee) {
        this.sellSingleFee = sellSingleFee;
    }

    public String getTickType() {
        return tickType;
    }

    public void setTickType(String tickType) {
        this.tickType = tickType;
    }
}
