package com.binance.dex.api.client.domain;

import com.binance.dex.api.client.BinanceDexConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.joda.time.DateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private String orderId;
    private String symbol;
    private String owner;
    private String price;
    private String quantity;
    private String cumulateQuantity;
    private String fee;
    private DateTime orderCreateTime;
    private DateTime transactionTime;
    private OrderStatus status;
    private TimeInForce timeInForce;
    private OrderSide side;
    private OrderType type;
    private String tradeId;
    private String lastExecutedPrice;
    private String lastExecutedQuantity;
    private String transactionHash;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getCumulateQuantity() {
        return cumulateQuantity;
    }

    public void setCumulateQuantity(String cumulateQuantity) {
        this.cumulateQuantity = cumulateQuantity;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public DateTime getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(DateTime orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public DateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(DateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getLastExecutedPrice() {
        return lastExecutedPrice;
    }

    public void setLastExecutedPrice(String lastExecutedPrice) {
        this.lastExecutedPrice = lastExecutedPrice;
    }

    public String getLastExecutedQuantity() {
        return lastExecutedQuantity;
    }

    public void setLastExecutedQuantity(String lastExecutedQuantity) {
        this.lastExecutedQuantity = lastExecutedQuantity;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceDexConstants.BINANCE_DEX_TO_STRING_STYLE)
                .append("orderId", orderId)
                .append("symbol", symbol)
                .append("owner", owner)
                .append("price", price)
                .append("quantity", quantity)
                .append("cumulateQuantity", cumulateQuantity)
                .append("fee", fee)
                .append("orderCreateTime", orderCreateTime)
                .append("transactionTime", transactionTime)
                .append("status", status)
                .append("timeInForce", timeInForce)
                .append("side", side)
                .append("type", type)
                .append("tradeId", tradeId)
                .append("lastExecutedPrice", lastExecutedPrice)
                .append("lastExecutedQuantity", lastExecutedQuantity)
                .append("transactionHash", transactionHash)
                .toString();
    }
}
