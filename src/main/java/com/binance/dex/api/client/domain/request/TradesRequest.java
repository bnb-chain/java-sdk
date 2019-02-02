package com.binance.dex.api.client.domain.request;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.client.domain.OrderSide;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TradesRequest {
    private String address;
    private String buyerOrderId;
    private Long end;
    private Long height;
    private Integer limit;
    private Integer offset;
    private String quoteAsset;
    private String sellerOrderId;
    private OrderSide side;
    private Long start;
    private String symbol;
    private Integer total;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuyerOrderId() {
        return buyerOrderId;
    }

    public void setBuyerOrderId(String buyerOrderId) {
        this.buyerOrderId = buyerOrderId;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public String getSellerOrderId() {
        return sellerOrderId;
    }

    public void setSellerOrderId(String sellerOrderId) {
        this.sellerOrderId = sellerOrderId;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("address", address)
                .append("buyerOrderId", buyerOrderId)
                .append("end", end)
                .append("height", height)
                .append("limit", limit)
                .append("offset", offset)
                .append("quoteAsset", quoteAsset)
                .append("sellerOrderId", sellerOrderId)
                .append("side", side)
                .append("start", start)
                .append("symbol", symbol)
                .append("total", total)
                .toString();
    }
}
