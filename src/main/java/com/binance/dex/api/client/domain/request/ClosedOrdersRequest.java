package com.binance.dex.api.client.domain.request;

import com.binance.dex.api.client.BinanceDexConstants;
import com.binance.dex.api.client.domain.OrderSide;
import com.binance.dex.api.client.domain.OrderStatus;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ClosedOrdersRequest {
    private String address;
    private Long end;
    private Integer limit;
    private Integer offset;
    private OrderSide side;
    private Long start;
    private List<OrderStatus> status;
    private String symbol;
    private Integer total;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public List<OrderStatus> getStatus() {
        return status;
    }

    public void setStatus(List<OrderStatus> status) {
        this.status = status;
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
                .append("end", end)
                .append("limit", limit)
                .append("offset", offset)
                .append("side", side)
                .append("start", start)
                .append("status", status)
                .append("symbol", symbol)
                .append("total", total)
                .toString();
    }
}
