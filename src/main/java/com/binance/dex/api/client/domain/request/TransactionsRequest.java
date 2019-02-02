package com.binance.dex.api.client.domain.request;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.TransactionType;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TransactionsRequest {
    private String address;
    private Long blockHeight;
    private Long endTime;
    private Integer limit;
    private Integer offset;
    private OrderSide side;
    private Long startTime;
    private String txAsset;
    private TransactionType txType;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
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

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getTxAsset() {
        return txAsset;
    }

    public void setTxAsset(String txAsset) {
        this.txAsset = txAsset;
    }

    public TransactionType getTxType() {
        return txType;
    }

    public void setTxType(TransactionType txType) {
        this.txType = txType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("address", address)
                .append("blockHeight", blockHeight)
                .append("endTime", endTime)
                .append("limit", limit)
                .append("offset", offset)
                .append("side", side)
                .append("startTime", startTime)
                .append("txAsset", txAsset)
                .append("txType", txType)
                .toString();
    }
}
