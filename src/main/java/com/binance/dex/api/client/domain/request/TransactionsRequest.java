package com.binance.dex.api.client.domain.request;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.TransactionType;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TransactionsRequest {
    private Long startTime;
    private Long endTime;
    private String type;
    private String asset;
    private String address;
    private String addressType;
    private Integer offset;
    private  Integer limit;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("type", type)
                .append("asset", asset)
                .append("address", address)
                .append("addressType", addressType)
                .append("limit", limit)
                .append("offset", offset)
                .toString();
    }
}
