package com.binance.dex.api.client.domain.request;

import com.binance.dex.api.client.BinanceDexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OpenOrdersRequest {
    private String address;
    private Integer limit;
    private Integer offset;
    private String symbol;
    private Integer total;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                .append("limit", limit)
                .append("offset", offset)
                .append("symbol", symbol)
                .append("total", total)
                .toString();
    }
}
